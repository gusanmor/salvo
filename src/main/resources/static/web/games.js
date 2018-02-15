$.getJSON("http://localhost:8080/api/games", function (data) {
    console.log(data);
    tablaLeaderBoard(data);
});

function tablaLeaderBoard(data) {

    var contenidoLeaderBoard = "";
    var nombresJugadores = [];

// CONSEGUIR TODOS LOS JUGADORES QUITANDO LOS REPETIDOS////
    for (var i=0; i<data.length; i++) {
        nombresJugadores.push(data[i].gamePlayers[0].player.playerEmail);
        nombresJugadores.push(data[i].gamePlayers[1].player.playerEmail);
    }
    var nomJugNoRepetidos = [];
    $.each(nombresJugadores, function(i, el){
        if($.inArray(el, nomJugNoRepetidos) === -1) nomJugNoRepetidos.push(el);
    });
    // console.log(nomJugNoRepetidos);

    arrayObjJugadores = [];
    for (var j=0; j<nomJugNoRepetidos.length; j++) {

        arrayObjJugadores.push(cogerPuntosJugador(data, nomJugNoRepetidos[j]));

    }
    ordenarMembers(arrayObjJugadores);
    console.log(arrayObjJugadores);

    rellenarTabla(arrayObjJugadores);

}

function cogerPuntosJugador(data, nombreJugador){

    objetosJugadores = {};
    objetosJugadores.name = nombreJugador;
    objetosJugadores.points = 0.0;
    objetosJugadores.win = 0;
    objetosJugadores.lost = 0;
    objetosJugadores.tied = 0;

    var puntosJugador = 0.0;
    var won = 0;

    for (var k=0; k<data.length; k++) {
        for (var l=0; l<2; l++) {
            // console.log(nombreJugador);
            if (data[k].gamePlayers[l].player.playerEmail == nombreJugador) {
                if (data[k].gamePlayers[l].score != "null") {
                    // console.log("diferente");
                    objetosJugadores.points += data[k].gamePlayers[l].score;
                }
                if (data[k].gamePlayers[l].score == "1") {
                    objetosJugadores.win++;
                }
                if (data[k].gamePlayers[l].score == "0") {
                    objetosJugadores.lost++;
                }
                if (data[k].gamePlayers[l].score == "0.5") {
                    objetosJugadores.tied++;
                }
            }
        }
    }
    return objetosJugadores;
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

function rellenarTabla(parArrayObj){
    console.log("rellenarTablar");
    console.log(parArrayObj);
    varTablaLeader = "";

    for (var m=0; m<parArrayObj.length; m++) {
        console.log("long");
        varTablaLeader += '<tr>'+
            '<td>'+parArrayObj[m].name+'</td>'+
            '<td>'+parArrayObj[m].points+'</td>'+
            '<td>'+parArrayObj[m].win+'</td>'+
            '<td>'+parArrayObj[m].lost+'</td>'+
            '<td>'+parArrayObj[m].tied+'</td>'+
            '</tr>';
    }
    document.getElementById("tablaID").innerHTML = varTablaLeader;
}
