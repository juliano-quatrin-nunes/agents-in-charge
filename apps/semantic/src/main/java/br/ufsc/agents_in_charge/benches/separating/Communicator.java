package br.ufsc.agents_in_charge.benches.separating;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import br.ufsc.agents_in_charge.Sosa;
import br.ufsc.agents_in_charge.Td;
import br.ufsc.agents_in_charge.Vocabulary;
import br.ufsc.agents_in_charge.commom.Component;

public class Communicator extends Component {
  public static final String URI = Vocabulary.BASE_URI + "Communicator";

  public Communicator() {
    super();
  }

  @Override
  public void build(Model model) {
    this.resource = model.createResource(URI);
    resource.addProperty(RDF.type, Sosa.Actuator);
    resource.addProperty(RDFS.label, "Communicator");
    resource.addProperty(RDFS.comment,
        model.createLiteral("Comunicador de disponibilidade da bancada", "pt-BR"));

    // Property affordance for reading bench status
    Resource propertyAffordance = model.createResource();
    propertyAffordance.addProperty(RDF.type, Td.PropertyAffordance);
    propertyAffordance.addProperty(RDFS.label, "Communicator");
    propertyAffordance.addProperty(RDFS.comment,
        model.createLiteral("Status do comunicador", "pt-BR"));
    propertyAffordance.addProperty(Td.hasForm, createPropertyForm(model, Vocabulary.SERVER_URI + "communicator"));

    resource.addProperty(Td.hasPropertyAffordance, propertyAffordance);

    // Action affordance for performing communication
    Resource actionAffordance = model.createResource();
    actionAffordance.addProperty(RDF.type, Td.ActionAffordance);
    actionAffordance.addProperty(RDFS.label, "Communicator");
    actionAffordance.addProperty(RDFS.comment,
        model.createLiteral("Comunica a disponibilidade da bancada", "pt-BR"));
    actionAffordance.addProperty(Td.hasForm, createActionForm(model, Vocabulary.SERVER_URI + "communicator"));

    resource.addProperty(Td.hasActionAffordance, actionAffordance);
  }
}