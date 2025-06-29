package br.ufsc.agents_in_charge.benches.separating;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import br.ufsc.agents_in_charge.Onto;
import br.ufsc.agents_in_charge.Sosa;
import br.ufsc.agents_in_charge.Ssn;
import br.ufsc.agents_in_charge.benches.separating.components.DiscardConveyor;
import br.ufsc.agents_in_charge.benches.separating.components.DiscardDiverter;
import br.ufsc.agents_in_charge.benches.separating.components.DiscardInputArea;
import br.ufsc.agents_in_charge.benches.separating.components.DiscardOutputArea;
import br.ufsc.agents_in_charge.benches.separating.components.DiscardSensor;
import br.ufsc.agents_in_charge.commom.Component;

public class DiscardSystem extends Component {
  public static final String URI = SeparatingBench.URI + "DiscardSystem/";

  public DiscardSystem() {
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
    resource.addProperty(RDFS.label, "Discard System");
    resource.addProperty(RDFS.comment, model.createLiteral("Subsistema responsável pelo descarte de peças", "pt-BR"));
    resource.addProperty(Sosa.hosts, model.getResource(DiscardDiverter.URI));
    resource.addProperty(Sosa.hosts, model.getResource(DiscardSensor.URI));
    resource.addProperty(Onto.inputArea, model.getResource(DiscardInputArea.URI));
    resource.addProperty(Onto.outputArea, model.getResource(DiscardOutputArea.URI));
    resource.addProperty(Ssn.hasSubSystem, model.getResource(DiscardConveyor.URI));
  }
}
