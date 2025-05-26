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
            
            // Verificar se é uma requisição para um recurso
            Matcher matcher = resourcePattern.matcher(path);
            if (!matcher.matches()) {
                return;
            }
            
            // Extrair o ID do recurso
            String resourceId = matcher.group(1);
            String resourceUri = Vocabulary.BASE_URI + resourceId;
            
            // Conectar ao dataset TDB
            Dataset dataset = TDB2Factory.connectDataset(tdbDirectory);
            
            try {
                dataset.begin(ReadWrite.READ);
                
                // Obter o modelo
                Model model = dataset.getDefaultModel();
                
                // Verificar se o recurso existe
                Resource resource = model.getResource(resourceUri);
                
                // Criar um submodelo contendo informações sobre este recurso
                Model resourceModel = ModelFactory.createDefaultModel();
                
                // Extrair subgráfico completo para este recurso
                extractSubgraph(model, resource, resourceModel, new HashSet<>());
                
                if (resourceModel.isEmpty()) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().println("Recurso não encontrado: " + resourceUri);
                    return;
                }
                
                // Copiar prefixos
                resourceModel.setNsPrefixes(model.getNsPrefixMap());
                
                // Content negotiation
                String acceptHeader = req.getHeader("Accept");
                
                // Determinar o formato de saída
                Lang outputLang = Lang.TURTLE; // Padrão para Turtle
                String contentType = "text/turtle";

                if (acceptHeader != null) {
                    // Processar cabeçalho Accept
                    if (acceptHeader.contains("application/ld+json")) {
                        outputLang = Lang.JSONLD;
                        contentType = "application/ld+json";
                    } else if (acceptHeader.contains("application/rdf+xml")) {
                        outputLang = Lang.RDFXML;
                        contentType = "application/rdf+xml";
                    }
                }
                
                // Definir tipo de conteúdo e escrever dados RDF
                resp.setContentType(contentType);
                OutputStream out = resp.getOutputStream();
                RDFDataMgr.write(out, resourceModel, outputLang);
                
            } finally {
                if (dataset != null) {
                    dataset.end();
                    dataset.close();
                }
            }
        }
    }

    /**
     * Extrai recursivamente o subgráfico ao redor de um recurso,
     * incluindo nós em branco e recursos conectados
     */
    private void extractSubgraph(Model sourceModel, RDFNode node, Model targetModel, Set<RDFNode> visited) {
        // Evitar processamento infinito em ciclos
        if (visited.contains(node)) {
            return;
        }
        visited.add(node);
        
        // Adicionar declarações onde o nó é o sujeito
        StmtIterator subjIt = sourceModel.listStatements(node.asResource(), null, (RDFNode)null);
        while (subjIt.hasNext()) {
            Statement stmt = subjIt.next();
            targetModel.add(stmt);
            
            // Se o objeto é um nó em branco, processar recursivamente
            RDFNode obj = stmt.getObject();
            if (obj.isAnon()) {
                extractSubgraph(sourceModel, obj, targetModel, visited);
            }
            // Para recursos normais, não expandimos para evitar trazer o grafo inteiro
        }
    }
}
