Agents in Charge
================

# Documentação da REST API

Documentação da REST API desenvolvida em **Node-RED**. Existem métodos **GET** para leitura dos estados dos sensores, e métodos **POST** para alteração do estado dos atuadores da bancada **SEPARATING**.

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
 
### POST `separating/mainConveyor`

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








