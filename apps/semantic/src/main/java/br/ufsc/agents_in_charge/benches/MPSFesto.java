package br.ufsc.agents_in_charge.benches;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import br.ufsc.agents_in_charge.Onto;
import br.ufsc.agents_in_charge.Ssn;
import br.ufsc.agents_in_charge.Vocabulary;
import br.ufsc.agents_in_charge.benches.separating.SeparatingBench;
import br.ufsc.agents_in_charge.commom.Component;

public class MPSFesto extends Component {
  public static final String URI = Vocabulary.BASE_URI + "MPSFesto/";

  public MPSFesto() {
    super();
  }

  @Override
  public String getURI() {
    return URI;
  }

  @Override
  public void build(Model model) {
    this.resource = model.createResource(URI);
    resource.addProperty(RDF.type, Onto.ProductionLine);
    resource.addProperty(RDFS.label, "MPS Festo");
    resource.addProperty(RDFS.comment, model.createLiteral("Linha de produção Festo", "pt-BR"));
    resource.addProperty(Ssn.hasSubSystem, model.getResource(SeparatingBench.URI));
  }
}
