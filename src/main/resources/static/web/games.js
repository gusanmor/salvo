$.getJSON("http://localhost:8080/api/games", function (data) {
    console.log(data);
    crearTablaGames(data);
    tablaLeaderBoard(data);
    ocultarSiLog(data);
});

function ocultarSiLog(data){
    if (data.playerLogueado.name!="NombreSinLog"){
        loginCorrecto(data.playerLogueado.name);
    }
}

function funcionLogIn(){
    var usuarInput = document.getElementById("usernameID").value;
    var passInput = document.getElementById("passwordID").value;
    $.post("/api/login", { username: usuarInput, password: passInput }).done(function() {
        location.reload();
        loginCorrecto(usuarInput);
    }).fail(function(response) {
        alert("Log in error: "+response.responseJSON.error);
    });
}

function funcionSingIn(){
    var usuarInput = document.getElementById("usernSignInID").value;
    var passInput = document.getElementById("passwSignInID").value;
    $.post("/api/players", { username: usuarInput, password: passInput }).done(function() {
        console.log("sign in");
        document.getElementById("usarCreatID").innerHTML = "<p>User "+usuarInput+ " has been created, please Log In</p>";
        $("#signIn").hide();
}).fail(function(response) {
    console.log(response);
        alert("Sign in error: "+response.responseJSON.error);
    });
}

function loginCorrecto(nombLogPar) {
    console.log("logged in!");
    document.getElementById("divLogin").style.display = "none";
    document.getElementById("divLogOut").style.display = "block";
    document.getElementById("welcUsuar").innerHTML = "<p>Welcome "+nombLogPar+"</p>";
    $("#usarCreatID").hide();
    $("#crearGameID").show();
    $("#signIn").hide();
}

function funcionLogOut(){
    $.post("/api/logout").done(function() {
        location.reload();
        console.log("logged out");
    $("#divLogin").show();
    $("#divLogOut").hide();
    $("#welcUsuar").hide();
    })
}

function crearGame(){
    $.post("/api/games").done(function(response) {
        console.log(response);
        window.location.assign("/web/game.html?gp="+response);
    }).fail(function(response) {
        alert(response.responseText);
    })
}

function joinGameBoton(botGameID){
    $.post("/api/game/"+botGameID+"/players").done(function(response) {
        window.location.assign("/web/game.html?gp="+response);
    }).fail(function(response) {
        alert(response.responseText);
    })
}

function tablaLeaderBoard(data) {
    var contenidoLeaderBoard = "";
    var nombresJugadores = [];

// CONSEGUIR TODOS LOS JUGADORES QUITANDO LOS REPETIDOS////
    for (var i=0; i<data.games.length; i++) {
        if (data.games[i].gamePlayers[0] != null) {
            nombresJugadores.push(data.games[i].gamePlayers[0].player.playerEmail);
        }
        if (data.games[i].gamePlayers[1] != null) {
            nombresJugadores.push(data.games[i].gamePlayers[1].player.playerEmail);
        }
    }
    var nomJugNoRepetidos = [];
    $.each(nombresJugadores, function(i, el){
        if($.inArray(el, nomJugNoRepetidos) === -1) nomJugNoRepetidos.push(el);
    });

    arrayObjJugadores = [];
    for (var j=0; j<nomJugNoRepetidos.length; j++) {
        arrayObjJugadores.push(cogerPuntosJugador(data, nomJugNoRepetidos[j]));
    }
    ordenarMembers(arrayObjJugadores);
    rellenarTabla(arrayObjJugadores);
}

function crearTablaGames(data){
    var tablaGames = "";
    for (var p=0; p<data.games.length; p++) {

        var creatDate = new Date(data.games[p].gameCreated);

        var horaSinSubstring = String(creatDate);

        var horaConSubstring = horaSinSubstring.substring(0, 21);
        var boton1 = "";
        var boton2 = "";
        var playerEmail1 = "NO_PLAYER";
        var playerEmail2 = "NO_PLAYER";
        if (data.games[p].gamePlayers[0] != null){
            playerEmail1 = data.games[p].gamePlayers[0].player.playerEmail;
        }
        if (data.games[p].gamePlayers[1] != null) {
            playerEmail2 = data.games[p].gamePlayers[1].player.playerEmail;
        }
        var gamePlayerGP1 ="";
        var gamePlayerGP2 ="";
        if (data.games[p].gamePlayers[0] !=null){
            gamePlayerGP1 = data.games[p].gamePlayers[0].gamePlayerID;
        }
        if (data.games[p].gamePlayers[1] !=null) {
            gameplayerGP2 = data.games[p].gamePlayers[1].gamePlayerID;
        }
        if (playerEmail1==data.playerLogueado.name){
            boton1 = '<a class="btn btn-primary" href="/web/game.html?gp='+gamePlayerGP1+'">GO TO GAME</a></td>';
        }
        if (playerEmail2==data.playerLogueado.name){
            boton2 = '<a class="btn btn-primary" href="/web/game.html?gp='+gameplayerGP2+'">GO TO GAME</a></td>';
        }
        if (playerEmail2=="NO_PLAYER" && data.playerLogueado.name !="NombreSinLog" && data.playerLogueado.name != playerEmail1) {
            boton2 = '<button class="btn btn-info" id="" onclick="joinGameBoton('+data.games[p].gameID+')">JOIN GAME</button>';
        }
        tablaGames += "<tr>"+
            "<td>"+data.games[p].gameID+"</td>"+
            "<td>"+horaConSubstring+"</td>"+
            "<td>"+playerEmail1+boton1+"</td>"+
            "<td>"+playerEmail2+boton2+"</td>"+
            "</tr>";
    }
    document.getElementById("tablaGamesID").innerHTML =tablaGames;
}

function cogerPuntosJugador(data, nombreJugador){
    objetosJugadores = {};
    objetosJugadores.name = nombreJugador;
    objetosJugadores.points = 0.0;
    objetosJugadores.win = 0;
    objetosJugadores.lost = 0;
    objetosJugadores.tied = 0;

    // var puntosJugador = 0.0;
    // var won = 0;

    for (var k=0; k<data.games.length; k++) {
        for (var l=0; l<2; l++) {
            if (data.games[k].gamePlayers[l] != null) {
                if (data.games[k].gamePlayers[l].player.playerEmail == nombreJugador) {
                    if (data.games[k].gamePlayers[l].score != "null") {
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