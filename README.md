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
   ```

4. Execute o comando para inicializar o projeto:  
   ```bash  
   sudo docker compose up
   ```

Isso irá:

* Instalar automaticamente todas as dependências necessárias.
* Criar os containers Docker para todos os serviços.
* Configurar o ambiente para que o projeto rode em qualquer máquina.

## Serviços e Portas

Após a execução, os seguintes serviços estarão disponíveis:

- **WoT Server**: http://localhost:1880 - Interface de desenvolvimento de fluxos e API
- **Dashboard**: http://localhost:3000 - Interface web principal
- **Nginx**: http://localhost:80 - Servidor web e proxy
- **Semantic App**: http://localhost:8080 - Aplicação semântica

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

Para desenvolvimento, você pode usar o arquivo de configuração específico:

```bash
sudo docker compose -f docker-compose-dev.yml up
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
