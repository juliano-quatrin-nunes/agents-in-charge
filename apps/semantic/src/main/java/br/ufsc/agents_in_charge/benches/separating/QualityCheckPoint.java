package br.ufsc.agents_in_charge.benches.separating;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import br.ufsc.agents_in_charge.Vocabulary;
import br.ufsc.agents_in_charge.commom.Component;

public class QualityCheckPoint extends Component {
  public static final String URI = Vocabulary.BASE_URI + "QualityCheckPoint";

  public QualityCheckPoint() {
    super();
  }

  @Override
  public void build(Model model) {
    this.resource = model.createResource(URI);
    resource.addProperty(RDF.type, model.getResource(Vocabulary.C_POINT_OF_INTEREST_URI));
    resource.addProperty(model.getProperty(Vocabulary.P_IS_REALIZED_BY_URI),
        model.getResource(HeightMeasurement.URI));
    resource.addProperty(RDFS.label, "Quality Check Point");
    resource.addProperty(RDFS.comment, model.createLiteral("Ponto de verificação de altura da peça", "pt-BR"));
  }
}
