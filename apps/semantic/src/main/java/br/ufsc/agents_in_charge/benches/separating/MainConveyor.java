package br.ufsc.agents_in_charge.benches.separating;

import java.util.Arrays;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import br.ufsc.agents_in_charge.Onto;
import br.ufsc.agents_in_charge.Sosa;
import br.ufsc.agents_in_charge.Td;
import br.ufsc.agents_in_charge.Vocabulary;
import br.ufsc.agents_in_charge.benches.separating.components.ArrivalSensor;
import br.ufsc.agents_in_charge.benches.separating.components.EntryPoint;
import br.ufsc.agents_in_charge.benches.separating.components.ExitPoint;
import br.ufsc.agents_in_charge.benches.separating.components.ExitSensor;
import br.ufsc.agents_in_charge.commom.Component;

public class MainConveyor extends Component {
  public static final String URI = SeparatingBench.URI + "MainConveyor/";

  private final List<Component> components = Arrays.asList(new ArrivalSensor(), new ExitSensor());

  public MainConveyor() {
    super();
  }

  @Override
  public String getURI() {
    return URI;
  }

  @Override
  public void build(Model model) {
    ensureBuilt(model, EntryPoint.class);
    ensureBuilt(model, ExitPoint.class);

    this.resource = model.createResource(URI);
    resource.addProperty(RDF.type, model.getResource(Vocabulary.C_CONVEYOR_URI));
    resource.addProperty(RDF.type, Td.Thing);
    resource.addProperty(RDFS.label, "Main Conveyor");
    resource.addProperty(Onto.inputArea, model.getResource(EntryPoint.URI));
    resource.addProperty(Onto.outputArea, model.getResource(ExitPoint.URI));
    resource.addProperty(RDFS.comment, model.createLiteral("Esteira principal", "pt-BR"));
    components.forEach(component -> {
      ensureBuilt(model, component.getClass());
      resource.addProperty(Sosa.hosts, model.getResource(component.getURI()));
    });

    Resource propertyAffordance = model.createResource();
    propertyAffordance.addProperty(RDF.type, Td.PropertyAffordance);
    propertyAffordance.addProperty(RDFS.label, "Main conveyor status");
    propertyAffordance.addProperty(RDFS.comment, model.createLiteral("Status da esteira principal", "pt-BR"));
    propertyAffordance.addProperty(Td.hasForm, createPropertyForm(model, Vocabulary.SERVER_URI + "mainConveyor"));

    resource.addProperty(Td.hasPropertyAffordance, propertyAffordance);

    Resource actionAffordance = model.createResource();
    actionAffordance.addProperty(RDF.type, Td.ActionAffordance);
    actionAffordance.addProperty(RDFS.label, "Main conveyor");
    actionAffordance.addProperty(RDFS.comment, model.createLiteral("Ativa a esteira principal", "pt-BR"));
    actionAffordance.addProperty(Td.hasForm, createActionForm(model, Vocabulary.SERVER_URI + "mainConveyor"));

    resource.addProperty(Td.hasActionAffordance, actionAffordance);
  }
}
