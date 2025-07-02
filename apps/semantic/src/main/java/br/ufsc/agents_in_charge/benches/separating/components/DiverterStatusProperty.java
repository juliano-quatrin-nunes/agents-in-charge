package br.ufsc.agents_in_charge.benches.separating.components;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import br.ufsc.agents_in_charge.Sosa;
import br.ufsc.agents_in_charge.benches.separating.DiscardSystem;
import br.ufsc.agents_in_charge.commom.Component;

public class DiverterStatusProperty extends Component {
  public static final String URI = DiscardSystem.URI + "DiverterStatusProperty/";

  public DiverterStatusProperty() {
    super();
  }

  @Override
  public String getURI() {
    return URI;
  }

  @Override
  public void build(Model model) {
    this.resource = model.createResource(URI);
    resource.addProperty(RDF.type, Sosa.ActuatableProperty);
    resource.addProperty(RDF.type, Sosa.ObservableProperty);
    resource.addProperty(RDFS.label, "Diverter Status");
    resource.addProperty(RDFS.comment,
        model.createLiteral("Status do módulo desviador para descarte de peças", "pt-BR"));
  }
}
