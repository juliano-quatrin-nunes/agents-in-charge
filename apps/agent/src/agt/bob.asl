!test .

+!test <-
    ?entryPoint(URI) ;
    !crawl(URI) ;
    !listThings ;
   // !listPropertyAffordances(separating) ;
    //!listarLabelsAffordancesDe("http://localhost/kg/MPSFesto/Separating/MainConveyor/");

   // !lerStatusEsteira("http://host.docker.internal:8080/kg/MPSFesto/Separating/MainConveyor/");

    
    .print("----------------------------------------------------------------------------------------------");
    .print("Arrival");
    !readProperty("http://localhost/kg/MPSFesto/Separating/MainConveyor/ArrivalSensor/", "https://ci.mines-stetienne.fr/kg/ontology#PresenceSensor", Presence) ;
    .print("Valor = ", Presence) ;

    if(Presence == true){
        .print("Presença confirmada");

        !invokeAction(
            "http://localhost/kg/MPSFesto/Separating/MainConveyor/", "http://localhost/kg/vocabulary/Conveyor",
            true
        ) ;
        .wait(1000);

        !invokeAction(
            "http://localhost/kg/MPSFesto/Separating/OrientationVerificationSystem/Lock/", "https://www.w3.org/2019/wot/td#Thing",
            true
        ) ;
        .wait(1000);

        !invokeAction(
            "http://localhost/kg/MPSFesto/Separating/MainConveyor/", "http://localhost/kg/vocabulary/Conveyor",
            false
        ) ;

        !readProperty("http://localhost/kg/MPSFesto/Separating/OrientationVerificationSystem/OrientationSensor/", "https://www.w3.org/2019/wot/td#Thing", Orientation) ;
        .print("AQUIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII", Orientation);
        if(Orientation == true){

        .print("Peça alta");
        .wait(1000);

        !invokeAction(
            "http://localhost/kg/MPSFesto/Separating/DiscardSystem/DiscardConveyor/", "http://www.w3.org/ns/sosa/Platform",
            true
        ) ;

        } ;

        .wait(2000);
        .print("Continuar processo...");

        !invokeAction(
                "http://localhost/kg/MPSFesto/Separating/DiscardSystem/DiscardConveyor/", "http://www.w3.org/ns/sosa/Platform",
                false
            ) ;

        !invokeAction(
            "http://localhost/kg/MPSFesto/Separating/OrientationVerificationSystem/Lock/", "https://www.w3.org/2019/wot/td#Thing",
            false
        ) ;
        .wait(1000);


        !invokeAction(
            "http://localhost/kg/MPSFesto/Separating/MainConveyor/", "http://localhost/kg/vocabulary/Conveyor",
            true
        ) ;

        !verificaSaida;
    }

    .wait(3000);
    !test .


+!verificaSaida <-
    .wait(1000);

    !readProperty("http://localhost/kg/MPSFesto/Separating/MainConveyor/ExitSensor/", "https://ci.mines-stetienne.fr/kg/ontology#PresenceSensor", Saida) ;

    if(Saida == true){
        .wait(3000);

            !invokeAction(
            "http://localhost/kg/MPSFesto/Separating/MainConveyor/", "http://localhost/kg/vocabulary/Conveyor",
            false
        ) ;
        !test;
    }

    !verificaSaida;
.
+!lerStatusEsteira(ThingURI)
    : hasPropertyAffordance(ThingURI, Aff)
    & hasForm(Aff, F)
    & hasTarget(F, TargetURI)
    <- 
    .print("🔍 Lendo status de: ", ThingURI);
    !prepareForm(Fp);
    get(TargetURI, Fp);
    ?(json(Val)[source(TargetURI)]);
    .print("✅ Status da esteira: ", Val).





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

+!listThings <- .print("Things:") ; for (thing(T)) { .print("* ", T) } .




    
+!listarLabelsAffordancesDe(T) <-
    .print("🔍 Affordances (labels) de: ", T);
    for (hasPropertyAffordance(T, Af)) {
        if (hasForm(Af, F)) {
            .print("  • Label: ", F);
            .print("  • Affordance: ", Af);
            if(hasTarget(F, TargetURI)){
            .print("  • F: ", F);
            .print("  • Target: ", TargetURI);
        };
        } else {
            .print("  • (sem 'label' definido) ↳ ", Af)
        }
    }.

+!readProperty(T, OT, Valor)
    : type(T, OT)
    & hasPropertyAffordance(T, Af)
    & hasForm(Af, F)
    & hasTarget(F, URI)
    <-
    HEADERS = [kv("Content-Type","application/json")];
    get(URI, HEADERS);
    ?(json(Payload)[source(URI)]) ;
    .member(kv(value, Val), Payload);
    Valor = Val;
    .

