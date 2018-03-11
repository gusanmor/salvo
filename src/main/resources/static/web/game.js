$.getJSON("http://localhost:8080/api/game_view/"+limpiarURL(document.location.search), function (data) {
    console.log(data);
    crearRejiBarcosYsalvos(data);
    crearJugadoresGV(data);
});

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
        contenidoRejillaBarcos1 += '<td>' + arrayNumerosTabla[i] + '</td>';
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
            // var idCelda = idCelda;
            var txBarcoTocado = idCelda;
            var txCeldaTuSalvo = idCelda;

            // --------PINTAR CELDAS CON BARCOS-----------
            for (var l = 0; l < data.ships.length; l++) {
                for (var m = 0; m < data.ships[l].locations.length; m++) {
                    if (data.ships[l].locations[m] == idCelda) {
                        var claseBarco = "celdaBarco";
                        // ----PINTAR LOS IMPACTOS DE LOS DISPAROS EN TUS BARCOS--------------
                        for (var p = 0; p < data.salvoes[keyContrario].locations.length; p++) {
                            for (var q = 0; q < data.salvoes[keyContrario].locations[p].length; q++) {
                                if (data.salvoes[keyContrario].locations[p][q] == idCelda) {
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
                    if (data.salvoes[keyTuJugador].locations[n][o] == idCelda) {
                        var claseSalvo = "celdaSalvo";
                        var txCeldaTuSalvo = data.salvoes[keyTuJugador].turn[n];
                    }
                }
            }

            contenidoRejillaBarcos1 += '<td id="' + idCelda + '" class="' + claseBarco + '"ondrop="drop(event)" ondragover="allowDrop(event)"></td>';
            contenidoRejillaSalvos1 += '<td id="' + idCelda + '" class="' + claseSalvo + '">' + txCeldaTuSalvo + '</td>';
        }
        contenidoRejillaBarcos1 += '</tr>';
        contenidoRejillaSalvos1 += '</tr>';
    }

    // ------------FIN PINTAR CELDAS BARCOS Y SALVOS---------

    document.getElementById("rejillaBarcosID").innerHTML = contenidoRejillaBarcos1;
    document.getElementById("rejillaSalvosID").innerHTML = contenidoRejillaSalvos1;
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

var arrayObjBarcPost = [
    { tipoBarcoV: "Carrier", locBarcoV: []},
    { tipoBarcoV: "Battleship", locBarcoV: []},
    { tipoBarcoV: "Submarine", locBarcoV: []},
    { tipoBarcoV: "Destroyer", locBarcoV: []},
    { tipoBarcoV: "PatrolBoat", locBarcoV: []}
];

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

function rellenarPost(celdaID, tipoBarco, classVH){
    for (var ii = 0; ii < arrayObjBarcPost.length; ii++) {
        if (arrayObjBarcPost[ii].tipoBarcoV==tipoBarco) {
            var letraCeld = celdaID.substring(0, 1);
            var letraCeldAscii = letraCeld.charCodeAt(0);
            numCeld = parseInt(celdaID.substring(1, 2));
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
    // -----CREAR BARCOS PICANDO BOTON---------

function enviarBarcos(arrayObjBarcPostPar){
    $.post({
        url: "/games/players/"+limpiarURL(document.location.search)+"/ships",
        data: JSON.stringify(arrayObjBarcPostPar),
        dataType: "text",
        contentType: "application/json"
    })
        .done(function () {
            console.log( "barcos añadido" );
            window.location.reload();
        })
        .fail(function () {
            console.log("barcos no añadidos");
        })
}

