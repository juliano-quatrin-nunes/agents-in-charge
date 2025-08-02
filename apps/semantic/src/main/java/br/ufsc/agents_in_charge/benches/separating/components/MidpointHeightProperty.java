package br.ufsc.agents_in_charge.benches.separating.components;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.vocabulary.RDF;

import br.ufsc.agents_in_charge.Vocabulary;
import br.ufsc.agents_in_charge.benches.separating.OrientationVerificationSystem;
import br.ufsc.agents_in_charge.commom.Component;

public class MidpointHeightProperty extends Component {
  public static final String URI = OrientationVerificationSystem.URI + "MidpointHeightProperty/";

  public MidpointHeightProperty() {
    super();
  }

  @Override
  public String getURI() {
    return URI;
  }

  @Override
  public void build(Model model) {
    this.resource = model.createResource(URI);
    resource.addProperty(RDF.type, model.getResource(Vocabulary.C_MIDPOINT_HEIGHT_URI));
  }
}
