package br.ufsc.agents_in_charge.benches.separating.components;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import br.ufsc.agents_in_charge.Onto;
import br.ufsc.agents_in_charge.Sosa;
import br.ufsc.agents_in_charge.Td;
import br.ufsc.agents_in_charge.Vocabulary;
import br.ufsc.agents_in_charge.benches.separating.MainConveyor;
import br.ufsc.agents_in_charge.commom.Component;

public class ExitSensor extends Component {
  public static final String URI = MainConveyor.URI + "ExitSensor/";

  public ExitSensor() {
    super();
  }

  @Override
  public String getURI() {
    return URI;
  }

  @Override
  public void build(Model model) {
    this.resource = model.createResource(URI);
    resource.addProperty(RDF.type, Onto.PresenceSensor);
    resource.addProperty(RDF.type, Td.Thing);
    resource.addProperty(Sosa.observes, model.getResource(ExitPointPresence.URI));
    resource.addProperty(RDFS.label, "Exit Sensor");
    resource.addProperty(RDFS.comment,
        model.createLiteral("Sensor de detecção de peça na saída da esteira principal", "pt-BR"));

    Resource propertyAffordance = model.createResource();
    propertyAffordance.addProperty(RDF.type, Td.PropertyAffordance);
    propertyAffordance.addProperty(RDFS.label, "Part Exit Sensor");
    propertyAffordance.addProperty(RDFS.comment,
        model.createLiteral("Sensor de detecção de peça na saída da esteira principal", "pt-BR"));
    propertyAffordance.addProperty(Td.hasForm, createPropertyForm(model, Vocabulary.SERVER_URI + "exitSensor"));

    resource.addProperty(Td.hasPropertyAffordance, propertyAffordance);
  }
}
