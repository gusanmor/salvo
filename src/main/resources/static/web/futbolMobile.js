$.getJSON("http://localhost:8080/api/games", function (data) {

    //	console.log(data);
    crearFutbolMobile(data);

});

function crearFutbolMobile(data) {

console.log(data);
    
    var contenidoFutbolMobile = {};
    
    for (var i=0; i<data.length; i++) {
    var fecha2 = new Date(data[i].Created);
    var fecha3 = fecha2.toString();
    
    contenidoFutbolMobile += '<div class="fotarIzquierda">'+
        '<ul>PARTIDA'+
        '<li>' + 'Creado: '+ fecha3 + '</li>'+
        '<li>'+'ID: '+data[i].ID+'<li>'+
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
        '</ul>'+'</ul>'+'</div>';
        
    }

//    console.log(contenidoFutbolMobile);

    document.getElementById("futbolMobileID").innerHTML = contenidoFutbolMobile;

}
