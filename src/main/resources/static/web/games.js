// console.log("prueba");

$.getJSON("http://localhost:8080/api/games", function (data) {

    	// console.log(data);
    tablaLeaderBoard(data);

});

function tablaLeaderBoard(data) {

    console.log(data);
    console.log(data[0].gamePlayers[1].score);

// }
//

    var contenidoLeaderBoard = "";
    var nombresJugadores = [];

// CONSEGUIR TODOS LOS JUGADORES QUITANDO LOS REPETIDOS////
    for (var i=0; i<data.length; i++) {

        nombresJugadores.push(data[i].gamePlayers[0].player.playerEmail);
        nombresJugadores.push(data[i].gamePlayers[1].player.playerEmail);

    }
    console.log(nombresJugadores);

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
        objetosJugadores.points = j;
        console.log(nomJugNoRepetidos[j]);
        arrayObjJugadores.push(objetosJugadores);

    }
    // console.log(objetosJugadores);
    console.log(arrayObjJugadores);



    document.getElementById("tablaID").innerHTML = "<td>hola</td>";
//
}
