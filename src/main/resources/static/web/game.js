$.getJSON("http://localhost:8080/api/game_view/"+limpiarURL(document.location.search), function (data) {
    console.log(data);
    verPorStatus(data);
    crearRejiBarcosYsalvos(data);
    pintarMisHits(data);
    crearJugadoresGV(data);
    crearStatus(data);
    crearTablaHitsOnYou(data);
    crearTablaHitsOpp(data);
    crearTablaSinksOn(data , "sinksOnMe");
    crearTablaSinksOn(data , "sinksOnOpponent");

});

function verPorStatus(data) {
    var status = data.gameStatus;
    if (status == "1-startPlaceShips") {
        $("#statusID, #jugadoresGamesviewID, #rejillaBarcosID, .allShips, #crearShipsID").show();
        $(".hitOnYouCl,.sinksOnYouCl,.hitOpponentCl,.sinksOnOpponCl,#rejillaSalvosID, #crearSalvosID").hide();

    }
    else if (status == "2-noOpponent") {
        $("#statusID, #jugadoresGamesviewID, #rejillaBarcosID").show();
        $(".hitOnYouCl,.sinksOnYouCl,.hitOpponentCl,.sinksOnOpponCl,#rejillaSalvosID, #crearShipsID, #crearSalvosID, .allShips").hide();
        setInterval(function () {
            reloadPage("2-noOpponent");
            // console.log(data);
        }, 5000);
    }
    else if (status == "3-opponentNoShips") {
        $("#statusID, #jugadoresGamesviewID, #rejillaBarcosID").show();
        $(".hitOnYouCl,.sinksOnYouCl,.hitOpponentCl,.sinksOnOpponCl,#rejillaSalvosID, #crearShipsID, #crearSalvosID, .allShips").hide();
    }
    else if (status == "4-addSalvos" || status == "4-addSalvosMismoTurno") {
        $("#statusID, #jugadoresGamesviewID, #rejillaBarcosID, #rejillaSalvosID, #crearSalvosID, .hitOnYouCl,.sinksOnYouCl,.hitOpponentCl,.sinksOnOpponCl").show();
        $("#crearShipsID, .allShips").hide();
    }

    else if (status == "5-whaitOppSalvo") {
        $("#statusID, #jugadoresGamesviewID, #rejillaBarcosID, #rejillaSalvosID, .hitOnYouCl,.sinksOnYouCl,.hitOpponentCl,.sinksOnOpponCl").show();
        $("#crearShipsID, .allShips, #crearSalvosID").hide();
    }

    else if (status == "6-Tie") {
        tieWinLose();
    }

    else if (status == "7-YouLose") {
        tieWinLose();
    }

    else if (status == "8-YouWin") {
        tieWinLose();
    }
}

function reloadPage(statusP) {
    $.getJSON("http://localhost:8080/api/game_view/"+limpiarURL(document.location.search), function (data) {
        console.log(data.gameStatus);
        console.log(statusP);
        if (statusP != data.gameStatus){
            window.location.reload();
        }
    });
}


function tieWinLose(){
    $("#statusID, #jugadoresGamesviewID, #rejillaBarcosID, #rejillaSalvosID, .hitOnYouCl,.sinksOnYouCl,.hitOpponentCl,.sinksOnOpponCl").show();
    $("#crearShipsID, .allShips, #crearSalvosID").hide();
}



