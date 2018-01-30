$.getJSON("http://localhost:8080/api/games", function (data) {

    //	console.log(data);
    crearFutbolMobile(data);

});

function crearFutbolMobile(data) {

console.log(data);

    var contenidoFutbolMobile = '<ul>PARTIDA'+
        '<li>' + 'Creado: '+ data["0"].Created + '</li>'+
        '<li>'+'ID: '+data["0"].ID+'<li>'+
        '<ul>GAMEPLAYER'+
        '<li>ID: '+data["0"].gamePlayers["0"].id+'</li>'+
        '<ul>JUGADOR'+
        '<li>MAIL: '+data["0"].gamePlayers["0"].players.email+'</li>'+
        '<li>ID: '+data["0"].gamePlayers["0"].players.id+'</li>'+
        '</ul>'+'</ul>'+
        '<ul>GAMEPLAYER'+
        '<li>ID: '+data["0"].gamePlayers["1"].id+'</li>'+
        '<ul>JUGADOR'+
        '<li>MAIL: '+data["0"].gamePlayers["1"].players.email+'</li>'+
        '<li>ID: '+data["0"].gamePlayers["1"].players.id+'</li>'
        '</ul>'+'</ul>'
        ;

    console.log(contenidoFutbolMobile);

    document.getElementById("futbolMobileID").innerHTML = contenidoFutbolMobile;

}
