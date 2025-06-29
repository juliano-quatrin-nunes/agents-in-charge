package br.ufsc.agents_in_charge.benches.separating.components;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import br.ufsc.agents_in_charge.Saref;
import br.ufsc.agents_in_charge.Sosa;
import br.ufsc.agents_in_charge.Td;
import br.ufsc.agents_in_charge.Vocabulary;
import br.ufsc.agents_in_charge.benches.separating.DiscardSystem;
import br.ufsc.agents_in_charge.commom.Component;

public class DiscardDiverter extends Component {
  public static final String URI = DiscardSystem.URI + "DiscardDiverter/";

  public DiscardDiverter() {
    super();
  }

  @Override
  public String getURI() {
    return URI;
  }

  @Override
  public void build(Model model) {
    ensureBuilt(model, DiverterStatusProperty.class);
    ensureBuilt(model, DiscardFunction.class);

    this.resource = model.createResource(URI);
    resource.addProperty(RDF.type, Sosa.Actuator);
    resource.addProperty(Saref.accomplishes, model.getResource(DiscardFunction.URI));
    resource.addProperty(RDFS.label, "Discard Diverter");
    resource.addProperty(RDFS.comment,
        model.createLiteral("Desviador de peças para o descarte", "pt-BR"));
    resource.addProperty(Saref.accomplishes, model.getResource(DiscardFunction.URI));

    // Property affordance for reading discard diverter status
    Resource propertyAffordance = model.createResource();
    propertyAffordance.addProperty(RDF.type, Td.PropertyAffordance);
    propertyAffordance.addProperty(RDFS.label, "Discard diverter status");
    propertyAffordance.addProperty(Td.hasForm, createPropertyForm(model, Vocabulary.SERVER_URI + "discardDiverter"));

    resource.addProperty(Td.hasPropertyAffordance, propertyAffordance);

    // Action affordance for activating discard diverter
    Resource actionAffordance = model.createResource();
    actionAffordance.addProperty(RDF.type, Td.ActionAffordance);
    actionAffordance.addProperty(RDFS.label, "Discard diverter for part");
    actionAffordance.addProperty(Td.hasForm, createActionForm(model, Vocabulary.SERVER_URI + "discardDiverter"));

    resource.addProperty(Td.hasActionAffordance, actionAffordance);
  }
}
