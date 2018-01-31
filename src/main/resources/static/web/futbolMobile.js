$.getJSON("http://localhost:8080/api/games", function (data) {

    //	console.log(data);
    crearFutbolMobile(data);

});

function crearFutbolMobile(data) {

//console.log(data);
    
    var contenidoFutbolMobile = {};
    
    for (var i=0; i<data.length; i++) {
    var fecha2 = new Date(data[i].Created);
    var fecha3 = fecha2.toString();
    
    contenidoFutbolMobile += '<div>_</div>'+
        '<div class="fotarIzquierda">'+
        '<ul>PARTIDA'+
        '<li>'+'ID: '+data[i].ID+'</li>'+
        '<li>' + 'Creado: '+ fecha3 + '</li>'+
        '<ul>GAMEPLAYER'+
        '<li>ID: '+data[i].gamePlayers["0"].id+'</li>'+
        '<div>JUGADOR</div>'+
        '<li>ID: '+data[i].gamePlayers["0"].players.id+'</li>'+
        '<li>MAIL: '+data[i].gamePlayers["0"].players.email+'</li>'+
        '</ul>'+
        '<ul>GAMEPLAYER'+
        '<li>ID: '+data[i].gamePlayers["1"].id+'</li>'+
        '<div>JUGADOR</div>'+
        '<li>ID: '+data[i].gamePlayers["1"].players.id+'</li>'+
        '<li>MAIL: '+data[i].gamePlayers["1"].players.email+'</li>'+
        '</ul>'+'</div>';
        
    }

//    console.log(contenidoFutbolMobile);

    document.getElementById("futbolMobileID").innerHTML = contenidoFutbolMobile;

}
