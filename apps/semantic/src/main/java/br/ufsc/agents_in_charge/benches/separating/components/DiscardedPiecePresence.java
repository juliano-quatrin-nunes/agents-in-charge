package br.ufsc.agents_in_charge.benches.separating.components;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import br.ufsc.agents_in_charge.Onto;
import br.ufsc.agents_in_charge.Sosa;
import br.ufsc.agents_in_charge.benches.separating.DiscardSystem;
import br.ufsc.agents_in_charge.commom.Component;

public class DiscardedPiecePresence extends Component {
  public static final String URI = DiscardSystem.URI + "DiscardedPiecePresence/";

  public DiscardedPiecePresence() {
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
    resource.addProperty(Sosa.isObservedBy, model.getResource(DiscardSensor.URI));
    resource.addProperty(RDFS.label, "Discarded Piece Presence");
    resource.addProperty(RDFS.comment, model.createLiteral("Presença de peça descartada", "pt-BR"));
  }
}
