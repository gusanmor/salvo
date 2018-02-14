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

//
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
//     var fecha2 = new Date(data[i].Created);
//     var fecha3 = fecha2.toString();
//
//     contenidoFutbolMobile += '<div>_</div>'+
//         '<div class="fotarIzquierda">'+
//         '<ul>PARTIDA'+
//         '<li>'+'ID: '+data[i].ID+'</li>'+
//         '<li>' + 'Creado: '+ fecha3 + '</li>'+
//         '<ul>GAMEPLAYER'+
//         '<li>ID: '+data[i].gamePlayers["0"].id+'</li>'+
//         '<div>JUGADOR</div>'+
//         '<li>ID: '+data[i].gamePlayers["0"].players.id+'</li>'+
//         '<li>MAIL: '+data[i].gamePlayers["0"].players.email+'</li>'+
//         '</ul>'+
//         '<ul>GAMEPLAYER'+
//         '<li>ID: '+data[i].gamePlayers["1"].id+'</li>'+
//         '<div>JUGADOR</div>'+
//         '<li>ID: '+data[i].gamePlayers["1"].players.id+'</li>'+
//         '<li>MAIL: '+data[i].gamePlayers["1"].players.email+'</li>'+
//         '</ul>'+'</div>';
//
//     }
//
//    console.log(contenidoFutbolMobile);
//
    document.getElementById("tablaID").innerHTML = "<td>hola</td>";
//
}
