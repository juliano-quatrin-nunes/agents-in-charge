package br.ufsc.agents_in_charge;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.tdb2.TDB2Factory;

import br.ufsc.agents_in_charge.benches.separating.SeparatingBench;
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

      components.add(new SeparatingBench());

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
}
