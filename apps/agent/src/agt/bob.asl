// ========================================
// NAVEGAÇÃO POR ÁREAS - DESACOPLAMENTO
// ========================================

!processWorkpiece .

// Plano principal - inicia pela área de entrada
+!processWorkpiece <-
    ?entryPoint(URI) ;
    !crawl(URI);
    .print("Iniciando processamento de peça...");
    !findSystemByType("https://ci.mines-stetienne.fr/kg/ontology#ProductionLine", ProductionLine);
    !findMainProcessingSystem(ProductionLine, MainSystem); // Encontra bancada Separating
    !startProcessingFlow(MainSystem);
.

// Encontra sistema por tipo RDF
+!findSystemByType(Type, System) <-
    ?type(System, Type);
    .print("Sistema encontrado: ", System);
.

// Encontra o sistema principal de processamento
+!findMainProcessingSystem(ProductionLine, MainSystem) <-
    ?hasSubSystem(ProductionLine, MainSystem);
    ?type(MainSystem, "http://www.w3.org/ns/sosa/Platform");
    .print("Sistema principal: ", MainSystem);
.

// Inicia o fluxo de processamento navegando pelas áreas
+!startProcessingFlow(System) <-
    .print("Iniciando fluxo de processamento...");
    !findConveyorInSystem(System, MainConveyor);
    ?inputArea(MainConveyor, InputArea);
    !waitForWorkpieceArrival(InputArea);
    !processWorkpieceInSystem(System, MainConveyor);
    .print("Fluxo concluído!");
    !startProcessingFlow(System);
.

// ========================================
// OPERAÇÕES BASEADAS EM ÁREAS
// ========================================

// Aguarda chegada de peça na área de entrada
+!waitForWorkpieceArrival(InputArea) <-
    .print("Aguardando peça na área: ", InputArea);
    !findPresencePropertyInArea(InputArea, PresenceProperty);
    !findSensorForProperty(PresenceProperty, Sensor);
    !monitorPresence(Sensor, true);
    .print("Peça detectada na entrada!");
.

// Aguarda saída de peça da área de saída
+!waitForWorkpieceExit(OutputArea) <-
    .print("Aguardando saída da peça...");
    !findPresencePropertyInArea(OutputArea, PresenceProperty);
    !findSensorForProperty(PresenceProperty, Sensor);
    !monitorPresence(Sensor, true);
    .print("Peça detectada na saída!");
    .wait(1000); // Aguarda peça sair completamente
    !monitorPresence(Sensor, false);
    .print("Peça saiu do sistema!");
.

// ========================================
// DESCOBERTA DE SENSORES, ATUADORES E PROPRIEDADES
// ========================================

// Encontra propriedade de presença em uma área
+!findPresencePropertyInArea(Area, PresenceProperty) <-
    ?hasProperty(Area, PresenceProperty);
    ?type(PresenceProperty, "https://ci.mines-stetienne.fr/kg/ontology#PresenceProperty");
    .print("Propriedade de presença: ", PresenceProperty);
.

// Encontra sensor que observa uma propriedade
+!findSensorForProperty(Property, Sensor) <-
    ?isObservedBy(Property, Sensor);
    ?sensor(Sensor);
    .print("Sensor encontrado: ", Sensor);
.

// Encontra atuador que controla uma propriedade
+!findActuatorForProperty(Property, Actuator) <-
    ?hasProperty(Property, Actuator);
    ?actuator(Actuator);
    .print("Atuador encontrado: ", Actuator);
.

// Monitora presença até atingir estado desejado
+!monitorPresence(Sensor, DesiredState) <-
    !readProperty(Sensor, CurrentState);
    .print("Estado atual: ", CurrentState, " | Desejado: ", DesiredState);
    
    if (CurrentState == DesiredState) {
        .print("Estado desejado atingido!");
    } else {
        .wait(200);
        !monitorPresence(Sensor, DesiredState);
    }
.

// ========================================
// PROCESSAMENTO NO SISTEMA
// ========================================

// Processa peça dentro do sistema
+!processWorkpieceInSystem(System, Conveyor) <-
    .print("Processando peça no sistema: ", System);
    !invokeAction(Conveyor, true);
    
    // Verifica se tem sistema de verificação de orientação
    !findOrientationSystem(System, OrientationSystem);
    !performOrientationCheck(OrientationSystem, Result); // Verifica se a peça está na posição correta
    
    // Executa ação de descarte, se necessário
    if (Result == true) {
        !discardWorkpiece(System, Result);
    } else {
        ?outputArea(Conveyor, OutputArea);
        !waitForWorkpieceExit(OutputArea);
    }
    !invokeAction(Conveyor, false);
