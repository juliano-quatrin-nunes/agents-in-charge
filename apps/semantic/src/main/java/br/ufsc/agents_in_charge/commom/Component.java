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

  /**
   * Returns the URI of this component.
   * All implementing classes MUST declare a static final String URI field
   * that is constructed using Vocabulary.BASE_URI plus a component-specific
   * suffix.
   * This method should return that static URI field.
   * 
   * @return The URI of this component
   */
  public abstract String getURI();

  public abstract void build(Model model);

  /**
   * Ensures a component is built if its resource doesn't have the expected
   * properties.
   * This prevents errors from referencing "bare" resources that haven't been
   * properly built.
   * 
   * @param model          The RDF model to use
   * @param componentClass The class of the component to ensure is built
   */
  protected void ensureBuilt(Model model, Class<? extends Component> componentClass) {
    try {
      Component component = componentClass.getDeclaredConstructor().newInstance();
      Resource resource = model.getResource(component.getURI());

      if (!resource.hasProperty(org.apache.jena.vocabulary.RDF.type)) {
        component.build(model);
      }
    } catch (Exception e) {
      throw new RuntimeException("Failed to ensure component is built: " + componentClass.getSimpleName(), e);
    }
  }

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
