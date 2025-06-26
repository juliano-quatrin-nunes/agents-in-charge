package br.ufsc.agents_in_charge.benches.separating;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import br.ufsc.agents_in_charge.Sosa;
import br.ufsc.agents_in_charge.Td;
import br.ufsc.agents_in_charge.Vocabulary;
import br.ufsc.agents_in_charge.commom.Component;

public class NextBenchStatus extends Component {
  public static final String URI = Vocabulary.BASE_URI + "NextBenchStatus";

  public NextBenchStatus() {
    super();
  }

  @Override
  public void build(Model model) {
    this.resource = model.createResource(URI);
    resource.addProperty(RDF.type, Sosa.Sensor);
    resource.addProperty(RDFS.label, "Next Bench Status");
    resource.addProperty(RDFS.comment,
        model.createLiteral("Sensor de status da próxima bancada", "pt-BR"));

    // Property affordance for reading next bench status
    Resource propertyAffordance = model.createResource();
    propertyAffordance.addProperty(RDF.type, Td.PropertyAffordance);
    propertyAffordance.addProperty(RDFS.label, "Next bench status");
    propertyAffordance.addProperty(RDFS.comment,
        model.createLiteral("Status da próxima bancada", "pt-BR"));
    propertyAffordance.addProperty(Td.hasForm, createPropertyForm(model, Vocabulary.SERVER_URI + "nextBench"));

    resource.addProperty(Td.hasPropertyAffordance, propertyAffordance);
  }
}