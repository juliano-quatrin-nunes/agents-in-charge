package br.ufsc.agents_in_charge;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.tdb2.TDB2Factory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servidor HTTP que expõe os dados RDF com content negotiation
 */
public class LinkedDataServer {
  private final String tdbDirectory;
  private final int port;
  private final Pattern resourcePattern = Pattern.compile("/kg/(.+)");

  public LinkedDataServer(String tdbDirectory, int port) {
    this.tdbDirectory = tdbDirectory;
    this.port = port;
  }

  /**
   * Inicia o servidor HTTP
   */
  public void start() throws Exception {
    System.out.println("Iniciando servidor na porta " + port);

    // Criar servidor Jetty
    Server server = new Server(port);

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");
    server.setHandler(context);

    // Adicionar servlet para atender as requisições
    context.addServlet(new ServletHolder(new LinkedDataServlet()), "/*");

    // Iniciar o servidor
    server.start();
    System.out.println("Servidor iniciado com sucesso em http://localhost:" + port);
    System.out.println("Exemplo de recurso: http://localhost:" + port + "/kg/HeightSensor");
    System.out.println("Pressione Ctrl+C para encerrar o servidor");
    server.join();
  }

  /**
   * Servlet para lidar com as requisições HTTP
   */
  private class LinkedDataServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

      String path = req.getRequestURI();

      if (path.startsWith("/kg/vocabulary")) {
        serveVocabularyGraph(req, resp);
        return;
      }

      if (path.equals("/kg") || path.equals("/kg/")) {
        serveFullGraph(req, resp);
        return;
      }

      Matcher matcher = resourcePattern.matcher(path);
      if (!matcher.matches()) {
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        resp.getWriter().println("Invalid resource path. Path must start with /kg/");
        return;
      }

      String resourceId = matcher.group(1);
      String resourceUri = Vocabulary.BASE_URI + resourceId;

      Dataset dataset = TDB2Factory.connectDataset(tdbDirectory);
      try {
        dataset.begin(ReadWrite.READ);
        Model model = dataset.getDefaultModel();
        Resource resource = model.getResource(resourceUri);

        if (!model.containsResource(resource)) {
          resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
          resp.getWriter().println("Resource not found: " + resourceUri);
          return;
        }

        Model resourceModel = extractMeaningfulSubgraph(model, resource);

        resourceModel.setNsPrefixes(model.getNsPrefixMap());
        serveRdfResponse(req, resp, resourceModel);

      } finally {
        if (dataset != null) {
          dataset.end();
          dataset.close();
        }
      }
    }

    /**
     * Serves the vocabulary definitions (TBox) from the TDB dataset.
     */
    private void serveVocabularyGraph(HttpServletRequest req, HttpServletResponse resp) throws IOException {
      Dataset dataset = TDB2Factory.connectDataset(tdbDirectory);
      try {
        dataset.begin(ReadWrite.READ);
        Model fullModel = dataset.getDefaultModel();

        // --- Filtering Logic for Vocabulary ---
        Model vocabModel = ModelFactory.createDefaultModel();
        vocabModel.setNsPrefixes(fullModel.getNsPrefixMap());

        StmtIterator iter = fullModel.listStatements();
        while (iter.hasNext()) {
          Statement stmt = iter.next();
          Resource subject = stmt.getSubject();

          // Check if the subject's namespace IS the vocabulary namespace
          if (subject.getNameSpace() != null && subject.getNameSpace().equals(Vocabulary.VOCAB_URI)) {
            vocabModel.add(stmt);
          }
        }
        // --- End of Filtering Logic ---

        serveRdfResponse(req, resp, vocabModel);

      } finally {
        if (dataset != null) {
          dataset.end();
          dataset.close();
        }
      }
    }

    /**
     * Serves the entire default graph from the TDB dataset.
     */
    private void serveFullGraph(HttpServletRequest req, HttpServletResponse resp) throws IOException {
      Dataset dataset = TDB2Factory.connectDataset(tdbDirectory);
      try {
        dataset.begin(ReadWrite.READ);
        Model model = dataset.getDefaultModel();
        serveRdfResponse(req, resp, model);
      } finally {
        if (dataset != null) {
          dataset.end();
          dataset.close();
        }
      }
    }

    /**
     * Handles content negotiation and writes the RDF model to the response.
     */
    private void serveRdfResponse(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
      String acceptHeader = req.getHeader("Accept");
      Lang outputLang = Lang.TURTLE; // Default to Turtle
      String contentType = "text/turtle;charset=UTF-8";

      if (acceptHeader != null) {
        if (acceptHeader.contains("application/ld+json")) {
          outputLang = Lang.JSONLD;
          contentType = "application/ld+json;charset=UTF-8";
        } else if (acceptHeader.contains("application/rdf+xml")) {
          outputLang = Lang.RDFXML;
          contentType = "application/rdf+xml;charset=UTF-8";
        }
        // Add more formats if needed (e.g., N-TRIPLES)
      }

      resp.setContentType(contentType);
      resp.setCharacterEncoding("UTF-8"); // Important for special characters
      OutputStream out = resp.getOutputStream();
      RDFDataMgr.write(out, model, outputLang);
    }
  }

  /**
   * Extracts properties of the central resource and full descriptions of any
   * blank nodes connected to it.
   * This includes:
   * 1. All properties of the central resource.
   * 2. The full description of any blank nodes connected to the central resource.
   *
   * @param sourceModel The full knowledge graph.
   * @param centerNode  The resource to describe.
   * @return A new Model containing the subgraph.
   */
  private Model extractMeaningfulSubgraph(Model sourceModel, Resource centerNode) {
    Model targetModel = ModelFactory.createDefaultModel();

    Set<Resource> visited = new HashSet<>();

    StmtIterator centerIter = sourceModel.listStatements(centerNode, null, (RDFNode) null);
    while (centerIter.hasNext()) {
      Statement stmt = centerIter.next();
      targetModel.add(stmt);
      RDFNode object = stmt.getObject();
      if (object.isAnon()) {
        extractRecursive(sourceModel, object, targetModel, visited);
      }
    }

    return targetModel;
  }

  private void extractRecursive(Model source, RDFNode node, Model target, Set<Resource> visited) {
    if (!node.isResource()) {
      return;
    }

    Resource resource = node.asResource();

    if (!resource.isAnon()) {
      return;
    }

    if (visited.contains(resource)) {
      return;
    }

    visited.add(resource);

    StmtIterator subjIter = source.listStatements(resource, null, (RDFNode) null);
    while (subjIter.hasNext()) {
      Statement stmt = subjIter.next();
      target.add(stmt);
      RDFNode object = stmt.getObject();
      if (object.isAnon()) {
        extractRecursive(source, object, target, visited);
      }
    }
  }
}
