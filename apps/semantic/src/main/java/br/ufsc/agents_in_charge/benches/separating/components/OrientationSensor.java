package br.ufsc.agents_in_charge.benches.separating.components;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import br.ufsc.agents_in_charge.Sosa;
import br.ufsc.agents_in_charge.Td;
import br.ufsc.agents_in_charge.Vocabulary;
import br.ufsc.agents_in_charge.benches.separating.OrientationVerificationSystem;
import br.ufsc.agents_in_charge.commom.Component;

public class OrientationSensor extends Component {
  public static final String URI = OrientationVerificationSystem.URI + "OrientationSensor/";

  public OrientationSensor() {
    super();
  }

  @Override
  public String getURI() {
    return URI;
  }

  @Override
  public void build(Model model) {
    ensureBuilt(model, MidpointHeightProperty.class);

    this.resource = model.createResource(URI);
    resource.addProperty(RDF.type, Sosa.Sensor);
    resource.addProperty(RDFS.label, "Height Sensor");
    resource.addProperty(Sosa.observes, model.getResource(MidpointHeightProperty.URI));

    Resource propertyAffordance = model.createResource();
    propertyAffordance.addProperty(RDF.type, Td.PropertyAffordance);
    propertyAffordance.addProperty(RDFS.label, "Current Midpoint Height");
    propertyAffordance.addProperty(Td.hasForm, createPropertyForm(model, Vocabulary.SERVER_URI + "heightSensor"));

    resource.addProperty(Td.hasPropertyAffordance, propertyAffordance);
  }
}