package br.ufsc.agents_in_charge.benches.separating.components;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import br.ufsc.agents_in_charge.Saref;
import br.ufsc.agents_in_charge.benches.separating.OrientationVerificationSystem;
import br.ufsc.agents_in_charge.commom.Component;

public class LockingFunction extends Component {
  public static final String URI = OrientationVerificationSystem.URI + "LockingFunction/";

  public LockingFunction() {
    super();
  }

  @Override
  public String getURI() {
    return URI;
  }

  @Override
  public void build(Model model) {
    this.resource = model.createResource(URI);
    resource.addProperty(RDF.type, Saref.ActuatingFunction);
    resource.addProperty(RDFS.label, "Locking Function");
    resource.addProperty(RDFS.comment,
        model.createLiteral("Função de trava da peça para a medição de altura", "pt-BR"));
  }
}
