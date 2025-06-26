package br.ufsc.agents_in_charge;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
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
  public static final String TD_PREFIX = "td";
  public static final String HCTL_PREFIX = "hctl";
  public static final String HTV_PREFIX = "htv";
  public static final String RDFS_PREFIX = "rdfs";

  public static final String C_HEIGHT_URI = VOCAB_URI + "Height";
  public static final String C_CONVEYOR_URI = VOCAB_URI + "Conveyor";
  public static final String C_POINT_OF_INTEREST_URI = VOCAB_URI + "PointOfInterest";
  public static final String P_IS_MONITORED_BY_URI = VOCAB_URI + "isMonitoredBy";
  public static final String P_IS_REALIZED_BY_URI = VOCAB_URI + "isRealizedBy";
  public static final String P_IS_ACTUATED_BY_URI = VOCAB_URI + "isActuatedBy";

  public static void setPrefixes(Model model) {
    model.setNsPrefix(VOCAB_PREFIX, VOCAB_URI);
    model.setNsPrefix(KG_PREFIX, BASE_URI);
    model.setNsPrefix(SOSA_PREFIX, Sosa.NS);
    model.setNsPrefix(TD_PREFIX, Td.NS);
    model.setNsPrefix(HCTL_PREFIX, Hctl.NS);
    model.setNsPrefix(HTV_PREFIX, Htv.NS);
    model.setNsPrefix(RDFS_PREFIX, RDFS.getURI());
  }

  public static void build(Model model) {
    setPrefixes(model);

    Resource height = model.createResource(C_HEIGHT_URI);
    height.addProperty(RDF.type, Sosa.ObservableProperty);
    height.addProperty(RDFS.label, "Height");

    Resource conveyor = model.createResource(C_CONVEYOR_URI);
    conveyor.addProperty(RDF.type, RDFS.Class);
    conveyor.addProperty(RDFS.subClassOf, Sosa.Platform);
    conveyor.addProperty(RDFS.label, model.createLiteral("Conveyor", "en-US"));
    conveyor.addProperty(RDFS.comment,
        model.createLiteral("Esteira é um dispositivo que move peças ao longo de um eixo", "pt-BR"));

    Resource pointOfInterest = model.createResource(C_POINT_OF_INTEREST_URI);
    pointOfInterest.addProperty(RDF.type, RDFS.Class);
    pointOfInterest.addProperty(RDFS.label, model.createLiteral("Point of Interest", "en-US"));
    pointOfInterest.addProperty(RDFS.comment,
        model.createLiteral("Ponto de interesse é um ponto de referência para a posição de uma peça", "pt-BR"));

    Property isMonitoredBy = model.createProperty(P_IS_MONITORED_BY_URI);
    isMonitoredBy.addProperty(RDF.type, RDF.Property);
    isMonitoredBy.addProperty(RDFS.domain, pointOfInterest);
    isMonitoredBy.addProperty(RDFS.range, Sosa.Sensor);
    isMonitoredBy.addProperty(RDFS.label, model.createLiteral("isMonitoredBy", "en-US"));
    isMonitoredBy.addProperty(RDFS.comment,
        model.createLiteral(
            "isMonitoredBy é uma relação que indica que um ponto de interesse é monitorado por um sensor", "pt-BR"));

    Property isRealizedBy = model.createProperty(P_IS_REALIZED_BY_URI);
    isRealizedBy.addProperty(RDF.type, RDF.Property);
    isRealizedBy.addProperty(RDFS.domain, pointOfInterest);
    isRealizedBy.addProperty(RDFS.range, Sosa.Platform);
    isRealizedBy.addProperty(RDFS.label, model.createLiteral("isRealizedBy", "en-US"));
    isRealizedBy.addProperty(RDFS.comment,
        model.createLiteral(
            "isRealizedBy é uma relação que indica que um ponto de interesse é realizado por um dispositivo (plataforma)",
            "pt-BR"));

    Property isActuatedBy = model.createProperty(P_IS_ACTUATED_BY_URI);
    isActuatedBy.addProperty(RDF.type, RDF.Property);
    isActuatedBy.addProperty(RDFS.domain, pointOfInterest);
    isActuatedBy.addProperty(RDFS.range, Sosa.Actuator);
    isActuatedBy.addProperty(RDFS.label, model.createLiteral("isActuatedBy", "en-US"));
    isActuatedBy.addProperty(RDFS.comment,
        model.createLiteral(
            "isActuatedBy é uma relação que indica que um ponto de interesse é atuado por um atuador", "pt-BR"));
  }
}