.

+!discardWorkpiece(System, Result) <-
    !findDiscardSystem(System, DiscardSystem);
    ?inputArea(DiscardSystem, InputArea);
    !findDiscardDiverter(InputArea, DiscardDiverter);

    !invokeAction(DiscardDiverter, true);
    .print("Diverter ativado!");

    !findDiscardConveyor(DiscardSystem, DiscardConveyor);
    !invokeAction(DiscardConveyor, true);
    .print("Esteira de descarte ativada!");

    ?outputArea(DiscardSystem, OutputArea);
    !waitForWorkpieceExit(OutputArea);
    .print("Peça descartada!");

    !invokeAction(DiscardConveyor, false);
    .print("Esteira de descarte desativada!");

    !invokeAction(DiscardDiverter, false);
    .print("Diverter desativado!");
.

+!findDiscardDiverter(InputArea, DiscardDiverter) <-
    ?hasProperty(InputArea, DiverterStatus)
    & actsUpon(Command, DiverterStatus)
    & hasCommand(DiscardFunction, Command) 
    & accomplishes(DiscardDiverter, DiscardFunction) ;
.

+!findDiscardConveyor(DiscardSystem, DiscardConveyor) <-
    ?hasSubSystem(DiscardSystem, DiscardConveyor)
    & conveyor(DiscardConveyor) ;
    .print("Esteira de descarte: ", DiscardConveyor);
.

// Encontra esteira principal no sistema
+!findConveyorInSystem(System, Conveyor) <-
    ?hasSubSystem(System, Conveyor) & conveyor(Conveyor);
    .print("Esteira encontrada: ", Conveyor);
.

// Encontra sistema de verificação de orientação
+!findOrientationSystem(System, OrientationSystem) <-
    ?hasSubSystem(System, OrientationSystem)  
    & hosts(OrientationSystem, OrientationSensor)
    & observes(OrientationSensor, OrientationProperty)
    & midpointHeight(OrientationProperty) ; // Encontra o subsistema que possui um sensor que observa orientação
    .print("Sistema de orientação: ", OrientationSystem);
.

+!findDiscardSystem(System, DiscardSystem) <-
    ?hasSubSystem(System, DiscardSystem) 
    & hasSubSystem(DiscardSystem, DiscardConveyor)
    & conveyor(DiscardConveyor) ;
    .print("Sistema de descarte: ", DiscardSystem);
.

+!performOrientationCheck(OrientationSystem, Result) <-
    .print("Verificando orientação da peça...");
    !findLock(OrientationSystem, Lock);
    !invokeAction(Lock, true);
    .print("Trava ativada!");

    !findStoppedSensor(OrientationSystem, StoppedSensor);
    !monitorPresence(StoppedSensor, true);
    .print("Peça na posição de leitura.");

    .wait(500);

    !findOrientationSensor(OrientationSystem, OrientationSensor);
    !readProperty(OrientationSensor, Result);
    if (Result == true) {
        .print("Peça é alta, não passou no teste.");
    } else {
        .print("Peça é da altura correta, passou no teste.");
    }
    !invokeAction(Lock, false);
    .print("Trava desativada!");
.

+!findLock(OrientationSystem, Lock) <-
    ?hosts(OrientationSystem, Lock) 
    & actuator(Lock)
    & accomplishes(Lock, "http://localhost/kg/MPSFesto/Separating/OrientationVerificationSystem/LockingFunction/") ;
    .print("Lock encontrado: ", Lock);
.

+!findStoppedSensor(OrientationSystem, StoppedSensor) <-
    ?hosts(OrientationSystem, StoppedSensor) 
    & presenceSensor(StoppedSensor) ;
    .print("Sensor de parada encontrado: ", StoppedSensor);
. 

+!findOrientationSensor(OrientationSystem, OrientationSensor) <-
    ?hosts(OrientationSystem, OrientationSensor) 
    & sensor(OrientationSensor) 
    & observes(OrientationSensor, OrientationProperty)
    & midpointHeight(OrientationProperty) ;
    .print("Sensor de orientação encontrado: ", OrientationSensor);
.

// ========================================
// AFFORDANCES GENÉRICAS
// ========================================

