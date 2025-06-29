package br.ufsc.agents_in_charge;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

/**
 * Vocabulário e URIs base para a aplicação
 */
public class Vocabulary {
  private static String getEnv(String name, String defaultValue) {
    String value = System.getenv(name);
    if (value == null || value.isEmpty()) {
      return defaultValue;
    }
    return value;
  }

  // Base URI para os recursos
  public static final String BASE_URI = getEnv("KG_BASE_URI", "http://localhost/kg/");
  public static final String VOCAB_URI = BASE_URI + "vocabulary/";
  public static final String SERVER_URI = getEnv("WOT_SERVER_URI", "http://localhost/api/separating/");

  public static final String KG_PREFIX = "";
  public static final String VOCAB_PREFIX = "vocab";
  public static final String SOSA_PREFIX = "sosa";
  public static final String SSN_PREFIX = "ssn";
  public static final String TD_PREFIX = "td";
  public static final String HCTL_PREFIX = "hctl";
  public static final String HTV_PREFIX = "htv";
  public static final String RDFS_PREFIX = "rdfs";
  public static final String ONTO_PREFIX = "onto";
  public static final String SAREF_PREFIX = "saref";

  public static final String C_MIDPOINT_HEIGHT_URI = VOCAB_URI + "MidpointHeight";
  public static final String C_CONVEYOR_URI = VOCAB_URI + "Conveyor";

  public static void setPrefixes(Model model) {
    model.setNsPrefix(VOCAB_PREFIX, VOCAB_URI);
    model.setNsPrefix(KG_PREFIX, BASE_URI);
    model.setNsPrefix(SOSA_PREFIX, Sosa.NS);
    model.setNsPrefix(SSN_PREFIX, Ssn.NS);
    model.setNsPrefix(TD_PREFIX, Td.NS);
    model.setNsPrefix(HCTL_PREFIX, Hctl.NS);
    model.setNsPrefix(HTV_PREFIX, Htv.NS);
    model.setNsPrefix(RDFS_PREFIX, RDFS.getURI());
    model.setNsPrefix(ONTO_PREFIX, Onto.NS);
    model.setNsPrefix(SAREF_PREFIX, Saref.NS);
  }

  public static void build(Model model) {
    setPrefixes(model);

    Resource height = model.createResource(C_MIDPOINT_HEIGHT_URI);
    height.addProperty(RDF.type, Sosa.ObservableProperty);
    height.addProperty(RDFS.label, "Midpoint Height");

    Resource conveyor = model.createResource(C_CONVEYOR_URI);
    conveyor.addProperty(RDF.type, RDFS.Class);
    conveyor.addProperty(RDFS.subClassOf, Sosa.Platform);
    conveyor.addProperty(RDFS.label, model.createLiteral("Conveyor", "en-US"));
    conveyor.addProperty(RDFS.comment,
        model.createLiteral("Esteira é um dispositivo que move peças ao longo de um eixo", "pt-BR"));
  }
}