package br.ufsc.agents_in_charge;

import java.io.File;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.tdb2.TDB2Factory;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

public class TDBBuilder {
  private final String tdbDirectory;
    
  public TDBBuilder(String tdbDirectory) {
    this.tdbDirectory = tdbDirectory;
  }

  public void buildTDB() {
    System.out.println("Iniciando a construção do banco de dados TDB em: " + tdbDirectory);
    
    // Delete existing TDB directory if it exists
    File tdbDir = new File(tdbDirectory);
    if (tdbDir.exists()) {
        System.out.println("Deleting existing TDB directory: " + tdbDirectory);
        deleteDirectory(tdbDir);
    }
    
    Dataset dataset = TDB2Factory.connectDataset(tdbDirectory);

    dataset.begin(ReadWrite.WRITE);
    
    try {
      // Criar/conectar ao modelo TDB
      Model model = dataset.getDefaultModel();

      model.setNsPrefix("vocab", Vocabulary.BASE_URI);
      model.setNsPrefix("sosa", Sosa.NS);
      model.setNsPrefix("td", Td.NS);
      model.setNsPrefix("hctl", Hctl.NS);
      model.setNsPrefix("htv", Htv.NS);
      model.setNsPrefix("rdfs", RDFS.getURI());
      
      Resource heightProperty = model.createResource(Vocabulary.PROPERTY_URI + "Height");
      heightProperty.addProperty(RDF.type, Sosa.ObservableProperty);
      
      Resource heightSensor = model.createResource(Vocabulary.BASE_URI + "HeightSensor");
      heightSensor.addProperty(RDF.type, Sosa.Sensor);
      heightSensor.addProperty(Sosa.observes, heightProperty);

      Resource propertyAffordance = model.createResource();
      propertyAffordance.addProperty(RDF.type, Td.PropertyAffordance);
      propertyAffordance.addProperty(RDFS.label, "Current Height");
      propertyAffordance.addProperty(Td.hasForm, 
        createPropertyForm(model, Vocabulary.SERVER_URI + "heightSensor"));
        
      heightSensor.addProperty(Td.hasPropertyAffordance, propertyAffordance);


      // Commit the transaction
      dataset.commit();
    } finally {
      // End the transaction - if it's still active and wasn't committed, this will abort it
      if (dataset.isInTransaction()) {
        dataset.end();
      }
      
      // Close the dataset when done
      dataset.close();
    }
  }

  private static Resource createPropertyForm(Model model, String url) {
    Resource form = model.createResource();
    form.addProperty(RDF.type, Hctl.Form);
    form.addProperty(Htv.methodName, "GET");
    form.addProperty(Hctl.forContentType, "application/json");
    form.addProperty(Hctl.hasOperationType, Td.readProperty);
    form.addProperty(Hctl.hasTarget, url);
    return form;
  }

  private static Resource createActionForm(Model model, String url) {
    Resource form = model.createResource();
    form.addProperty(RDF.type, Hctl.Form);
    form.addProperty(Htv.methodName, "POST");
    form.addProperty(Hctl.forContentType, "application/json");
    form.addProperty(Hctl.hasOperationType, Td.writeProperty);
    form.addProperty(Hctl.hasTarget, url);
    return form;
  }

  /**
   * Deletes a directory and all its contents recursively
   */
  private boolean deleteDirectory(File directory) {
    if (directory.exists()) {
      File[] files = directory.listFiles();
      if (files != null) {
        for (File file : files) {
          if (file.isDirectory()) {
            deleteDirectory(file);
          } else {
            file.delete();
          }
        }
      }
    }
    return directory.delete();
  }
}
