// console.log("prueba");

$.getJSON("http://localhost:8080/api/games", function (data) {

    	// console.log(data);
    tablaLeaderBoard(data);

});

function tablaLeaderBoard(data) {

    console.log(data);
    // console.log(data[0].gamePlayers[1].score);

// }
//

    var contenidoLeaderBoard = "";
    var nombresJugadores = [];

// CONSEGUIR TODOS LOS JUGADORES QUITANDO LOS REPETIDOS////
    for (var i=0; i<data.length; i++) {

        nombresJugadores.push(data[i].gamePlayers[0].player.playerEmail);
        nombresJugadores.push(data[i].gamePlayers[1].player.playerEmail);

    }
    // console.log(nombresJugadores);

    var nomJugNoRepetidos = [];
    $.each(nombresJugadores, function(i, el){
        if($.inArray(el, nomJugNoRepetidos) === -1) nomJugNoRepetidos.push(el);
    });

    console.log(nomJugNoRepetidos);


    arrayObjJugadores = [];
    for (var j=0; j<nomJugNoRepetidos.length; j++) {
        objetosJugadores = {};
        // console.log("hola");
        objetosJugadores.name = nomJugNoRepetidos[j];
        objetosJugadores.points = cogerPuntosJugador(data, nomJugNoRepetidos[j]);
        // objetosJugadores.won = cogerWonJugador(data, nomJugNoRepetidos[j]);
        // console.log(nomJugNoRepetidos[j]);
        arrayObjJugadores.push(objetosJugadores);

    }
    ordenarMembers(arrayObjJugadores);
    console.log(arrayObjJugadores);

    document.getElementById("tablaID").innerHTML = "<td>hola</td>";
//
}

// cogerPuntosJugador("j.bauer@ctu.gov");

function cogerPuntosJugador(data, nombreJugador){

    var puntosJugador = 0.0;

    for (var k=0; k<data.length; k++) {
        for (var l=0; l<2; l++) {
            console.log(nombreJugador);
            if (data[k].gamePlayers[l].player.playerEmail == nombreJugador) {
                if (data[k].gamePlayers[l].score != "null") {
                    // console.log("diferente");
                    puntosJugador += data[k].gamePlayers[l].score;
                }
            }
        }
    }
    return puntosJugador;
}

function ordenarMembers(data) {
    return data.sort(function (a, b) {
        if (a.points < b.points) {
            return 1;
        }
        if (a.points > b.points) {
            return -1;
        }
        // a must be equal to b
        return 0;
    });

}