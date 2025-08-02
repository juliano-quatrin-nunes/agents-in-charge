package br.ufsc.agents_in_charge.benches.separating;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import br.ufsc.agents_in_charge.Sosa;
import br.ufsc.agents_in_charge.Ssn;
import br.ufsc.agents_in_charge.benches.MPSFesto;
import br.ufsc.agents_in_charge.commom.Component;

public class SeparatingBench extends Component {
  public static final String URI = MPSFesto.URI + "Separating/";

  public SeparatingBench() {
    super();
  }

  @Override
  public String getURI() {
    return URI;
  }

  @Override
  public void build(Model model) {
    this.resource = model.createResource(URI);
    resource.addProperty(RDF.type, Sosa.Platform);

    ensureBuilt(model, OrientationVerificationSystem.class);
    ensureBuilt(model, DiscardSystem.class);
    ensureBuilt(model, MainConveyor.class);

    resource.addProperty(Ssn.hasSubSystem,
        model.getResource(OrientationVerificationSystem.URI));
    resource.addProperty(Ssn.hasSubSystem,
        model.getResource(DiscardSystem.URI));
    resource.addProperty(Ssn.hasSubSystem,
        model.getResource(MainConveyor.URI));
    resource.addProperty(RDFS.label, "Separating Bench");
    resource.addProperty(RDFS.comment,
        model.createLiteral("Bancada de separação de peças por orientação", "pt-BR"));
  }
}
