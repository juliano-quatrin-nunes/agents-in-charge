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

public class ArrivalSensor extends Component {
  public static final String URI = MainConveyor.URI + "ArrivalSensor/";

  public ArrivalSensor() {
    super();
  }

  @Override
  public String getURI() {
    return URI;
  }

  @Override
  public void build(Model model) {
    ensureBuilt(model, EntryPointPresence.class);

    this.resource = model.createResource(URI);
    resource.addProperty(RDF.type, Onto.PresenceSensor);
    resource.addProperty(Sosa.observes, model.getResource(EntryPointPresence.URI));
    resource.addProperty(RDFS.label, "Arrival Sensor");
    resource.addProperty(RDFS.comment, model.createLiteral("Sensor de chegada de peças", "pt-BR"));

    Resource propertyAffordance = model.createResource();
    propertyAffordance.addProperty(RDF.type, Td.PropertyAffordance);
    propertyAffordance.addProperty(RDFS.label, "Part Arrival Sensor");
    propertyAffordance.addProperty(RDFS.comment,
        model.createLiteral("Sensor de detecção de peça na entrada da esteira principal", "pt-BR"));
    propertyAffordance.addProperty(Td.hasForm, createPropertyForm(model, Vocabulary.SERVER_URI + "arrivalSensor"));

    resource.addProperty(Td.hasPropertyAffordance, propertyAffordance);
  }
}
