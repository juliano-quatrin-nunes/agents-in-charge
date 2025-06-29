package br.ufsc.agents_in_charge.benches.separating.components;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
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
    ensureBuilt(model, DiverterStatusProperty.class);

    this.resource = model.createResource(URI);
    resource.addProperty(RDF.type, Saref.ActuatingFunction);

    Resource command = model.createResource();
    command.addProperty(RDF.type, Saref.Command);
    command.addProperty(RDFS.label, "Discard Diverter State Command");
    command.addProperty(RDFS.comment,
        model.createLiteral("Comando para alterar o estado do desviador de descarte", "pt-BR"));
    command.addProperty(Saref.actsUpon, model.getResource(DiverterStatusProperty.URI));

    resource.addProperty(Saref.hasCommand, command);
    resource.addProperty(RDFS.label, "Discard Function");
    resource.addProperty(RDFS.comment, model.createLiteral("Função de descarte de peças", "pt-BR"));
  }
}