package br.ufsc.agents_in_charge.benches.separating.components;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import br.ufsc.agents_in_charge.Onto;
import br.ufsc.agents_in_charge.Ssn;
import br.ufsc.agents_in_charge.benches.separating.MainConveyor;
import br.ufsc.agents_in_charge.commom.Component;

public class EntryPoint extends Component {
  public static final String URI = MainConveyor.URI + "EntryPoint/";

  public EntryPoint() {
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
    resource.addProperty(RDF.type, Onto.ProductManagementArea);
    resource.addProperty(Ssn.hasProperty, model.getResource(EntryPointPresence.URI));
    resource.addProperty(RDFS.label, "Entry Point");
    resource.addProperty(RDFS.comment, model.createLiteral("Ponto de entrada para a esteira principal", "pt-BR"));
  }
}
