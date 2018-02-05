$.getJSON("http://localhost:8080/api/games", function (data) {

    	console.log(data);
    crearFutbolMobile(data);

});

function crearFutbolMobile(data) {

// console.log(data);
    
    var contenidoRejillaBarcos1 = {};
    
    // for (var i=0; i<data.length; i++) {
    // var fecha2 = new Date(data[i].Created);
    // var fecha3 = fecha2.toString();

        contenidoRejillaBarcos1 = '<div>hola2</div>';

        
    // }

//    console.log(contenidoFutbolMobile);

    document.getElementById("gameID").innerHTML = contenidoRejillaBarcos1;

}
