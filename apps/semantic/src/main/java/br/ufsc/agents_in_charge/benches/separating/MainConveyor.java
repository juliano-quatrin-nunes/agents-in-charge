package br.ufsc.agents_in_charge.benches.separating;

import java.util.Arrays;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import br.ufsc.agents_in_charge.Sosa;
import br.ufsc.agents_in_charge.Vocabulary;
import br.ufsc.agents_in_charge.commom.Component;

public class MainConveyor extends Component {
  public static final String URI = Vocabulary.BASE_URI + "MainConveyor";

  private final List<Component> components = Arrays.asList(new EntryPoint());

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
  }
}
