package br.ufsc.agents_in_charge.benches.separating;

import java.util.Arrays;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import br.ufsc.agents_in_charge.Sosa;
import br.ufsc.agents_in_charge.benches.separating.components.Lock;
import br.ufsc.agents_in_charge.benches.separating.components.OrientationSensor;
import br.ufsc.agents_in_charge.benches.separating.components.StoppedSensor;
import br.ufsc.agents_in_charge.commom.Component;

public class OrientationVerificationSystem extends Component {
  public static final String URI = SeparatingBench.URI + "OrientationVerificationSystem/";

  public OrientationVerificationSystem() {
    super();
  }

  private static final List<Component> hosts = Arrays.asList(
      new StoppedSensor(),
      new Lock(),
      new OrientationSensor());

  @Override
  public String getURI() {
    return URI;
  }

  @Override
  public void build(Model model) {
    this.resource = model.createResource(URI);
    resource.addProperty(RDF.type, Sosa.Platform);
    hosts.forEach(host -> {
      ensureBuilt(model, host.getClass());
      resource.addProperty(Sosa.hosts, model.getResource(host.getURI()));
    });
    resource.addProperty(RDFS.label, "Orientation Verification System");
    resource.addProperty(RDFS.comment, model.createLiteral("Sistema que verifica a orientação da peça", "pt-BR"));
  }
}