// Lê valor de propriedade via affordance
+!readProperty(Thing, Value)
    : thing(Thing)
    & hasPropertyAffordance(Thing, PropertyAffordance)
    & hasForm(PropertyAffordance, Form)
    & hasTarget(Form, URI)
    <-
    HEADERS = [kv("Content-Type","application/json")];
    get(URI, HEADERS);
    ?(json(Payload)[source(URI)]) ;
    .member(kv(value, Value), Payload) .

+!invokeAction(Thing, Value)
    : thing(Thing)
    & hasActionAffordance(Thing, ActionAffordance)
    & hasForm(ActionAffordance, Form)
    & hasTarget(Form, URI)
    <-
    VALOR = [kv("value", Value)];
    HEADERS = [kv("Content-Type", "application/json")];

    post(URI, [json(VALOR)], HEADERS) .

// ========================================

+!crawl(URI)
    <-
    get(URI) ;
    +crawled(URI) ;
    for (system(S) & hasSubSystem(S, SS)) {
        h.target(SS, TargetURI) ;
        if (not crawled(TargetURI) & not .intend(crawl(TargetURI))) {
            !crawl(TargetURI)
        }
    } .

-!crawl(URI)
    <-
    .print("Couldn't crawl ", URI, ". Giving up.") ;
    +crawled(URI) .

// ========================================
// CRENÇAS E REGRAS (mantidas do original)
// ========================================

type(Individual, Class) :-
    rdf(Individual, "http://www.w3.org/1999/02/22-rdf-syntax-ns#type", Class).

system(Individual) :- 
    type(Individual, "http://www.w3.org/ns/ssn/System").

thing(Individual) :- 
    type(Individual, "https://www.w3.org/2019/wot/td#Thing").

actuator(Individual) :- 
    type(Individual, "http://www.w3.org/ns/sosa/Actuator").

sensor(Individual) :- 
    type(Individual, "http://www.w3.org/ns/sosa/Sensor").

presenceSensor(Individual) :- 
    type(Individual, "https://ci.mines-stetienne.fr/kg/ontology#PresenceSensor").

sensor(Individual) :- presenceSensor(Individual).

conveyor(Individual) :- 
    type(Individual, "http://localhost/kg/vocabulary/Conveyor").    

midpointHeight(Individual) :- 
    type(Individual, "http://localhost/kg/vocabulary/MidpointHeight").

hasSubSystem(Individual1, Individual2) :-
    rdf(Individual1, "http://www.w3.org/ns/ssn/hasSubSystem", Individual2).

hosts(Individual1, Individual2) :-
    rdf(Individual1, "http://www.w3.org/ns/sosa/hosts", Individual2).

hasPropertyAffordance(Individual1, Individual2) :-
    rdf(Individual1, "https://www.w3.org/2019/wot/td#hasPropertyAffordance", Individual2).

hasActionAffordance(Individual1, Individual2) :-
    rdf(Individual1, "https://www.w3.org/2019/wot/td#hasActionAffordance", Individual2).

hasForm(Individual1, Individual2) :-
    rdf(Individual1, "https://www.w3.org/2019/wot/td#hasForm", Individual2).

hasTarget(Individual1, Individual2) :-
    rdf(Individual1, "https://www.w3.org/2019/wot/hypermedia#hasTarget", Individual2).

// Crenças adicionais para navegação por áreas
hasProperty(Individual1, Individual2) :-
    rdf(Individual1, "http://www.w3.org/ns/ssn/hasProperty", Individual2).

isObservedBy(Individual1, Individual2) :-
    rdf(Individual1, "http://www.w3.org/ns/sosa/isObservedBy", Individual2).

observes(Individual1, Individual2) :-
    rdf(Individual1, "http://www.w3.org/ns/sosa/observes", Individual2).

inputArea(Individual1, Individual2) :-
    rdf(Individual1, "https://ci.mines-stetienne.fr/kg/ontology#inputArea", Individual2).

outputArea(Individual1, Individual2) :-
    rdf(Individual1, "https://ci.mines-stetienne.fr/kg/ontology#outputArea", Individual2).

accomplishes(Individual1, Individual2) :-
    rdf(Individual1, "https://saref.etsi.org/core/accomplishes", Individual2).

actsUpon(Individual1, Individual2) :-
    rdf(Individual1, "https://saref.etsi.org/core/actsUpon", Individual2).

hasCommand(Individual1, Individual2) :-
    rdf(Individual1, "https://saref.etsi.org/core/hasCommand", Individual2).

{ include("$jacamoJar/templates/common-cartago.asl") }