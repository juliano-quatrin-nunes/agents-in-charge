package br.ufsc.agents_in_charge.benches.separating;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.vocabulary.RDF;

import br.ufsc.agents_in_charge.Vocabulary;
import br.ufsc.agents_in_charge.commom.Component;

public class MainConveyor extends Component {

  public MainConveyor() {
    super();
  }

  @Override
  public void build(Model model) {
    this.resource = model.createResource(Vocabulary.BASE_URI + "MainConveyor");
    model.add(resource, RDF.type, model.createResource(Vocabulary.BASE_URI + "MainConveyor"));
  }
}
