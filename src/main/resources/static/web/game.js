$.getJSON("http://localhost:8080/api/game_view/"+limpiarURL(document.location.search), function (data) {
    console.log(data);
    verPorStatus(data);
    crearRejiBarcosYsalvos("Barco");
    crearRejiBarcosYsalvos("Salvo");
    pintarBarcos(data);
    pintarHitsOnMe(data);
    pintarTusSalvos(data);
    pintarMisHits(data);
    crearJugadoresGV(data);
    crearTablaHitsOnYou(data);
    crearTablaHitsOpp(data);
    crearTablaSinksOn(data , "sinksOnMe");
    crearTablaSinksOn(data , "sinksOnOpponent");

});

function verPorStatus(data) {
    var status = data.gameStatus;
    if (status == "0-error") {
        $("#statusID").show();
        $("#jugadoresGamesviewID, #rejillaBarcosID, .allShips, #crearShipsID,.hitOnYouCl,.sinksOnYouCl,.hitOpponentCl,.sinksOnOpponCl,#rejillaSalvosID, #crearSalvosID, .instruCl").hide();
        document.getElementById("statusID").innerText="ERROR";
    }
    else if (status == "1-startPlaceShips") {
        $("#statusID, #jugadoresGamesviewID, #rejillaBarcosID, .allShips, #crearShipsID, .instruCl").show();
        $(".hitOnYouCl,.sinksOnYouCl,.hitOpponentCl,.sinksOnOpponCl,#rejillaSalvosID, #crearSalvosID").hide();
        document.getElementById("statusID").innerText="PLEASE, PLACE THE SHIPS";
    }
    else if (status == "2-noOpponent") {
        $("#statusID, #jugadoresGamesviewID, #rejillaBarcosID").show();
        $(".hitOnYouCl,.sinksOnYouCl,.hitOpponentCl,.sinksOnOpponCl,#rejillaSalvosID, #crearShipsID, #crearSalvosID, .allShips, .instruCl").hide();
        document.getElementById("statusID").innerText="WAITING FOR THE OPPONENT";
        setInterval(function () {
            reloadPage("2-noOpponent");
        }, 10000);
    }
    else if (status == "3-opponentNoShips") {
        $("#statusID, #jugadoresGamesviewID, #rejillaBarcosID").show();
        $(".hitOnYouCl,.sinksOnYouCl,.hitOpponentCl,.sinksOnOpponCl,#rejillaSalvosID, #crearShipsID, #crearSalvosID, .allShips, .instruCl").hide();
        document.getElementById("statusID").innerText="WAITING FOR THE OPPONENT TO PLACE THEIR SHIPS";
        setInterval(function () {
            reloadPage("3-opponentNoShips");
        }, 10000);
    }
    else if (status == "4-addSalvos" || status == "4-addSalvosMismoTurno") {
        $("#statusID, #jugadoresGamesviewID, #rejillaBarcosID, #rejillaSalvosID, #crearSalvosID, .hitOnYouCl,.sinksOnYouCl,.hitOpponentCl,.sinksOnOpponCl").show();
        $("#crearShipsID, .allShips, .instruCl").hide();
        document.getElementById("statusID").innerText="PLEASE PLACE YOUR SALVO (5 SHOTS)";
    }
    else if (status == "5-whaitOppSalvo") {
        $("#statusID, #jugadoresGamesviewID, #rejillaBarcosID, #rejillaSalvosID, .hitOnYouCl,.sinksOnYouCl,.hitOpponentCl,.sinksOnOpponCl").show();
        document.getElementById("statusID").innerText="WAITING FOR THE OPPONENT TO PLACE HIS SALVO";
        $("#crearShipsID, .allShips, #crearSalvosID, .instruCl").hide();
        setInterval(function () {
            reloadPage("5-whaitOppSalvo");
        }, 10000);
    }
    else if (status == "6-Tie") {
        document.getElementById("statusID").innerText="GAME OVER, TIE";
        tieWinLose();
    }

    else if (status == "7-YouLose") {
        document.getElementById("statusID").innerText="YOU LOSE";
        tieWinLose();
    }
    else if (status == "8-YouWin") {
        document.getElementById("statusID").innerText="YOU WIN";
        tieWinLose();
    }
}

