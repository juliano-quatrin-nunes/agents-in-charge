package br.ufsc.agents_in_charge.benches.separating;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import br.ufsc.agents_in_charge.Sosa;
import br.ufsc.agents_in_charge.Td;
import br.ufsc.agents_in_charge.Vocabulary;
import br.ufsc.agents_in_charge.commom.Component;

public class HeightSensor extends Component {

  private final String url = Vocabulary.SERVER_URI + "heightSensor";

  public HeightSensor() {
    super();
  }

  @Override
  public void build(Model model) {
    this.resource = model.createResource(url);
    model.add(resource, RDF.type, Sosa.Sensor);
    model.add(resource, RDFS.label, "Height Sensor");

    Resource heightProperty = model.createResource(Vocabulary.PROPERTY_URI + "Height");
    heightProperty.addProperty(RDF.type, Sosa.ObservableProperty);

    resource.addProperty(Sosa.observes, heightProperty);

    Resource propertyAffordance = model.createResource();
    propertyAffordance.addProperty(RDF.type, Td.PropertyAffordance);
    propertyAffordance.addProperty(RDFS.label, "Current Height");
    propertyAffordance.addProperty(Td.hasForm, createPropertyForm(model, url));

    resource.addProperty(Td.hasPropertyAffordance, propertyAffordance);

  }

}