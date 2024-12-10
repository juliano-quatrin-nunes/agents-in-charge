Agents in Charge
================

Este repositório contém um sistema de gerenciamento para a bancada **separating**, utilizando Node-RED no backend e Next.js no frontend.  

## Estrutura do Projeto  

- **`node-red/`**  
  Contém o backend desenvolvido em Node-RED, que fornece uma REST API para a interação com a bancada **separating**.  

- **`dashboard/`**  
  Contém o frontend desenvolvido em Next.js, oferecendo uma interface gráfica para monitoramento e controle.  

- **`td/`**  
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

* Criar um container Docker.
* Instalar automaticamente todas as dependências necessárias.
* Configurar o ambiente para que o projeto rode em qualquer máquina.

---

# Documentação da REST API

Documentação da REST API desenvolvida em **Node-RED**. Existem métodos **GET** para leitura dos estados dos sensores e atuadores, e métodos **POST** para alteração do estado dos atuadores da bancada **SEPARATING**.

---

## Main Conveyor

### GET `/separating/mainConveyor`

#### Descrição:
Obtém o estado atual da esteira principal da bancada Separating.

#### Response:
```json
{
  "message": "Estado obtido com sucesso.",
  "status": true
}
```
* message: Mensagem indicando o sucesso da requisiçao
* status: Estado atual da esteira principal
    * true para ligada
    * false para desligada
 
### POST `/separating/mainConveyor`

#### Descrição:
Altera o estado atual da esteira principal da bancada Separating.

#### Body:
O corpo da requisição deve ser enviado no formato JSON com a seguinte estrutura:

```json
{
  "status": true
}
```

* status:
    * true para ligar a esteira
    * false para desligar a esteira

#### Response:
```json
{
  "message": "Estado alterado com sucesso"
}
```

* message: Mensagem indicando o sucesso da operação

---

## Discard Conveyor

### GET `/separating/discardConveyor`

#### Descrição:
Obtém o estado atual da esteira de descarte de peças da bancada Separating.

#### Response:
```json
{
  "message": "Estado obtido com sucesso.",
  "status": true
}
```
* message: Mensagem indicando o sucesso da requisiçao
* status: Estado atual da esteira de descarte de peças
    * true para ligada
    * false para desligada
 
### POST `/separating/discardConveyor`

#### Descrição:
Altera o estado atual da esteira de descarte de peças da bancada Separating.

#### Body:
O corpo da requisição deve ser enviado no formato JSON com a seguinte estrutura:

```json
{
  "status": true
}
```

* status:
    * true para ligar a esteira
    * false para desligar a esteira

#### Response:
```json
{
  "message": "Estado alterado com sucesso"
}
```

* message: Mensagem indicando o sucesso da operação

---

## Lock

### GET `/separating/lock`

#### Descrição:
Obtém o estado atual da trava da bancada Separating.

#### Response:
```json
{
  "message": "Estado obtido com sucesso.",
  "status": true
}
```
* message: Mensagem indicando o sucesso da requisiçao
* status: Estado atual da trava
    * true para ligada
    * false para desligada
 
### POST `/separating/lock`

#### Descrição:
Altera o estado atual da trava da bancada Separating.

#### Body:
O corpo da requisição deve ser enviado no formato JSON com a seguinte estrutura:

```json
{
  "status": true
}
```

* status:
    * true para ativar a trava
    * false para desligar a trava

#### Response:
```json
{
  "message": "Estado alterado com sucesso"
}
```

* message: Mensagem indicando o sucesso da operação

---

## Discard Diverter

### GET `/separating/discardDiverter`

#### Descrição:
Obtém o estado atual do braço de descarte da bancada Separating.

#### Response:
```json
{
  "message": "Estado obtido com sucesso.",
  "status": true
}
```
* message: Mensagem indicando o sucesso da requisiçao
* status: Estado atual do braço de descarte
    * true para ligada
    * false para desligada
 
### POST `/separating/discardDiverter`

#### Descrição:
Altera o estado atual do braço de descarte da bancada Separating.

#### Body:
O corpo da requisição deve ser enviado no formato JSON com a seguinte estrutura:

```json
{
  "status": true
}
```

* status:
    * true para ativar o braço
    * false para desligar o braço

#### Response:
```json
{
  "message": "Estado alterado com sucesso"
}
```

* message: Mensagem indicando o sucesso da operação

---

## Arrival sensor

### GET `/separating/arrivalSensor`

#### Descrição:
Obtém o estado atual do sensor de chegada de peça da bancada Separating.

#### Response:
```json
{
  "message": "Estado obtido com sucesso.",
  "status": true
}
```
* message: Mensagem indicando o sucesso da requisiçao
* status: Estado atual do sensor
    * true para peça presente
    * false para peça ausente
 
---

## Stopped sensor

### GET `/separating/stoppedSensor`

#### Descrição:
Obtém o estado atual do sensor de parada de peça da bancada Separating.

#### Response:
```json
{
  "message": "Estado obtido com sucesso.",
  "status": true
}
```
* message: Mensagem indicando o sucesso da requisiçao
* status: Estado atual do sensor
    * true para peça presente
    * false para peça ausente
 
---

## Height sensor

### GET `/separating/heightSensor`

#### Descrição:
Obtém o estado atual do sensor de tamanho de peça da bancada Separating.

#### Response:
```json
{
  "message": "Estado obtido com sucesso.",
  "status": true
}
```
* message: Mensagem indicando o sucesso da requisiçao
* status: Estado atual do sensor
    * true para peça presente
    * false para peça ausente
 
---

## Discard sensor

### GET `/separating/discardSensor`

#### Descrição:
Obtém o estado atual do sensor de descarte de peça da bancada Separating.

#### Response:
```json
{
  "message": "Estado obtido com sucesso.",
  "status": true
}
```
* message: Mensagem indicando o sucesso da requisiçao
* status: Estado atual do sensor
    * true para peça presente
    * false para peça ausente
 
---

## Exit sensor

### GET `/separating/exitSensor`

#### Descrição:
Obtém o estado atual do sensor de descarte de peça da bancada Separating.

#### Response:
```json
{
  "message": "Estado obtido com sucesso.",
  "status": true
}
```
* message: Mensagem indicando o sucesso da requisiçao
* status: Estado atual do sensor
    * true para peça presente
    * false para peça ausente
 
---
