package br.ufsc.agents_in_charge;

/**
 * Classe principal da aplicação
 */
public class Main {
  public static void main(String[] args) {
      try {
          // Diretório TDB onde serão armazenados os dados
          String tdbDirectory = "data/tdb";
          
          // Verificar o primeiro argumento para modo de execução
          String mode = args.length > 0 ? args[0] : "serve";
          
          if ("build".equals(mode)) {
              // Modo de construção do banco TDB
              System.out.println("Modo: Construção do banco de dados TDB");
              TDBBuilder builder = new TDBBuilder(tdbDirectory);
              builder.buildTDB();
          } else if ("serve".equals(mode)) {
              // Modo de servidor HTTP
              System.out.println("Modo: Servidor HTTP");
              int port = args.length > 1 ? Integer.parseInt(args[1]) : 8080;
              LinkedDataServer server = new LinkedDataServer(tdbDirectory, port);
              server.start();
          } else {
              // Modo desconhecido
              System.out.println("Modo desconhecido: " + mode);
              printUsage();
          }
      } catch (Exception e) {
          System.err.println("Erro: " + e.getMessage());
          e.printStackTrace();
          System.exit(1);
      }
  }
  
  /**
   * Imprime as instruções de uso da aplicação
   */
  private static void printUsage() {
      System.out.println("Uso: java -jar industrial-bench-semantic.jar [modo] [porta]");
      System.out.println("Modos:");
      System.out.println("  build  - Constrói o banco de dados TDB");
      System.out.println("  serve  - Inicia o servidor HTTP (padrão)");
      System.out.println("Porta:   - Porta HTTP (padrão: 8080)");
  }
}