Agents in Charge
================

Este repositório contém um sistema de gerenciamento para a bancada **separating**, utilizando Node-RED no backend e Next.js no frontend.  

## Estrutura do Projeto  

- **`/apps/node-red/`**  
  Contém o backend desenvolvido em Node-RED, que fornece uma REST API para a interação com a bancada **separating**.  

- **`/apps/dashboard/`**  
  Contém o frontend desenvolvido em Next.js, oferecendo uma interface gráfica para monitoramento e controle.  

- **`/apps/td/`**  
  Contém a **Thing Description (TD)**, que define como os agentes irão consumir a API REST do sistema.  

## Requisitos  

- **Docker**: Certifique-se de que o Docker está instalado e configurado em sua máquina.  

## Como Executar  

1. Clone este repositório:  
   ```bash  
   git clone <URL_DO_REPOSITORIO>  
    ```

2. Acesse o diretório do projeto:  
   ```bash  
   cd <NOME_DO_REPOSITORIO>
    ```

3. Execute o comando para inicializar o projeto:  
   ```bash  
   sudo docker compose up
    ```

Isso irá

* Instalar automaticamente todas as dependências necessárias.
* Criar os containers Docker para todos os serviços.
* Configurar o ambiente para que o projeto rode em qualquer máquina.

---
