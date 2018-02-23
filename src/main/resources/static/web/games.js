$.getJSON("http://localhost:8080/api/games", function (data) {
    console.log(data);
    tablaLeaderBoard(data);
});

function funcionLogIn(){
    // console.log("login");
    var usuarInput = document.getElementById("usernameID").value;
    var passInput = document.getElementById("passwordID").value;
    // console.log(usuarInput);
    // console.log(passInput);
    $.post("/api/login", { username: usuarInput, password: passInput }).done(function() {
        loginCorrecto();
    })
}

function loginCorrecto() {
    console.log("logged in!");
    document.getElementById("divLogin").style.display = "none";
    document.getElementById("divLogOut").style.display = "block";

}

function funcionLogOut(){
    $.post("/api/logout").done(function() { console.log("logged out"); })
    $("#divLogin").show();
    $("#divLogOut").hide();

}

function tablaLeaderBoard(data) {

    var contenidoLeaderBoard = "";
    var nombresJugadores = [];

// CONSEGUIR TODOS LOS JUGADORES QUITANDO LOS REPETIDOS////
    for (var i=0; i<data.games.length; i++) {
        nombresJugadores.push(data.games[i].gamePlayers[0].player.playerEmail);
        nombresJugadores.push(data.games[i].gamePlayers[1].player.playerEmail);
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

    for (var k=0; k<data.games.length; k++) {
        for (var l=0; l<2; l++) {
            // console.log(nombreJugador);
            if (data.games[k].gamePlayers[l].player.playerEmail == nombreJugador) {
                if (data.games[k].gamePlayers[l].score != "null") {
                    // console.log("diferente");
                    objetosJugadores.points += data.games[k].gamePlayers[l].score;
                }
                if (data.games[k].gamePlayers[l].score == "1") {
                    objetosJugadores.win++;
                }
                if (data.games[k].gamePlayers[l].score == "0") {
                    objetosJugadores.lost++;
                }
                if (data.games[k].gamePlayers[l].score == "0.5") {
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
        else if (a.points > b.points) {
            return -1;
        }
        else
        if (a.win< b.win)
            return -1;
        else if (a.win> b.win)
            return 1;
        else
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

// $.post("/api/login", { username: "j.bauer@ctu.gov", password: "24" }).done(function() { console.log("logged in!"); })

// $.post("/api/logout").done(function() { console.log("logged out"); })
