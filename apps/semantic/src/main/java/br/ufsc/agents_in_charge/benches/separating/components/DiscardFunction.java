package br.ufsc.agents_in_charge.benches.separating.components;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import br.ufsc.agents_in_charge.Saref;
import br.ufsc.agents_in_charge.benches.separating.DiscardSystem;
import br.ufsc.agents_in_charge.commom.Component;

public class DiscardFunction extends Component {
  public static final String URI = DiscardSystem.URI + "DiscardFunction/";

  public DiscardFunction() {
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
    resource.addProperty(RDFS.label, "Discard Function");
    resource.addProperty(RDFS.comment, model.createLiteral("Função de descarte de peças", "pt-BR"));
  }
}