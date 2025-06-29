package br.ufsc.agents_in_charge.benches.separating.components;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import br.ufsc.agents_in_charge.Onto;
import br.ufsc.agents_in_charge.Sosa;
import br.ufsc.agents_in_charge.benches.separating.MainConveyor;
import br.ufsc.agents_in_charge.commom.Component;

public class ExitPointPresence extends Component {
  public static final String URI = MainConveyor.URI + "ExitPointPresence/";

  public ExitPointPresence() {
    super();
  }

  @Override
  public String getURI() {
    return URI;
  }

  @Override
  public void build(Model model) {
    this.resource = model.createResource(URI);
    resource.addProperty(RDF.type, Onto.PresenceProperty);
    resource.addProperty(Sosa.isObservedBy, model.getResource(ExitSensor.URI));
    resource.addProperty(RDFS.label, "Exit Point Presence");
    resource.addProperty(RDFS.comment, model.createLiteral("Presença no ponto de saída", "pt-BR"));
  }
}
