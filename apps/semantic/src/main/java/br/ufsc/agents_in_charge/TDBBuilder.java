package br.ufsc.agents_in_charge;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.tdb2.TDB2Factory;
import org.apache.jena.vocabulary.RDFS;

import br.ufsc.agents_in_charge.benches.separating.ArrivalSensor;
import br.ufsc.agents_in_charge.benches.separating.DiscardDiverter;
import br.ufsc.agents_in_charge.benches.separating.DiscardPoint;
import br.ufsc.agents_in_charge.benches.separating.EntryPoint;
import br.ufsc.agents_in_charge.benches.separating.ExitPoint;
import br.ufsc.agents_in_charge.benches.separating.ExitSensor;
import br.ufsc.agents_in_charge.benches.separating.HeightMeasurement;
import br.ufsc.agents_in_charge.benches.separating.HeightSensor;
import br.ufsc.agents_in_charge.benches.separating.Lock;
import br.ufsc.agents_in_charge.benches.separating.MainConveyor;
import br.ufsc.agents_in_charge.benches.separating.QualityCheckPoint;
import br.ufsc.agents_in_charge.benches.separating.StoppedSensor;
import br.ufsc.agents_in_charge.commom.Component;

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

      Vocabulary.build(model);

      List<Component> components = new ArrayList<>();

      components.add(new ArrivalSensor());
      components.add(new MainConveyor());
      components.add(new HeightSensor());
      components.add(new StoppedSensor());
      components.add(new Lock());
      components.add(new EntryPoint());
      components.add(new ExitSensor());
      components.add(new DiscardDiverter());
      components.add(new DiscardPoint());
      components.add(new ExitPoint());
      components.add(new QualityCheckPoint());
      components.add(new HeightMeasurement());

      for (Component component : components) {
        component.build(model);
      }

      dataset.commit();
    } finally {
      if (dataset.isInTransaction()) {
        dataset.end();
      }

      dataset.close();
    }
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

  private void setPrefixes(Model model) {
    model.setNsPrefix("vocab", Vocabulary.BASE_URI);
    model.setNsPrefix("sosa", Sosa.NS);
    model.setNsPrefix("td", Td.NS);
    model.setNsPrefix("hctl", Hctl.NS);
    model.setNsPrefix("htv", Htv.NS);
    model.setNsPrefix("rdfs", RDFS.getURI());
  }
}
