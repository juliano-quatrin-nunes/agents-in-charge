package br.ufsc.agents_in_charge.benches.separating.components;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import br.ufsc.agents_in_charge.Sosa;
import br.ufsc.agents_in_charge.Td;
import br.ufsc.agents_in_charge.Vocabulary;
import br.ufsc.agents_in_charge.benches.separating.DiscardSystem;
import br.ufsc.agents_in_charge.commom.Component;

public class DiscardConveyor extends Component {
  public static final String URI = DiscardSystem.URI + "DicardConveyor/";

  public DiscardConveyor() {
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
    resource.addProperty(RDF.type, model.getResource(Vocabulary.C_CONVEYOR_URI));
    resource.addProperty(RDFS.label, "Discard Conveyor");
    resource.addProperty(RDFS.comment, model.createLiteral("Esteira de descarte de peças", "pt-BR"));

    // Property affordance for reading discard conveyor status
    Resource propertyAffordance = model.createResource();
    propertyAffordance.addProperty(RDF.type, Td.PropertyAffordance);
    propertyAffordance.addProperty(RDFS.label, "Discard conveyor status");
    propertyAffordance.addProperty(Td.hasForm, createPropertyForm(model, Vocabulary.SERVER_URI + "discardConveyor"));

    resource.addProperty(Td.hasPropertyAffordance, propertyAffordance);

    // Action affordance for activating discard conveyor
    Resource actionAffordance = model.createResource();
    actionAffordance.addProperty(RDF.type, Td.ActionAffordance);
    actionAffordance.addProperty(RDFS.label, "Discard conveyor");
    actionAffordance.addProperty(Td.hasForm, createActionForm(model, Vocabulary.SERVER_URI + "discardConveyor"));

    resource.addProperty(Td.hasActionAffordance, actionAffordance);
  }
}
