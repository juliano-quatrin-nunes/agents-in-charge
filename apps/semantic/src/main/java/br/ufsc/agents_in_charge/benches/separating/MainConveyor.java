package br.ufsc.agents_in_charge.benches.separating;

import java.util.Arrays;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import br.ufsc.agents_in_charge.Sosa;
import br.ufsc.agents_in_charge.Td;
import br.ufsc.agents_in_charge.Vocabulary;
import br.ufsc.agents_in_charge.commom.Component;

public class MainConveyor extends Component {
  public static final String URI = Vocabulary.BASE_URI + "MainConveyor";

  private final List<Component> components = Arrays.asList(new EntryPoint(), new HeightMeasurement(),
      new DiscardPoint(), new ExitPoint());

  public MainConveyor() {
    super();
  }

  @Override
  public void build(Model model) {
    this.resource = model.createResource(URI);
    resource.addProperty(RDF.type, Sosa.Platform);
    resource.addProperty(RDF.type, model.getResource(Vocabulary.C_CONVEYOR_URI));
    resource.addProperty(RDFS.label, "Main Conveyor");
    resource.addProperty(RDFS.comment, model.createLiteral("Esteira principal", "pt-BR"));
    components.forEach(component -> {
      component.build(model);
      resource.addProperty(Sosa.hosts, component.resource);
    });

    // Property affordance for reading main conveyor status
    Resource propertyAffordance = model.createResource();
    propertyAffordance.addProperty(RDF.type, Td.PropertyAffordance);
    propertyAffordance.addProperty(RDFS.label, "Main conveyor status");
    propertyAffordance.addProperty(RDFS.comment, model.createLiteral("Status da esteira principal", "pt-BR"));
    propertyAffordance.addProperty(Td.hasForm, createPropertyForm(model, Vocabulary.SERVER_URI + "mainConveyor"));

    resource.addProperty(Td.hasPropertyAffordance, propertyAffordance);

    // Action affordance for activating main conveyor
    Resource actionAffordance = model.createResource();
    actionAffordance.addProperty(RDF.type, Td.ActionAffordance);
    actionAffordance.addProperty(RDFS.label, "Main conveyor");
    actionAffordance.addProperty(RDFS.comment, model.createLiteral("Ativa a esteira principal", "pt-BR"));
    actionAffordance.addProperty(Td.hasForm, createActionForm(model, Vocabulary.SERVER_URI + "mainConveyor"));

    resource.addProperty(Td.hasActionAffordance, actionAffordance);
  }
}