function crearStatus(data){
    document.getElementById("statusID").innerHTML = "Status "+data.gameStatus;
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

function crearRejiBarcosYsalvos(data) {

    var contenidoRejillaBarcos1 = "";
    arrayNumerosTabla = [" ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"];

    arrayLetrasTabla = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J"];

    contenidoRejillaBarcos1 += "<tr>";

    var keyTuJugador = 0;
    var keyContrario = 1;

    if (data.gameplayers[1] != null && data.gameplayers[1].gamePlayerID == limpiarURL(document.location.search)) {
        keyTuJugador = 1;
        keyContrario = 0;
    }

    // ----------CONSTRUIR TABLA-------------------
    for (var i = 0; i < arrayNumerosTabla.length; i++) {
        contenidoRejillaBarcos1 += '<td class="celdaNormal">' + arrayNumerosTabla[i] + '</td>';
    }

    contenidoRejillaBarcos1 += "</tr><tr>";
    contenidoRejillaSalvos1 = contenidoRejillaBarcos1;

    for (var j = 0; j < arrayLetrasTabla.length; j++) {

        contenidoRejillaBarcos1 += '<td>' + arrayLetrasTabla[j] + '</td>';
        contenidoRejillaSalvos1 += '<td>' + arrayLetrasTabla[j] + '</td>';

        for (var k = 1; k < arrayNumerosTabla.length; k++) {
            var claseBarco = "celdaSinBarco";
            var claseSalvo = "celdaSinSalvo";
            var idCelda = arrayLetrasTabla[j] + arrayNumerosTabla[k];
            var idCeldaSalvo = arrayLetrasTabla[j] + arrayNumerosTabla[k]+"s";
            var txCeldaTuSalvo = "";

            // --------PINTAR CELDAS CON BARCOS-----------
            for (var l = 0; l < data.ships.length; l++) {
                for (var m = 0; m < data.ships[l].locations.length; m++) {
                    if (data.ships[l].locations[m] == idCelda) {
                        var claseBarco = "celdaBarco";
                        // ----PINTAR LOS IMPACTOS DE LOS DISPAROS EN TUS BARCOS--------------
                        if (data.salvoes.length<2){
                            break;
                        }
                        for (var p = 0; p < data.salvoes[keyContrario].locations.length; p++) {
                            for (var q = 0; q < data.salvoes[keyContrario].locations[p].length; q++) {
                                if (data.salvoes[keyContrario].locations[p][q] == idCeldaSalvo) {
                                    var claseBarco = "celdaTocado";
                                    var txBarcoTocado = data.salvoes[keyContrario].turn[p];
                                }
                            }
                        }
                    }
                }
            }

            // -------------PINTAR CELDAS CON TUS SALVOS------------

            for (var n = 0; n < data.salvoes[keyTuJugador].locations.length; n++) {
                // console.log(data.salvoes["0"].locations["0"].locations[n]);
                for (var o = 0; o < data.salvoes[keyTuJugador].locations[n].length; o++) {
                    // console.log(data.salvoes["0"].locations[n][o]);
                    if (data.salvoes[keyTuJugador].locations[n][o] == idCeldaSalvo) {
                        var claseSalvo = "celdaSalvo";
                        var txCeldaTuSalvo = data.salvoes[keyTuJugador].turn[n];
                    }
                }
            }
var stringIDcelda = "Ssf";
            contenidoRejillaBarcos1 += '<td id="' + idCelda + '" class="' + claseBarco + '"ondrop="drop(event)" ondragover="allowDrop(event)"></td>';
            contenidoRejillaSalvos1 += '<td id="' + idCeldaSalvo + '" class="' + claseSalvo +'"'+

                ' onclick='+"enviarSalvo('"+idCeldaSalvo+"')"+'>' + txCeldaTuSalvo + '</td>';
        }
        contenidoRejillaBarcos1 += '</tr>';
        contenidoRejillaSalvos1 += '</tr>';
    }

    // ------------FIN PINTAR CELDAS BARCOS Y SALVOS---------

    document.getElementById("rejillaBarcosID").innerHTML = contenidoRejillaBarcos1;
    document.getElementById("rejillaSalvosID").innerHTML = contenidoRejillaSalvos1;
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
if (data.gameplayers[0]!= null && data.gameplayers[1]!= null) {
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

// enviarBarcos2(arrayObjBarcPost);

function allowDrop(ev) {
    ev.preventDefault();
    console.log("PERMITIENDO DROP");
}

function drag(ev) {
    ev.dataTransfer.setData("text", ev.target.id);
    console.log("HACIENDO DRAG");
}

function drop(ev) {
    ev.preventDefault();
    var IDTipoBarco = ev.dataTransfer.getData("text");

    ev.target.appendChild(document.getElementById(IDTipoBarco));
    console.log("DROP HECHO");
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

function rellenarPost(celdaID, tipoBarco, classVH){
    for (var ii = 0; ii < arrayObjBarcPost.length; ii++) {
        if (arrayObjBarcPost[ii].tipoBarcoV==tipoBarco) {
            var letraCeld = celdaID.substring(0, 1);
            var letraCeldAscii = letraCeld.charCodeAt(0);
            numCeld = parseInt(celdaID.substring(1));
            if (classVH == "CarrierHor") {
                arrayObjBarcPost[ii].locBarcoV = [celdaID, letraCeld + (numCeld + 1), letraCeld + (numCeld + 2), letraCeld + (numCeld + 3),letraCeld + (numCeld + 4)];
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
                arrayObjBarcPost[ii].locBarcoV = [celdaID, String.fromCharCode(letraCeldAscii + 1) + numCeld, String.fromCharCode(letraCeldAscii + 2)+ numCeld];
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
    console.log(arrayObjBarcPostPar)
    var localizacionesVac = [];
    for (var jj=0; jj<arrayObjBarcPostPar.length; jj++){
        for (var kk=0; kk<arrayObjBarcPostPar[jj].locBarcoV.length; kk++) {
            localizacionesVac.push(arrayObjBarcPostPar[jj].locBarcoV[kk]);
        }
    }
    // var norepetidos = new Set(localizacionesVac);
    var norepetidos = localizacionesVac.filter(function (elem, pos) {
        return localizacionesVac.indexOf(elem) == pos;
    });

    // -----SABER SI ESTÁS FUERA DE LA REJILLA---------
    var fueraODentro="dentro";
    for (var ll=0; ll<localizacionesVac.length; ll++){
        console.log(localizacionesVac[ll]);
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
                console.log("barcos añadido");
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
        // alert("casilla ocupada");
        document.getElementById(casilla).style.backgroundColor = "cyan";
        contadorSalvos--;
        var indiceCasilla = contenidoSalvo.indexOf(casilla);
        contenidoSalvo.splice(indiceCasilla,1);
        console.log(contenidoSalvo);
    }
    else if (contadorSalvos >= 5) {
        alert("no hay más disparos en este turno");
    }
    else if (document.getElementById(casilla).getAttribute("class")=="celdaSalvo"){
        alert("ya hay un disparo en esta casilla");
    }
    else {
        document.getElementById(casilla).style.backgroundColor = "yellow";
        console.log(casilla);
        contadorSalvos++;
        console.log(contadorSalvos);
        contenidoSalvo.push(casilla);
        console.log(contenidoSalvo);
        // console.log("casilla");
    }

}

function enviarSalvos(contenidoSalvo){
    console.log(contenidoSalvo);
    if (contadorSalvos < 5){
        alert("no has introducido todos los disparos");
    }
    else {
        // arrayObjSalvosPostPar
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

    if (data.gameplayers.length <2){
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
            // console.log(data.salvoes[contHitOnYou].turn[nnn]);
            for (var ooo = 0; ooo < data.salvoes[contHitOnYou].locations[nnn].length; ooo++) {
                var locSalvCont = data.salvoes[contHitOnYou].locations[nnn][ooo];
                // console.log(data.salvoes[contHitOnYou].locations[nnn][ooo]);
                for (var ppp = 0; ppp < data.ships.length; ppp++) {
                    var tipoMiBarco = data.ships[ppp].type;
                    // console.log(data.ships[ppp].type);
                    for (var qqq = 0; qqq < data.ships[ppp].locations.length; qqq++) {
                        var locMisBarc = data.ships[ppp].locations[qqq];
                        // console.log(data.ships[ppp].locations[qqq]);
                        if (locSalvCont == locMisBarc + "s") {
                            // console.log("igual");
                            turnHitsOnMeObj[tipoMiBarco]++;
                        }
                    }
                }
            }
            // console.log(turnHitsOnMeObj);
            turnHitsOnMe.push(turnHitsOnMeObj);
            // console.log(turnHitsOnMe);
        }

        // -------CREAR TABLAS HITS ON ME-------
        var datosTablaHitsOnMe = "";
        for (var lll = 0; lll < turnHitsOnMe.length; lll++) {
            datosTablaHitsOnMe += "<tr>" +
                "<td>" + turnHitsOnMe[lll].turn + "</td>" +
                "<td>" + "Bat:" + turnHitsOnMe[lll].Battleship + " Carr:" + turnHitsOnMe[lll].Carrier + " Des:" + turnHitsOnMe[lll].Destroyer + " Pat:" + turnHitsOnMe[lll].PatrolBoat + " Sub:" + turnHitsOnMe[lll].Submarine + "</td>" +
                "</tr>";
            // "<td>"+tunMyHits[jjj].Battleship+"</td></tr>";
        }
        document.getElementById("hitsOnYouTableID").innerHTML = datosTablaHitsOnMe;
    }
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
        // data.hitsAndSinks =
        // var hithis;
        for (var iii = 0; data.hitsOnOppHistory.length > iii; iii++) {
    // --------SI EL CONTADOR DE TURNOS COINCIDE CON TURNO JSON----
    if (contTurnoSalvo == data.hitsOnOppHistory[iii].hitTurn) {

        // console.log(data.hitsAndSinks[iii].hitShip);
        turnMyHitsObj[data.hitsOnOppHistory[iii].hitShip]++;
    }

}
        // console.log(contTurnoSalvo);
        turnMyHitsObj.turn = contTurnoSalvo;
        turnMyHits.push(turnMyHitsObj);
    }
    // console.log(turnMyHits);

    // -------CREAR TABLAS HITS--------
    var datosTablaHits = "";
    for (var jjj = 0; jjj < turnMyHits.length; jjj++) {
        datosTablaHits += "<tr>"+
            "<td>"+turnMyHits[jjj].turn+"</td>"+
            "<td>"+"Bat:"+turnMyHits[jjj].Battleship+" Carr:"+turnMyHits[jjj].Carrier+" Des:"+turnMyHits[jjj].Destroyer+" Pat:"+turnMyHits[jjj].PatrolBoat+" Sub:"+turnMyHits[jjj].Submarine+"</td>"+
            "</tr>";
            // "<td>"+tunMyHits[jjj].Battleship+"</td></tr>";
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

        console.log("sinksOn");
        var txSinksOn = "";
        var txSinksLeft = "";
        // console.log(Object.keys(data.sinksOnMe[1]));
        for (var kkk = 0; kkk < data[sinkOn].length; kkk++) {

            var nombreBarcoO = Object.keys(data[sinkOn][kkk]);
            var nombreBarco = nombreBarcoO[0];
            // console.log(nombreBarco);
            if (data[sinkOn][kkk][nombreBarco] == "sink") {
                txSinksOn += nombreBarco + " sink " + "<br>";
                console.log(txSinksOn);
            }
            else {
                txSinksLeft += nombreBarco + "<br>";
            }
        }
        document.getElementById(sinkOn + "ID").innerHTML = txSinksOn;
        document.getElementById(sinkOn + "LeftID").innerHTML = txSinksLeft;
    }
}