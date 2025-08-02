package br.ufsc.agents_in_charge.benches.separating.components;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import br.ufsc.agents_in_charge.Onto;
import br.ufsc.agents_in_charge.Sosa;
import br.ufsc.agents_in_charge.benches.separating.OrientationVerificationSystem;
import br.ufsc.agents_in_charge.commom.Component;

public class VerificationSystemPresence extends Component {
  public static final String URI = OrientationVerificationSystem.URI + "VerificationSystemPresence/";

  public VerificationSystemPresence() {
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
    resource.addProperty(Sosa.isObservedBy, model.getResource(StoppedSensor.URI));
    resource.addProperty(RDFS.label, "Verification System Presence");
    resource.addProperty(RDFS.comment, model.createLiteral("Presença do sistema de verificação de peças", "pt-BR"));
  }
}