/*
+!readProperty(T)
    : hasPropertyAffordance(T, Af)
    & hasForm(Af, F)
    & hasTarget(F, URI)
    <-
    .replace(URI, "localhost", "host.docker.internal", NewURI);
    !prepareForm(Fp) ;
    HEADERS = [kv("Content-Type","application/json")];
    .print("➡️ GET em ", NewURI);
    get(NewURI, HEADERS);
    ?(json(Payload)[source(NewURI)]) ;
    .member(kv(value, Val), Payload);
    .print("Valor = ", Val) 
    .
*/
+!writeProperty(TType, PType, Val)
    : type(T, TType)
    & hasPropertyAffordance(T, Af)
    & type(Af, PType)
    & hasForm(Af, F)
    & hasTarget(F, URI)
    <-
    !prepareForm(Fp) ;
    put(URI, [json(Val)], Fp) .

+!invokeAction(T, In)
    : hasActionAffordance(T, Af)
    & hasForm(Af, F)
    & hasTarget(F, URI)
    <-

    //!prepareForm(Pf);

    VALOR = [kv("value", In)];
    HEADERS = [kv("Content-Type", "application/json")];


    post(URI, [json(VALOR)], HEADERS)
     
     
     .


+!invokeAction(T, OT, In)
    : type(T, OT)
    & hasActionAffordance(T, Af)
    & hasForm(Af, F)
    & hasTarget(F, URI)
    <-

    //!prepareForm(Pf);

    VALOR = [kv("value", In)];
    HEADERS = [kv("Content-Type", "application/json")];

    post(URI, [json(VALOR)], HEADERS)
     
     
     .


+!prepareForm(F) : credentials(User, Pw)
    <-
    h.basic_auth_credentials(User, Pw, H) ;
    F = [kv("urn:hypermedea:http:authorization", H)] .

        //pode ter problema aqui (preciso testar com algo que retorna um valor ou um action)

+!prepareForm(F) : not(credentials(_, _))
    <- 
    .print("⚠️  Nenhuma credencial definida, usando form vazio.");
    F = [kv("Content-Type", "application/json")].


type(Individual, Class)
    :-
    rdf(
        Individual,
        "http://www.w3.org/1999/02/22-rdf-syntax-ns#type",
        Class
    ) .

system(Individual) :- type(Individual, "http://www.w3.org/ns/ssn/System") .
thing(Individual) :- type(Individual, "https://www.w3.org/2019/wot/td#Thing") .

automated_storage_and_retrieval_system(Individual)
    :-
    type(
        Individual,
        "http://www.productontology.org/id/Automated_storage_and_retrieval_system"
    ) .

type(Individual, automated_storage_and_retrieval_system)
    :-
    automated_storage_and_retrieval_system(Individual) .



separating(Individual)
    :-
    type(
        Individual,
        "http://localhost/kg/MPSFesto/Separating/MainConveyor/"
    ) .

type(Individual, separating)
    :-
    separating(Individual) .

    


converyor(Individual)
    :-
    type(
        Individual,
        "http://localhost/kg/vocabulary/Conveyor"
    ) .

type(Individual, converyor)
    :-
    converyor(Individual) .



conveyorSpeed(Individual)
    :-
    type(
        Individual,
        "https://ci.mines-stetienne.fr/kg/ontology#ConveyorSpeed"
    ) .

type(Individual, conveyorSpeed) :- conveyorSpeed(Individual) .

moveFromToAction(Individual)
    :-
    type(
        Individual,
        "https://ci.mines-stetienne.fr/kg/ontology#MoveFromToAction"
    ) .

type(Individual, moveFromToAction) :- moveFromToAction(Individual) .

hasSubSystem(Individual1, Individual2)
    :-
    rdf(Individual1, "http://www.w3.org/ns/ssn/hasSubSystem", Individual2) .


type(Individual, Class) :-
    rdf(Individual, "http://www.w3.org/1999/02/22-rdf-syntax-ns#type", Class) .


hasPropertyAffordance(Individual1, Individual2)
    :-
    rdf(
        Individual1,
        "https://www.w3.org/2019/wot/td#hasPropertyAffordance",
        Individual2
    ) .


hasHosts(Individual1, Individual2)
    :-
    rdf(
        Individual1,
        "https://www.w3.org/ns/sosa/hosts",
        Individual2
    ) .

hasActionAffordance(Individual1, Individual2)
    :-
    rdf(
        Individual1,
        "https://www.w3.org/2019/wot/td#hasActionAffordance",
        Individual2
    ) .

//http://www.w3.org/2000/01/rdf-schema#label


name(Individual1, Individual2)
    :-
    rdf(Individual1, "https://www.w3.org/2019/wot/td#name", Individual2) .
    
hasForm(Individual1, Individual2)
    :-
    rdf(Individual1, "https://www.w3.org/2019/wot/td#hasForm", Individual2) .

hasTarget(Individual1, Individual2)
    :-
    rdf(
        Individual1,
        "https://www.w3.org/2019/wot/hypermedia#hasTarget",
        Individual2
    ) .

{ include("$jacamoJar/templates/common-cartago.asl") }  