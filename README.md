Agents in Charge
================

Este repositório contém um sistema de gerenciamento para a bancada **separating**, utilizando Node-RED no backend e Next.js no frontend.  

## Estrutura do Projeto  

- **`apps/wot-server/`**  
  Contém o backend desenvolvido em Node-RED, que fornece uma REST API para a interação com a bancada **separating**.  

- **`apps/dashboard/`**  
  Contém o frontend desenvolvido em Next.js, oferecendo uma interface gráfica para monitoramento e controle.  

- **`apps/td/`**  
  Contém a **Thing Description (TD)**, que define como os agentes irão consumir a API REST do sistema.  

- **`apps/semantic/`**  
  Contém a aplicação semântica responsável pela descrição dos componentes da bancadas FESTO para utilização dos agentes BDI. 

- **`apps/nginx/`**  
  Contém a configuração do servidor web Nginx para proxy reverso.

- **`apps/agent/`**
  Contém a programação do agente de software que controla a planta, utilizando a plataforma JaCaMo.

## Requisitos  

- **Docker**: Certifique-se de que o Docker está instalado e configurado em sua máquina.  
- **Docker Compose**: Para orquestração dos containers.

## Como Executar  

1. Clone este repositório:  
   ```bash  
   git clone <URL_DO_REPOSITORIO>
   ```

2. Acesse o diretório do projeto:  
   ```bash  
   cd agents-in-charge
   ```

3. Configure as variáveis de ambiente:
   ```bash
   cp .env.example .env
   # Edite o arquivo .env com suas configurações
   # O conteúdo atual já é suficiente para rodar em desenvolvimento
   ```

4. Execute o comando para inicializar o projeto:  
   ```bash  
   sudo docker compose --profile dev up
   ```

Isso irá:

* Instalar automaticamente todas as dependências necessárias.
* Criar os containers Docker para todos os serviços, incluindo o simulador OPC-UA da bancada.
* Configurar o ambiente para que o projeto rode em qualquer máquina.

## Serviços e Portas

A configuração do Nginx permite que todos os serviços sejam acessados através da port 80. Dessa forma, cada serviço possui seu próprio endpoint:

- **WoT Server**: http://localhost/api - REST API
- **Node RED**: http://localhost/node-red - Acessa os fluxos do node-red
- **Dashboard**: http://localhost/ - Interface web principal
- **Semantic App**: http://localhost/kg - Aplicação semântica

## Desenvolvimento

Será necessário criar um arquivo de variáveis de ambiente (`.env.dev`), com o seguinte conteúdo:
```bash
# Development Environment Variables
API_AUTHORIZATION_TOKEN=dev-api-token-here

# OPC-UA endpoints for development  
opc_endpoint_separating=opc.tcp://opc-simulator:4840

# Development specific settings
NODE_ENV=development
```

Para desenvolvimento, você pode usar o perfil dev do docker compose:

```bash
sudo docker compose --profile dev up -d
```

## Construção do Grafo Semântico

Para construir o grafo semântico, execute:

```bash
sudo docker compose run --rm semantic-builder
```

## Contribuição

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## Licença

Este projeto está sob a licença [MIT](LICENSE).

---
