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

    arrayNumerosTabla = ["0", "1","2","3","4","5","6","7","8","9","10"];
    console.log(arrayNumerosTabla);

    arrayLetrasTabla = ["A", "B","C","D","F","G","H","I","J"];

    contenidoRejillaBarcos1 += "<tr>";

    for (var i=0; i<arrayNumerosTabla.length; i++) {

        contenidoRejillaBarcos1 += '<td>'+arrayNumerosTabla[i]+'</td>';

    }

    contenidoRejillaBarcos1 += "</tr><tr>";

    for (var i=0; i<arrayNumerosTabla.length; i++) {

        contenidoRejillaBarcos1 += '<td>'+arrayLetrasTabla[0]+'</td>';

    }
    contenidoRejillaBarcos1 += "</tr>";


        // contenidoRejillaBarcos1 = '<tr><td>0<td>DOS</td></tr>';

        
    // }

//    console.log(contenidoFutbolMobile);

    document.getElementById("gameID").innerHTML = contenidoRejillaBarcos1;

}
