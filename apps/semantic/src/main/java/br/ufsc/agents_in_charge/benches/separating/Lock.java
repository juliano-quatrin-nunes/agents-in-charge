package br.ufsc.agents_in_charge.benches.separating;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import br.ufsc.agents_in_charge.Sosa;
import br.ufsc.agents_in_charge.Td;
import br.ufsc.agents_in_charge.Vocabulary;
import br.ufsc.agents_in_charge.commom.Component;

public class Lock extends Component {
  public static final String URI = Vocabulary.BASE_URI + "Lock";

  public Lock() {
    super();
  }

  @Override
  public void build(Model model) {
    this.resource = model.createResource(URI);
    resource.addProperty(RDF.type, Sosa.Actuator);
    resource.addProperty(RDFS.label, "Lock");
    resource.addProperty(RDFS.comment, model.createLiteral("Trava da peça para a medição de altura", "pt-BR"));

    // Property affordance for reading lock status
    Resource propertyAffordance = model.createResource();
    propertyAffordance.addProperty(RDF.type, Td.PropertyAffordance);
    propertyAffordance.addProperty(RDFS.label, "Lock status");
    propertyAffordance.addProperty(RDFS.comment, model.createLiteral("Status da trava", "pt-BR"));
    propertyAffordance.addProperty(Td.hasForm, createPropertyForm(model, Vocabulary.SERVER_URI + "lock"));

    resource.addProperty(Td.hasPropertyAffordance, propertyAffordance);

    // Action affordance for activating lock
    Resource actionAffordance = model.createResource();
    actionAffordance.addProperty(RDF.type, Td.ActionAffordance);
    actionAffordance.addProperty(RDFS.label, "Lock for part analysis");
    actionAffordance.addProperty(RDFS.comment, model.createLiteral("Trava da peça para a medição de altura", "pt-BR"));
    actionAffordance.addProperty(Td.hasForm, createActionForm(model, Vocabulary.SERVER_URI + "lock"));

    resource.addProperty(Td.hasActionAffordance, actionAffordance);
  }
}
