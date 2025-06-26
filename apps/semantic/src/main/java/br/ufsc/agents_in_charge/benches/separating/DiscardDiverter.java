package br.ufsc.agents_in_charge.benches.separating;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import br.ufsc.agents_in_charge.Sosa;
import br.ufsc.agents_in_charge.Vocabulary;
import br.ufsc.agents_in_charge.commom.Component;

public class DiscardDiverter extends Component {
  public static final String URI = Vocabulary.BASE_URI + "DiscardDiverter";

  public DiscardDiverter() {
    super();
  }

  @Override
  public void build(Model model) {
    this.resource = model.createResource(URI);
    resource.addProperty(RDF.type, Sosa.Actuator);
    resource.addProperty(RDFS.label, "Discard Diverter");
    resource.addProperty(RDFS.comment,
        model.createLiteral("Desviador de peças para o descarte", "pt-BR"));
  }
}
