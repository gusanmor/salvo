$.getJSON("http://localhost:8080/api/game_view/1", function (data) {

    	console.log(data);
    crearFutbolMobile(data);

});

function crearFutbolMobile(data) {

// console.log(data);
    
    var contenidoRejillaBarcos1 = "";
    
    // for (var i=0; i<data.length; i++) {
    // var fecha2 = new Date(data[i].Created);
    // var fecha3 = fecha2.toString();

    arrayNumerosTabla = [" ", "1","2","3","4","5","6","7","8","9","10"];
    console.log(arrayNumerosTabla);

    arrayLetrasTabla = ["A", "B","C","D","F","G","H","I","J"];

    contenidoRejillaBarcos1 += "<tr>";

    for (var i=0; i<arrayNumerosTabla.length; i++) {

        contenidoRejillaBarcos1 += '<td>'+arrayNumerosTabla[i]+'</td>';

    }

    contenidoRejillaBarcos1 += "</tr><tr>";

    for (var j=0; j<arrayLetrasTabla.length; j++) {

        contenidoRejillaBarcos1 += '<td>'+arrayLetrasTabla[j]+'</td>';

        for (var k=1; k<arrayNumerosTabla.length; k++) {
            var claseBarco = "celdaSinBarco";
            // console.log("hola");
            var idCeldas = arrayLetrasTabla[j]+arrayNumerosTabla[k];
            // contenidoRejillaBarcos1 += '<td>'+idCeldas+'</td>';

            for (var l=0; l<data.ships.length; l++) {

                // console.log(data.ships.length);

                for (var m=0; m<data.ships[l].locations.length; m++) {
                    // console.log(l);
                    // console.log(m);
                    console.log(data.ships[l].locations[m]);
                    console.log(idCeldas);

                    if (data.ships[l].locations[m]==idCeldas){
                        var claseBarco = "celdaBarco";
                    }

                }

            }
            contenidoRejillaBarcos1 += '<td class="'+claseBarco+'">'+idCeldas+'</td>';
        }
        contenidoRejillaBarcos1 += '</tr>';

    }
    contenidoRejillaBarcos1 += "</tr>";


        // contenidoRejillaBarcos1 = '<tr><td>0<td>DOS</td></tr>';

        
    // }

//    console.log(contenidoFutbolMobile);

    document.getElementById("gameID").innerHTML = contenidoRejillaBarcos1;

}
