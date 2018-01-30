$.getJSON("http://localhost:8080/api/games", function (data) {

    //	console.log(data);
    crearFutbolMobile(data);

});

function crearFutbolMobile(data) {

console.log(data);

//    var contenidoFutbolMobile = '<p>' + data.equipo["0"].nombre + '</p>';

//    console.log(contenidoFutbolMobile);

//    document.getElementById("futbolMobileID").innerHTML = contenidoFutbolMobile;

}
