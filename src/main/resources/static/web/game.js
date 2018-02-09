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

    if (data.gameplayers[1].gamePlayerID == limpiarURL(document.location.search)) {
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

            contenidoRejillaBarcos1 += '<td id="' + idCelda + '" class="' + claseBarco + '">' + txBarcoTocado + '</td>';
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

        if (limpiarURL(document.location.search) == data.gameplayers[0].gamePlayerID) {
            datosJugadoresGamesView = data.gameplayers[0].player.playerEmail + "(YOU) VS. " + data.gameplayers[1].player.playerEmail;
        }
        else {
            datosJugadoresGamesView = data.gameplayers[1].player.playerEmail + "(YOU) VS. " + data.gameplayers[0].player.playerEmail;
        }

        document.getElementById("jugadoresGamesviewID").innerHTML = datosJugadoresGamesView;

    }

