$.getJSON("http://localhost:8080/api/game_view/"+paramObj(document.location.search), function (data) {
    console.log(data);
    crearRejillaBarcos(data);
});

function paramObj(search) {
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

    for (var j=0; j<arrayLetrasTabla.length; j++) {

        contenidoRejillaBarcos1 += '<td>'+arrayLetrasTabla[j]+'</td>';

        for (var k=1; k<arrayNumerosTabla.length; k++) {
            var claseBarco = "celdaSinBarco";
            var idCeldas = arrayLetrasTabla[j]+arrayNumerosTabla[k];

            for (var l=0; l<data.ships.length; l++) {

                for (var m=0; m<data.ships[l].locations.length; m++) {

                    if (data.ships[l].locations[m]==idCeldas){
                        var claseBarco = "celdaBarco";
                    }

                }

            }
            contenidoRejillaBarcos1 += '<td id="'+idCeldas+'" class="'+claseBarco+'">'+idCeldas+'</td>';
        }
        contenidoRejillaBarcos1 += '</tr>';

    }
    contenidoRejillaBarcos1 += "</tr>";

    document.getElementById("gameID").innerHTML = contenidoRejillaBarcos1;

    // ---------------JUGADORES--------------

    var crearDatosJugadoresGamesView = "";


    if (paramObj(document.location.search) ==data.gameplayers[0].gamePlayerID){
        crearDatosJugadoresGamesView = data.gameplayers[0].player.playerEmail+"(YOU) VS. "+data.gameplayers[1].player.playerEmail;
    }
    else {
        crearDatosJugadoresGamesView = data.gameplayers[1].player.playerEmail+"(YOU) VS. "+data.gameplayers[0].player.playerEmail;
    }

    document.getElementById("jugadoresGamesviewID").innerHTML = crearDatosJugadoresGamesView;

}
