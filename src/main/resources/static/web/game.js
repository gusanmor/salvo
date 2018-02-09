$.getJSON("http://localhost:8080/api/game_view/"+limpiarURL(document.location.search), function (data) {
    console.log(data);
    crearRejillaBarcos(data);
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

function crearRejillaBarcos(data) {
    
    var contenidoRejillaBarcos1 = "";
    arrayNumerosTabla = [" ", "1","2","3","4","5","6","7","8","9","10"];

    arrayLetrasTabla = ["A", "B","C","D","E","F","G","H","I","J"];

    contenidoRejillaBarcos1 += "<tr>";

    for (var i=0; i<arrayNumerosTabla.length; i++) {

        contenidoRejillaBarcos1 += '<td>'+arrayNumerosTabla[i]+'</td>';
    }

    contenidoRejillaBarcos1 += "</tr><tr>";
    contenidoRejillaSalvos1 = contenidoRejillaBarcos1;

    for (var j=0; j<arrayLetrasTabla.length; j++) {

        contenidoRejillaBarcos1 += '<td>'+arrayLetrasTabla[j]+'</td>';
        contenidoRejillaSalvos1 += '<td>'+arrayLetrasTabla[j]+'</td>';

        for (var k=1; k<arrayNumerosTabla.length; k++) {
            var claseBarco = "celdaSinBarco";
            var claseSalvo = "celdaSinSalvo";
            var idCeldas = arrayLetrasTabla[j]+arrayNumerosTabla[k];

            // --------PINTAR CELDAS CON BARCOS-----------
            for (var l=0; l<data.ships.length; l++) {
                for (var m=0; m<data.ships[l].locations.length; m++) {
                    if (data.ships[l].locations[m]==idCeldas){
                        var claseBarco = "celdaBarco";
                    }
                }
            }

            // -------------PINTAR CELDAS CON SALVOS------------

            var keySalvos = 0;
            if (data.gameplayers[1].gamePlayerID == limpiarURL(document.location.search)) {
                keySalvos = 1;
            }

            for (var n=0; n<data.salvoes[keySalvos].locations.length; n++) {
                // console.log(data.salvoes["0"].locations["0"].locations[n]);
                for (var o=0; o<data.salvoes[keySalvos].locations[n].length; o++) {
                    // console.log(data.salvoes["0"].locations[n][o]);
                    if (data.salvoes[keySalvos].locations[n][o]==idCeldas){
                        var claseSalvo = "celdaSalvo";
                        idCeldas = data.salvoes[keySalvos].turn[n];
                    }
                }
            }

            contenidoRejillaBarcos1 += '<td id="'+idCeldas+'" class="'+claseBarco+'">'+idCeldas+'</td>';
            contenidoRejillaSalvos1 += '<td id="'+idCeldas+'" class="'+claseSalvo+'">'+idCeldas+'</td>';
        }
        contenidoRejillaBarcos1 += '</tr>';
        contenidoRejillaSalvos1 += '</tr>';
    }

    // ------------FIN PINTAR CELDAS BARCOS Y SALVOS---------

    // console.log(contenidoRejillaSalvos1);

    document.getElementById("gameID").innerHTML = contenidoRejillaBarcos1;
    document.getElementById("gameID2").innerHTML = contenidoRejillaSalvos1;

    // ---------------JUGADORES--------------

    var crearDatosJugadoresGamesView = "";


    if (limpiarURL(document.location.search) ==data.gameplayers[0].gamePlayerID){
        crearDatosJugadoresGamesView = data.gameplayers[0].player.playerEmail+"(YOU) VS. "+data.gameplayers[1].player.playerEmail;
    }
    else {
        crearDatosJugadoresGamesView = data.gameplayers[1].player.playerEmail+"(YOU) VS. "+data.gameplayers[0].player.playerEmail;
    }

    document.getElementById("jugadoresGamesviewID").innerHTML = crearDatosJugadoresGamesView;

}