function reloadPage(statusP) {
    $.getJSON("http://localhost:8080/api/game_view/"+limpiarURL(document.location.search), function (data) {
        if (statusP != data.gameStatus){
            window.location.reload();
        }
    });
}

function tieWinLose(){
    $("#statusID, #jugadoresGamesviewID, #rejillaBarcosID, #rejillaSalvosID, .hitOnYouCl,.sinksOnYouCl,.hitOpponentCl,.sinksOnOpponCl").show();
    $("#crearShipsID, .allShips, #crearSalvosID, .instruCl").hide();
}

function limpiarURL(search) {
    var obj = {};
    var reg = /(?:[?&]([^?&#=]+)(?:=([^&#]*))?)(?:#.*)?/g;

    search.replace(reg, function(match, param, val) {
        obj[decodeURIComponent(param)] = val === undefined ? "" : decodeURIComponent(val);
    });
    var obj2 = "";
    obj2 = obj.gp;
    return obj2;
}

function crearRejiBarcosYsalvos(barcoOsalvo) {
    var contenidoRejilla = "";
    arrayNumerosTabla = [" ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"];
    arrayLetrasTabla = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J"];

    // ----------CONSTRUIR TABLA-------------------
    for (var i = 0; i < arrayNumerosTabla.length; i++) {
        contenidoRejilla += '<td class="celdaNormal">' + arrayNumerosTabla[i] + '</td>';
    }

    for (var j = 0; j < arrayLetrasTabla.length; j++) {
        contenidoRejilla += "</tr><tr>";
        contenidoRejilla += '<td class="celdaNormal">' + arrayLetrasTabla[j] + '</td>';

        for (var k = 1; k < arrayNumerosTabla.length; k++) {
            var idCelda = arrayLetrasTabla[j] + arrayNumerosTabla[k];
            if (barcoOsalvo == "Barco") {
                contenidoRejilla += '<td id="' + idCelda + '" class="celdaSinBarco" ondrop="drop(event)" ondragover="allowDrop(event)"></td>';
            }
            if (barcoOsalvo == "Salvo") {
                contenidoRejilla += '<td id="' + idCelda + 's" class="celdaSinSalvo" onclick='+"enviarSalvo('"+idCelda+"s')"+'></td>';
            }
        }
        document.getElementById("rejilla"+barcoOsalvo+"sID").innerHTML = contenidoRejilla;
    }
}

// --------PINTAR CELDAS CON BARCOS-----------
function pintarBarcos(data) {

    for (var l = 0; l < data.ships.length; l++) {
        for (var m = 0; m < data.ships[l].locations.length; m++) {
            document.getElementById(data.ships[l].locations[m]).setAttribute("class", "celdaBarco");
        }
    }
}

function pintarHitsOnMe(data) {
    var keyContrario = 1;

    if (data.gameplayers[1] != null && data.gameplayers[1].gamePlayerID == limpiarURL(document.location.search)) {
        keyContrario = 0;
    }
    var idCeldaSalCon="";
    if (data.gameplayers.length>1) {
        for (var p = 0; p < data.salvoes[keyContrario].locations.length; p++) {
            for (var q = 0; q < data.salvoes[keyContrario].locations[p].length; q++) {
                idCeldaSalCon = data.salvoes[keyContrario].locations[p][q].substring(0, 2);
                if (document.getElementById(idCeldaSalCon).getAttribute("class") == "celdaBarco") {
                    document.getElementById(idCeldaSalCon).setAttribute("class", "celdaTocado");
                }
            }
        }
    }
}

// -------------PINTAR CELDAS CON TUS SALVOS------------
function pintarTusSalvos(data) {

    var keyTuJugador = 0;

    if (data.gameplayers[1] != null && data.gameplayers[1].gamePlayerID == limpiarURL(document.location.search)) {
        keyTuJugador = 1;
    }

    for (var n = 0; n < data.salvoes[keyTuJugador].locations.length; n++) {
        for (var o = 0; o < data.salvoes[keyTuJugador].locations[n].length; o++) {

            document.getElementById(data.salvoes[keyTuJugador].locations[n][o]).setAttribute("class","celdaSalvo");
            document.getElementById(data.salvoes[keyTuJugador].locations[n][o]).innerText = data.salvoes[keyTuJugador].turn[n];
        }
    }
}

// -------PINTAR HITS EN TABLA CONTRARIO-------
function pintarMisHits(data) {
    if (data.gameplayers.length > 1) {
        for (var aa = 0; aa < data.hitsOnOppHistory.length; aa++) {
            document.getElementById(data.hitsOnOppHistory[aa].hitLocation).setAttribute("class", "celdaSalvoTocado");
        }
    }
}

    // ---------------JUGADORES--------------
function crearJugadoresGV(data) {
    var datosJugadoresGamesView = "";
    if (data.gameplayers[0] != null && data.gameplayers[1] != null) {
        if (limpiarURL(document.location.search) == data.gameplayers[0].gamePlayerID) {
            datosJugadoresGamesView = data.gameplayers[0].player.playerEmail + "(YOU) VS. " + data.gameplayers[1].player.playerEmail;
        }
        else {
            datosJugadoresGamesView = data.gameplayers[1].player.playerEmail + "(YOU) VS. " + data.gameplayers[0].player.playerEmail;
        }
    }
    else {
        datosJugadoresGamesView = data.gameplayers[0].player.playerEmail + "(YOU) VS. NO PLAYER";
    }

    document.getElementById("jugadoresGamesviewID").innerHTML = datosJugadoresGamesView;
}

//POSICIONAR BARCOS CON DRAG AND DROP////
function allowDrop(ev) {
    ev.preventDefault();
}

function drag(ev) {
    ev.dataTransfer.setData("text", ev.target.id);
}

function drop(ev) {
    ev.preventDefault();
    var IDTipoBarco = ev.dataTransfer.getData("text");

    ev.target.appendChild(document.getElementById(IDTipoBarco));
    var idDeCelda = ev.target.id;
    var classVertOHor = document.getElementById(IDTipoBarco).getAttribute("class");
    document.getElementById(IDTipoBarco).setAttribute("ubicacion", idDeCelda);
    rellenarPost(idDeCelda, IDTipoBarco, classVertOHor);
}

var arrayObjBarcPost = [
    { tipoBarcoV: "Carrier", locBarcoV: []},
    { tipoBarcoV: "Battleship", locBarcoV: []},
    { tipoBarcoV: "Submarine", locBarcoV: []},
    { tipoBarcoV: "Destroyer", locBarcoV: []},
    { tipoBarcoV: "PatrolBoat", locBarcoV: []}
];

function rellenarPost(celdaID, tipoBarco, classVH) {
    for (var ii = 0; ii < arrayObjBarcPost.length; ii++) {
        if (arrayObjBarcPost[ii].tipoBarcoV == tipoBarco) {
            var letraCeld = celdaID.substring(0, 1);
            var letraCeldAscii = letraCeld.charCodeAt(0);
            numCeld = parseInt(celdaID.substring(1));
            if (classVH == "CarrierHor") {
                arrayObjBarcPost[ii].locBarcoV = [celdaID, letraCeld + (numCeld + 1), letraCeld + (numCeld + 2), letraCeld + (numCeld + 3), letraCeld + (numCeld + 4)];
            }
            if (classVH == "CarrierVer") {
                arrayObjBarcPost[ii].locBarcoV = [celdaID, String.fromCharCode(letraCeldAscii + 1) + numCeld, String.fromCharCode(letraCeldAscii + 2) + numCeld, String.fromCharCode(letraCeldAscii + 3) + numCeld, String.fromCharCode(letraCeldAscii + 4) + numCeld];
            }
            if (classVH == "BattleshipHor") {
                arrayObjBarcPost[ii].locBarcoV = [celdaID, letraCeld + (numCeld + 1), letraCeld + (numCeld + 2), letraCeld + (numCeld + 3)];
            }
            if (classVH == "BattleshipVer") {
                arrayObjBarcPost[ii].locBarcoV = [celdaID, String.fromCharCode(letraCeldAscii + 1) + numCeld, String.fromCharCode(letraCeldAscii + 2) + numCeld, String.fromCharCode(letraCeldAscii + 3) + numCeld];
            }
            if (classVH == "SubmarineHor" || classVH == "DestroyerHor") {
                arrayObjBarcPost[ii].locBarcoV = [celdaID, letraCeld + (numCeld + 1), letraCeld + (numCeld + 2)];
            }
            if (classVH == "SubmarineVer" || classVH == "DestroyerVer") {
                arrayObjBarcPost[ii].locBarcoV = [celdaID, String.fromCharCode(letraCeldAscii + 1) + numCeld, String.fromCharCode(letraCeldAscii + 2) + numCeld];
            }
            if (classVH == "PatrolBoatHor") {
                arrayObjBarcPost[ii].locBarcoV = [celdaID, letraCeld + (numCeld + 1)];
            }
            if (classVH == "PatrolBoatVer") {
                arrayObjBarcPost[ii].locBarcoV = [celdaID, String.fromCharCode(letraCeldAscii + 1) + numCeld];
            }
        }
    }
}

document.getElementById("Carrier").onclick = function(){
    girarVerHor("Carrier");
    };
document.getElementById("Battleship").onclick = function(){
    girarVerHor("Battleship");
};
document.getElementById("Submarine").onclick = function(){
    girarVerHor("Submarine");
};
document.getElementById("Destroyer").onclick = function(){
    girarVerHor("Destroyer");
};
document.getElementById("PatrolBoat").onclick = function(){
    girarVerHor("PatrolBoat");
};

function girarVerHor(idAgirar) {
    var vertOHor = document.getElementById(idAgirar).getAttribute("class");
    if (vertOHor == idAgirar+"Hor") {
        document.getElementById(idAgirar).setAttribute("class", idAgirar+"Ver");
    }
    else {
        document.getElementById(idAgirar).setAttribute("class", idAgirar+"Hor");
    }
    var ubicacionCelda = document.getElementById(idAgirar).getAttribute("ubicacion");
    var classVertHor = document.getElementById(idAgirar).getAttribute("class");
    rellenarPost(ubicacionCelda, idAgirar, classVertHor);
};

    // -----CREAR BARCOS PICANDO BOTON ENVIAR BARCOS---------
function enviarBarcos(arrayObjBarcPostPar){
    var localizacionesVac = [];
    for (var jj=0; jj<arrayObjBarcPostPar.length; jj++){
        for (var kk=0; kk<arrayObjBarcPostPar[jj].locBarcoV.length; kk++) {
            localizacionesVac.push(arrayObjBarcPostPar[jj].locBarcoV[kk]);
        }
    }
    var norepetidos = localizacionesVac.filter(function (elem, pos) {
        return localizacionesVac.indexOf(elem) == pos;
    });

    // -----SABER SI ESTÁS FUERA DE LA REJILLA---------
    var fueraODentro="dentro";
    for (var ll=0; ll<localizacionesVac.length; ll++){
        letraCelda = localizacionesVac[ll].substring(0 , 1);
        numCelda = localizacionesVac[ll].substring(1);
        if (numCelda>10) {
            fueraODentro="fuera";
            break;
        }
        if (letraCelda>"J"){
            fueraODentro="fuera";
            break;
        }
    }
    if (fueraODentro=="fuera"){
        alert("BARCO FUERA DE LA REJILLA");
    }
    else if (norepetidos.length != localizacionesVac.length){
        alert("BARCO SUPERPUESTO");
    }
    else if (localizacionesVac.length < 17){
        alert("NO HAS COLOCADO TODOS LOS BARCOS");
    }
    else {
        $.post({
            url: "/games/players/" + limpiarURL(document.location.search) + "/ships",
            data: JSON.stringify(arrayObjBarcPostPar),
            dataType: "text",
            contentType: "application/json"
        })
            .done(function () {
                window.location.reload();
            })
            .fail(function () {
                console.log("barcos no añadidos");
            })
    }
}
var contadorSalvos = 0;
var contenidoSalvo=[];
// -----CREAR SALVOS PICANDO BOTON ENVIAR SALVOS---------
function enviarSalvo(casilla) {
;
    if (contenidoSalvo.includes(casilla)==true){
        document.getElementById(casilla).style.backgroundColor = "blue";
        contadorSalvos--;
        var indiceCasilla = contenidoSalvo.indexOf(casilla);
        contenidoSalvo.splice(indiceCasilla,1);
    }
    else if (contadorSalvos >= 5) {
        alert("no hay más disparos en este turno");
    }
    else if (document.getElementById(casilla).getAttribute("class")=="celdaSalvo"){
        alert("ya hay un disparo en esta casilla");
    }
    else {
        document.getElementById(casilla).style.backgroundColor = "yellow";
        contadorSalvos++;
        contenidoSalvo.push(casilla);
    }
}

function enviarSalvos(contenidoSalvo){
    if (contadorSalvos < 5){
        alert("no has introducido todos los disparos");
    }
    else {
        $.post({
            url: "/games/players/" + limpiarURL(document.location.search) + "/salvos",
            data: JSON.stringify(
                {locSalvoV: contenidoSalvo}
            ),
            dataType: "text",
            contentType: "application/json"
        })
            .done(function () {
                console.log("salvo añadido");
                window.location.reload();
            })
            .fail(function () {
                console.log("salvo no añadidos");
            })
    }
}

function crearTablaHitsOnYou(data) {
    if (limpiarURL(document.location.search) == data.gameplayers[0].gamePlayerID) {
        var contHitOnYou = 1;
    }
    else {
        var contHitOnYou = 0;
    }
    // ------------TABLA HISTORIAL HIT AND SINKS ON ME--------
    var turnHitsOnMe = [];

    if (data.gameplayers.length < 2) {
        document.getElementById("hitsOnYouTableID").innerHTML = "";
    }
    else {
        for (var nnn = 0; nnn < data.salvoes[contHitOnYou].locations.length; nnn++) {
            var turnHitsOnMeObj = {};
            turnHitsOnMeObj.Destroyer = 0;
            turnHitsOnMeObj.PatrolBoat = 0;
            turnHitsOnMeObj.Submarine = 0;
            turnHitsOnMeObj.Carrier = 0;
            turnHitsOnMeObj.Battleship = 0;
            var turnOpp = data.salvoes[contHitOnYou].turn[nnn];
            turnHitsOnMeObj.turn = turnOpp;
            for (var ooo = 0; ooo < data.salvoes[contHitOnYou].locations[nnn].length; ooo++) {
                var locSalvCont = data.salvoes[contHitOnYou].locations[nnn][ooo];
                for (var ppp = 0; ppp < data.ships.length; ppp++) {
                    var tipoMiBarco = data.ships[ppp].type;
                    for (var qqq = 0; qqq < data.ships[ppp].locations.length; qqq++) {
                        var locMisBarc = data.ships[ppp].locations[qqq];
                        if (locSalvCont == locMisBarc + "s") {
                            turnHitsOnMeObj[tipoMiBarco]++;
                        }
                    }
                }
            }
            turnHitsOnMe.push(turnHitsOnMeObj);
            dibujarTabHitsOnMe(turnHitsOnMe);
        }
    }
}


function dibujarTabHitsOnMe(turnHitsOnMeP) {

    // -------CREAR TABLAS HITS ON ME-------
    var datosTablaHitsOnMe = "";
    for (var lll = 0; lll < turnHitsOnMeP.length; lll++) {
        for (var mmm = 0; mmm < turnHitsOnMeP.length; mmm++) {
            if (turnHitsOnMeP[mmm].turn == lll + 1) {
                datosTablaHitsOnMe += "<tr hitsOnYouTableID>" +
                    "<td>" + turnHitsOnMeP[mmm].turn + "</td>" +
                    "<td>" + "Bat:" + turnHitsOnMeP[mmm].Battleship + " Carr:" + turnHitsOnMeP[mmm].Carrier + " Des:" + turnHitsOnMeP[mmm].Destroyer + " Pat:" + turnHitsOnMeP[mmm].PatrolBoat + " Sub:" + turnHitsOnMeP[mmm].Submarine + "</td>" +
                    "</tr>";
            }
        }
    }
    document.getElementById("hitsOnYouTableID").innerHTML = datosTablaHitsOnMe;
}

function crearTablaHitsOpp(data) {

    if (limpiarURL(document.location.search) == data.gameplayers[0].gamePlayerID) {
        var tuJugadorHits = 0;
    }
    else {
        var tuJugadorHits = 1;
    }
// ------------TABLA HISTORIAL HITS OPONENTE--------

    var turnMyHits = [];

    // -------EMPIEZO CONTANDO TURNO DESDE EL TURNO 1------
    for (var contTurnoSalvo = 1; contTurnoSalvo < data.salvoes[tuJugadorHits].locations.length+1; contTurnoSalvo++) {
        var turnMyHitsObj = {};
        // -----INICIALIZO CONTADOR BARCOS-----
        turnMyHitsObj.Destroyer = 0;
        turnMyHitsObj.PatrolBoat = 0;
        turnMyHitsObj.Submarine = 0;
        turnMyHitsObj.Carrier = 0;
        turnMyHitsObj.Battleship = 0;

        // ------ITERO LOS HITS-----
        for (var iii = 0; data.hitsOnOppHistory.length > iii; iii++) {
    // --------SI EL CONTADOR DE TURNOS COINCIDE CON TURNO JSON----
    if (contTurnoSalvo == data.hitsOnOppHistory[iii].hitTurn) {
        turnMyHitsObj[data.hitsOnOppHistory[iii].hitShip]++;
    }

}
        turnMyHitsObj.turn = contTurnoSalvo;
        turnMyHits.push(turnMyHitsObj);
    }

    // -------CREAR TABLAS HITS--------
    var datosTablaHits = "";
    for (var jjj = 0; jjj < turnMyHits.length; jjj++) {
        datosTablaHits += "<tr>"+
            "<td>"+turnMyHits[jjj].turn+"</td>"+
            "<td>"+"Bat:"+turnMyHits[jjj].Battleship+" Carr:"+turnMyHits[jjj].Carrier+" Des:"+turnMyHits[jjj].Destroyer+" Pat:"+turnMyHits[jjj].PatrolBoat+" Sub:"+turnMyHits[jjj].Submarine+"</td>"+
            "</tr>";
    }
    document.getElementById("hitsTableID").innerHTML = datosTablaHits;
}

// ------CREAR TABLA SINKS ON ME----
function crearTablaSinksOn(data, sinkOn) {
    if (data.gameplayers.length <2){
        document.getElementById(sinkOn+"ID").innerHTML = "";
        document.getElementById(sinkOn+"LeftID").innerHTML = "";
    }
    else {
        var txSinksOn = "";
        var txSinksLeft = "";
        for (var kkk = 0; kkk < data[sinkOn].length; kkk++) {
            var nombreBarcoO = Object.keys(data[sinkOn][kkk]);
            var nombreBarco = nombreBarcoO[0];
            if (data[sinkOn][kkk][nombreBarco] == "sink") {
                txSinksOn += nombreBarco + " sink " + "<br>";
            }
            else {
                txSinksLeft += nombreBarco + "<br>";
            }
        }
        document.getElementById(sinkOn + "ID").innerHTML = txSinksOn;
        document.getElementById(sinkOn + "LeftID").innerHTML = txSinksLeft;
    }
}