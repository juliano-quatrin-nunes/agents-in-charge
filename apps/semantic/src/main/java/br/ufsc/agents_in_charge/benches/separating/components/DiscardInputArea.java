package br.ufsc.agents_in_charge.benches.separating.components;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import br.ufsc.agents_in_charge.Onto;
import br.ufsc.agents_in_charge.Ssn;
import br.ufsc.agents_in_charge.benches.separating.DiscardSystem;
import br.ufsc.agents_in_charge.commom.Component;

public class DiscardInputArea extends Component {
  public static final String URI = DiscardSystem.URI + "DiscardInputArea/";

  public DiscardInputArea() {
    super();
  }

  @Override
  public String getURI() {
    return URI;
  }

  @Override
  public void build(Model model) {
    ensureBuilt(model, DiverterStatusProperty.class);

    this.resource = model.createResource(URI);
    resource.addProperty(RDF.type, Onto.ProductManagementArea);
    resource.addProperty(RDFS.label, "Discard Input Area");
    resource.addProperty(RDFS.comment, model.createLiteral("Área de entrada do sistema de descarte de peças", "pt-BR"));
    resource.addProperty(Ssn.hasProperty, model.getResource(DiverterStatusProperty.URI));
  }
}
