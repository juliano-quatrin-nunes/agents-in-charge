
!test .

+!test
    <-
    ?entryPoint(URI) ;
    get(URI) ;
    !listConveyors .

+!listConveyors 
    <- 
    .print("Conveyors:") ; 
    for (conveyor(T)) { 
        .print("* ", T)
    } .

type(Individual, Class)
    :-
    rdf(
        Individual,
        "http://www.w3.org/1999/02/22-rdf-syntax-ns#type",
        Class
    ) .

conveyor(Individual) :- type(Individual, "http://localhost/kg/vocabulary/Conveyor") .

{ include("$jacamoJar/templates/common-cartago.asl") }