# Semantic Module

Este módulo contém a aplicação semântica responsável pela descrição dos componentes das bancadas FESTO para utilização dos agentes BDI.

## Visão Geral

A aplicação semantic utiliza Apache Jena para gerenciar dados RDF e fornece uma API HTTP, utilizando Jetty, para acesso do RDF gerado. O módulo possui dois modos de operação:

- **Build**: Constrói e popula o banco de dados TDB2 com dados semânticos
- **Serve**: Executa o servidor HTTP para consultas SPARQL

## Tecnologias Utilizadas

- **Java 23**: Linguagem de programação
- **Apache Jena**: Framework para dados RDF e consultas SPARQL
- **TDB2**: Banco de dados para armazenamento de triplas RDF
- **Jetty**: Servidor HTTP embarcado
- **Maven**: Gerenciamento de dependências

## Estrutura do Projeto

```
apps/semantic/
├── src/                    # Código fonte Java
├── data/                   # Dados persistentes
│   └── tdb/               # Banco de dados TDB2
├── pom.xml                # Configuração Maven
├── Dockerfile             # Configuração Docker
└── README.md              # Este arquivo
```

## Executando com Docker Compose

### Pré-requisitos

- Docker
- Docker Compose

### Construindo o Banco de Dados TDB2

Para construir e popular o banco de dados semântico:

```bash
# A partir do diretório raiz do projeto
docker compose run --rm semantic-builder
```

Este comando irá:
- Construir a imagem Docker do semantic
- Executar o processo de build que popula o banco TDB2
- Remover o container após a execução

### Executando o Servidor HTTP

Para iniciar o servidor HTTP que fornece a API SPARQL:

```bash
# A partir do diretório raiz do projeto
docker compose up semantic-app
```

O servidor estará disponível em: **http://localhost:8080**

### Executando Ambos os Serviços

Para executar tanto o build quanto o servidor:

```bash
# 1. Primeiro, construa o banco de dados
docker compose run --rm semantic-builder

# 2. Em seguida, inicie o servidor
docker compose up semantic-app
```

## Desenvolvimento Local

### Executando sem Docker

Se preferir executar localmente sem Docker:

```bash
# Navegar para o diretório semantic
cd apps/semantic

# Compilando o projeto
mvn clean compile

# Construir o banco de dados
mvn exec:java -Dexec.args="build"

# Executar o servidor
mvn exec:java -Dexec.args="serve"
```