package br.ufsc.agents_in_charge.benches.separating;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import br.ufsc.agents_in_charge.Sosa;
import br.ufsc.agents_in_charge.Vocabulary;
import br.ufsc.agents_in_charge.commom.Component;

public class HeightMeasurement extends Component {
  public static final String URI = Vocabulary.BASE_URI + "HeightMeasurement";

  public HeightMeasurement() {
    super();
  }

  @Override
  public void build(Model model) {
    this.resource = model.createResource(URI);
    resource.addProperty(RDF.type, Sosa.Platform);
    resource.addProperty(RDFS.label, "Height Measurement");
    resource.addProperty(RDFS.comment, model.createLiteral("Plataforma de medição de altura", "pt-BR"));
    resource.addProperty(Sosa.hosts, model.getResource(HeightSensor.URI));
    resource.addProperty(Sosa.hosts, model.getResource(StoppedSensor.URI));
    resource.addProperty(Sosa.hosts, model.getResource(Lock.URI));
  }
}
