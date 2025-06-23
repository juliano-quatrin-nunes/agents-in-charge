package br.ufsc.agents_in_charge.commom;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;

import br.ufsc.agents_in_charge.Hctl;
import br.ufsc.agents_in_charge.Htv;
import br.ufsc.agents_in_charge.Td;

public abstract class Component {
  public Resource resource;

  public Component() {
    // Default constructor
  }

  public abstract void build(Model model);

  protected Resource createPropertyForm(Model model, String url) {
    Resource form = model.createResource();
    form.addProperty(RDF.type, Hctl.Form);
    form.addProperty(Htv.methodName, "GET");
    form.addProperty(Hctl.forContentType, "application/json");
    form.addProperty(Hctl.hasOperationType, Td.readProperty);
    form.addProperty(Hctl.hasTarget, url);
    return form;
  }

  protected Resource createActionForm(Model model, String url) {
    Resource form = model.createResource();
    form.addProperty(RDF.type, Hctl.Form);
    form.addProperty(Htv.methodName, "POST");
    form.addProperty(Hctl.forContentType, "application/json");
    form.addProperty(Hctl.hasOperationType, Td.writeProperty);
    form.addProperty(Hctl.hasTarget, url);
    return form;
  }
}
