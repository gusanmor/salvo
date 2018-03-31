package salvo.salvo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class SalvoController {

    @Autowired
    private GameRepository repoGames;

    @Autowired
    private PlayerRepository repoPlayers;

    @Autowired
    private GamePlayerRepository repoGamePlayer;

    @Autowired
    private ScoreRepository repoScore;

    @Autowired
    private ShipRepository repoShip;

    @Autowired
    private SalvoRepository repoSalvo;


    @RequestMapping("api/games")
    public Map<String, Object> IDyCreatedMetodo(Game game, Authentication authentication) {
        ArrayList IDyCreatedList = new ArrayList();
        List<Game> repoGamesfindAll = repoGames.findAll();
        String nameAuth = "NombreSinLog";
        String IDAuth = "IDSinLog";

        if (authentication != null) {
            nameAuth = authentication.getName();
            IDAuth = "" + repoPlayers.findByUserName(nameAuth).get(0).getId();
        }
        Set<GamePlayer> gamePlayers = game.getGamePlayers();

        for (int i = 0; i < repoGamesfindAll.size(); i++) {
            Map<String, Object> IDyCreatedMap = new HashMap<String, Object>();
            IDyCreatedMap.put("gameID", repoGamesfindAll.get(i).getId());
            IDyCreatedMap.put("gameCreated", repoGamesfindAll.get(i).getFechaVar());

            IDyCreatedMap.put("gamePlayers", repoGamesfindAll.get(i).getGamePlayers().stream()
                    .map(gameLambda -> gamePlayerDTO(gameLambda))
                    .collect(Collectors.toList()));

            IDyCreatedList.add(IDyCreatedMap);
//            IDyCreatedMap.put("scores","hola");
        }
        Map<String, String> playerLogueado = new HashMap<>();

        playerLogueado.put("ID", IDAuth);

        playerLogueado.put("name", nameAuth);

        Map<String, Object> gamesYplayLog = new HashMap<String, Object>();

        gamesYplayLog.put("games", IDyCreatedList);

        gamesYplayLog.put("playerLogueado", playerLogueado);

//        IDyCreatedList.add(playerLogueado);

        return gamesYplayLog;
    }

    //    @RequestMapping("/books")
    public List<Player> getAll(Authentication authentication) {
        return repoPlayers.findByUserName(authentication.getName());
    }

    @RequestMapping("api/game_view/{gamePlayerId}")
    public Map<String, Object> metodoGameView(@PathVariable Long gamePlayerId) {

        //        -----CONSEGUIR EL GAMEPLAYER CONTRARIO----------
        Set<GamePlayer> gamePlayers = repoGamePlayer.getOne(gamePlayerId).getGameEnGamePlayers().getGamePlayers();
        Long gamePlaContrarioID = null;
//        List<Map<String, Object>> tocadosArrayMap = new ArrayList<>();
//        List<String> todasLocalTodosMisSalvos = new ArrayList<>();
        for (GamePlayer GP : gamePlayers) {
            if (GP != null) {
                if (GP.getId() != gamePlayerId) {
                    gamePlaContrarioID = GP.getId();
                }
            }
        }

        Map<String, Object> gameViewMap = new HashMap<>();
        gameViewMap.put("gameID", (repoGamePlayer.findOne(gamePlayerId).getGameEnGamePlayers().getId()));
        gameViewMap.put("creationDateGame", (repoGamePlayer.findOne(gamePlayerId).getGameEnGamePlayers().getFechaVar()));
        gameViewMap.put("gameplayers", (repoGamePlayer.findOne(gamePlayerId).getGameEnGamePlayers().getGamePlayers()
                .stream()
                .map(gpLambda -> gamePlayerDTO(gpLambda))
                .collect(Collectors.toList())));
        gameViewMap.put("ships", (repoGamePlayer.findOne(gamePlayerId).getShips()
                .stream()
                .map(shLambda -> shipsDTO(shLambda))
                .collect(Collectors.toList())));
        gameViewMap.put("salvoes", (repoGamePlayer.findOne(gamePlayerId).getGameEnGamePlayers().getGamePlayers()
                .stream()
                .map(salvoLambda -> salvosDTO(salvoLambda))
                .collect(Collectors.toList())));

        gameViewMap.put("gameStatus",gameStatusDTO(gamePlayerId , gamePlaContrarioID));

        if (gamePlaContrarioID == null) {
            return gameViewMap;
        }
        gameViewMap.put("hitsOnOppHistory", hitsAndSinksDTO(gamePlayerId , gamePlaContrarioID));
        gameViewMap.put("sinksOnMe", sinksOnDTO(gamePlayerId , gamePlaContrarioID));
        gameViewMap.put("sinksOnOpponent", sinksOnDTO(gamePlaContrarioID , gamePlayerId ));


        return gameViewMap;

    }

    public String gameStatusDTO(Long GP, Long GPCont){
        Set<Ship> shipsGP = repoGamePlayer.getOne(GP).getShips();
//        int numeroGPs = repoGamePlayer.getOne(GP).getGameEnGamePlayers().getGamePlayers().size();


//        System.out.println(shipsGP);
        if (repoGamePlayer.getOne(GP).getShips().size() == 0){
            return "1-startPlaceShips";
        }
        else if (repoGamePlayer.getOne(GP).getGameEnGamePlayers().getGamePlayers().size()<2){
//            System.out.println(numeroGPs);
            return "2-noOpponent";
        }
        else if (repoGamePlayer.getOne(GPCont).getShips().size()<2){
            return "3-opponentNoShips";
        }

        else if (repoGamePlayer.getOne(GP).getSalvos().size()<repoGamePlayer.getOne(GPCont).getSalvos().size()){
            return "4-addSalvos";
        }
        else if (repoGamePlayer.getOne(GP).getSalvos().size()>repoGamePlayer.getOne(GPCont).getSalvos().size()){
            return "5-whaitOppSalvo";
        }
        else if (numBarcoHund(GP, GPCont)==true && numBarcoHund(GPCont, GP)==true){
            sumarPuntos(0.5 , GP);
            return "6-Tie";
        }
        else if (numBarcoHund(GP, GPCont)==true){
            sumarPuntos(0.0 , GP);
            return "7-YouLose";
        }
        else if (numBarcoHund(GPCont, GP)==true){
            sumarPuntos(1.0 , GP);
            return "8-YouWin";
        }
        else if (repoGamePlayer.getOne(GP).getSalvos().size()==repoGamePlayer.getOne(GPCont).getSalvos().size()){
            return "4-addSalvosMismoTurno";
        }
        else return "unknown status";
    }

    public void sumarPuntos(Double puntos, Long GPP){
        if (repoGamePlayer.getOne(GPP).getPlayerEnGameplayer().get1Score(repoGamePlayer.getOne(GPP).getGameEnGamePlayers()) != null){
            System.out.println("yaTienenPunt");
        }
        else {
            Score scoreLose = new Score(puntos, new Date());
            Game gameLose = repoGamePlayer.getOne(GPP).getGameEnGamePlayers();
            Player playerLose = repoGamePlayer.getOne(GPP).getPlayerEnGameplayer();
            gameLose.addScore(scoreLose);
            playerLose.addScore(scoreLose);
            repoPlayers.save(playerLose);
            repoGames.save(gameLose);
            repoScore.save(scoreLose);

        }
    }

    public boolean numBarcoHund(long GPP, long GPcontP){
        int contadorBarcosHund = 0;
        List<Map<Object, String>> misBarcosHund = sinksOnDTO(GPP , GPcontP);
        String barcHund = "";
        for (int uuu = 0; uuu < misBarcosHund.size(); uuu++) {
//            Map asfd = misBarcosHund.get(uuu);
            for (Object key : misBarcosHund.get(uuu).keySet()){
//                System.out.println(key);
                barcHund = misBarcosHund.get(uuu).get(key);
                System.out.println(barcHund);
//                String ffff = asfd.get(key);
                }
//            String barcHund;
            if (barcHund == "sink"){
                contadorBarcosHund++;
            }
        }
        System.out.println(contadorBarcosHund);
        System.out.println(misBarcosHund.size());
        if (contadorBarcosHund == misBarcosHund.size()){
            return true;
        }
        else return false;

    }

    public List<Map<Object, String>> sinksOnDTO(Long gamePlayerP, Long GPContP) {
        List<String> locTodSalv = new ArrayList<>();
        List<Map<Object, String>> listBarcHund = new ArrayList<>();
//        if (GPContP == null) {
//            return listBarcHund;
//        }
        Set<Salvo> todSalvo = repoGamePlayer.getOne(GPContP).getSalvos();
        for (Salvo unSalvo : todSalvo) {
            for (int ttt = 0; ttt < unSalvo.getLocSalvoV().size(); ttt++) {
                String strinUnSalvo = unSalvo.getLocSalvoV().get(ttt);
                String strinUnSalvoSinS = strinUnSalvo.substring(0,2);
                locTodSalv.add(strinUnSalvoSinS);
            }
        }
        System.out.println(locTodSalv);
        Set<Ship> barcos = repoGamePlayer.getOne(gamePlayerP).getShips();

        for (Ship barco : barcos) {
            Map<Object, String> barcHund = new HashMap<>();
            barcHund.put(barco.getTipoBarcoV(), shipIsSunk(locTodSalv, barco));

            listBarcHund.add(barcHund);
//            System.out.println(listBarcHund);
        }
        return listBarcHund;

    }

    public List<Map<String, Object>> hitsAndSinksDTO(Long gamePlaPar , Long GPContID) {

        List<Map<String, Object>> tocadosArrayMap = new ArrayList<>();
        List<String> todasLocalTodosMisSalvos = new ArrayList<>();

//        if (GPContID == null) {
//            return tocadosArrayMap;
//        }
//        -------CONSEGUIR BARCOS CONTRARIO-----------
        Set<Ship> shipsContrario = repoGamePlayer.getOne(GPContID).getShips();

//----------ITERAR BARCO CONTRARIO-------------
        for (Ship shipContrario : shipsContrario) {
//            -------CONSEGUIR TIPO BARCO CONTRARIO---------
            String tipoBarco = shipContrario.getTipoBarcoV();
//            -------------ARRAY CON LAS LOCALIZACIONES DE UN BARCO CONTRARIO---------
            List<String> arrayLocBarco = shipContrario.getLocBarcoV();
//------------ITERAR EL ARRAY DE LOCALIZACIONES DE UN BARCO CONTRARIO---------------
            for (int j = 0; j < arrayLocBarco.size(); j++) {
//                    ------------CONSIGO CADA UNA DE LAS LOCALIZACIONES DE UN BARCO CONTRARIO------
                    String unaLocCont = arrayLocBarco.get(j);
//                    ---------CONSIGO LAS LICALIZACIONES DE MIS SALVOS------
                    Set<Salvo> locMisSalvos = repoGamePlayer.getOne(gamePlaPar).getSalvos();
//                    -----ITERO MIS SALVOS-----------
                    for (Salvo unSalvo : locMisSalvos) {
//                        ------CONSIGO LAS 5 LOCALIZACIONES DE UN SALVO----
                        List<String> locUnSalvo = unSalvo.getLocSalvoV();

                        for (int l = 0; l < locUnSalvo.size(); l++) {
//                            ------CONSIGO UNA LOCALIZACION DE UN SALVO----
                            String unaLocMisSalvos = locUnSalvo.get(l);
                            String unaLocSinS = unaLocMisSalvos.substring(0,2);
                            todasLocalTodosMisSalvos.add(unaLocSinS);
                            int turnoMisSalvos = unSalvo.getNumeroTurnoV();
                            String unaLocContS = unaLocCont + "s";
                            if (unaLocMisSalvos.equals(unaLocContS)) {
                                Map<String, Object> tocadosMap = new HashMap<>();
                                tocadosMap.put("hitTurn", turnoMisSalvos);
                                tocadosMap.put("hitLocation", unaLocMisSalvos);
                                tocadosMap.put("hitShip", tipoBarco);
                                tocadosMap.put("sunkShip", shipIsSunk(todasLocalTodosMisSalvos, shipContrario));

                                tocadosArrayMap.add(tocadosMap);
                            }
                        }
                    }
                }
            }
        return tocadosArrayMap;
    }

    private String shipIsSunk(List<String> playerSalvos, Ship ship) {

        if (playerSalvos == null) {
            return "noOpponent";
        }

        boolean shipIsSunk = ship.getLocBarcoV().stream()
                .allMatch(locations -> playerSalvos.contains(locations));
        if (shipIsSunk == true) {
//            shipIsSunk = false;
            return "sink";
        }
        else return "noSink";
    }


    public Map<String, Object> gamePlayerDTO(GamePlayer gamePlayerParam) {
        Map<String, Object> gamePlayersMap = new HashMap<>();
        gamePlayersMap.put("gamePlayerID", gamePlayerParam.getId());
        gamePlayersMap.put("player", playersDTO(gamePlayerParam.getPlayerEnGameplayer()));
//        Double scoreGamePlayer = gamePlayerParam.getPlayerEnGameplayer().get1Score(gamePlayerParam.getGameEnGamePlayers()).getScoreV();
        if (gamePlayerParam.getPlayerEnGameplayer().get1Score(gamePlayerParam.getGameEnGamePlayers()) != null) {
            gamePlayersMap.put("score", gamePlayerParam.getPlayerEnGameplayer().get1Score(gamePlayerParam.getGameEnGamePlayers()).getScoreV());
        } else gamePlayersMap.put("score", "null");
        return gamePlayersMap;
    }

    public Map<String, Object> playersDTO(Player playerParam) {
        Map<String, Object> playerMap = new HashMap<>();
        playerMap.put("playerId", playerParam.getId());
        playerMap.put("playerEmail", playerParam.getUserName());
//        playerMap.put("playerScore", playerParam.getScores());

        return playerMap;
    }

    public Map<String, Object> shipsDTO(Ship shipParam) {
        Map<String, Object> shipsMap = new HashMap<>();
        shipsMap.put("type", shipParam.getTipoBarcoV());
        shipsMap.put("locations", shipParam.getLocBarcoV());

        return shipsMap;
    }

    public Map<Object, Object> salvosDTO(GamePlayer salvoParam) {
        Map<Object, Object> shipsMap = new HashMap<>();
        shipsMap.put("playerId", salvoParam.getPlayerEnGameplayer().getId());
        shipsMap.put("turn", salvoParam.getSalvos()
                .stream()
                .map(salvo2Lambda -> salvoDTO2(salvo2Lambda))
                .collect(Collectors.toList()));
        shipsMap.put("locations", salvoParam.getSalvos()
                .stream()
                .map(salvo3Lambda -> salvoDTO3(salvo3Lambda))
                .collect(Collectors.toList()));

        return shipsMap;
    }

    public int salvoDTO2(Salvo salvoPar2) {
//        Map<Object, Object> shipsMap = new HashMap<>();
//        shipsMap.put(salvoPar2.getLocSalvoV(), salvoPar2.getNumeroTurnoV());
//        shipsMap.put(salvoPar2.getNumeroTurnoV(), salvoPar2.getLocSalvoV());
//        shipsMap.put("turn", salvoPar2.getNumeroTurnoV());

        return salvoPar2.getNumeroTurnoV();
    }

    public List salvoDTO3(Salvo salvoPar3) {
//        ArrayList shipsMap = new HashMap<>();
//        shipsMap.put(salvoPar2.getLocSalvoV(), salvoPar2.getNumeroTurnoV());
//        shipsMap.put("locations", salvoPar3.getLocSalvoV());

        return salvoPar3.getLocSalvoV();
    }


    @RequestMapping(path = "api/players", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createUser(@RequestParam String username, @RequestParam String password) {
        if (username.isEmpty()) {
            return new ResponseEntity<>(makeMap("error", "no name introduced"), HttpStatus.FORBIDDEN);
        }
        Player user = repoPlayers.findOneByUserName(username);
        if (user != null) {
            return new ResponseEntity<>(makeMap("error", "this username already exists"), HttpStatus.CONFLICT);
        }
        user = repoPlayers.save(new Player(username, password));
        return new ResponseEntity<>(makeMap("success", "username " + username + " has been created"), HttpStatus.CREATED);
    }

    private Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    @RequestMapping(path = "api/games", method = RequestMethod.POST)
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> createGame(Authentication authentication) {
        Date datePrV = new Date();
        Game gameprueb = new Game(datePrV);
        if (authentication != null) {
            Player playerPr = repoPlayers.findOneByUserName(authentication.getName());
            GamePlayer gpPrueb = new GamePlayer(playerPr, gameprueb);
            repoGames.save(gameprueb);
            repoGamePlayer.save(gpPrueb);

            return new ResponseEntity<>("" + gpPrueb.getId(), HttpStatus.OK);
        } else return new ResponseEntity<>("Error, no login", HttpStatus.FORBIDDEN);

    }

    @RequestMapping(path = "/api/game/{IDgameP}/players", method = RequestMethod.POST)
    public ResponseEntity<String> joinGame(@PathVariable Long IDgameP, Authentication authentication) {
        if (authentication != null) {
            Player playerJoin = repoPlayers.findOneByUserName(authentication.getName());
            Game gameJoin = repoGames.findOne(IDgameP);

            if (gameJoin.getGamePlayers().size() > 1) {
                return new ResponseEntity<>("Game is full", HttpStatus.FORBIDDEN);
            } else {
                GamePlayer gamePlayJoin = new GamePlayer(playerJoin, gameJoin);
                repoGamePlayer.save(gamePlayJoin);
                return new ResponseEntity<>("" + gamePlayJoin.getId(), HttpStatus.OK);
            }
        } else return new ResponseEntity<>("Error, no login", HttpStatus.FORBIDDEN);
    }

    @RequestMapping(path = "/games/players/{IDGP}/ships", method = RequestMethod.POST)
    public ResponseEntity<String> colocarBarcos(@PathVariable Long IDGP, @RequestBody Set<Ship> todosBarcos, Authentication authentication) {
////LE PREGUNTO SI HAY ALGUIEN LOGUEADO///
        if (authentication != null) {
            GamePlayer GPcolocandoBarcos = repoGamePlayer.findOne(IDGP);
            ///SI EL GAMEPLAYER EXISTE///
            if (GPcolocandoBarcos != null) {
                //SI EL GAMEPLAYER AUTHENTIFICADO ES IGUAL QUE EL QUE HA HECHO EL POST///
                if (GPcolocandoBarcos.getPlayerEnGameplayer().getUserName() == repoPlayers.findOneByUserName(authentication.getName()).getUserName()) {
                    for (Ship barco : todosBarcos) {
                        GPcolocandoBarcos.addShips(barco);
                        repoGamePlayer.save(GPcolocandoBarcos);
                        repoShip.save(barco);
                    }
                    return new ResponseEntity<>("Barco añadido", HttpStatus.CREATED);
                } else
                    return new ResponseEntity<>("Tu usuario no puede poner barcos en este juego", HttpStatus.FORBIDDEN);

            }
            ///GAMEPLAYER NO EXISTE///
            else return new ResponseEntity<>("Error barco no añadido, Gameplayer no existe", HttpStatus.FORBIDDEN);
        }
        else return new ResponseEntity<>("Error barco no añadido, no login", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(path = "/games/players/{gamePID}/salvos", method = RequestMethod.POST)
    public ResponseEntity<String> colocarSalvos(@PathVariable Long gamePID, @RequestBody Salvo salvoTurno, Authentication authentication) {
        ////LE PREGUNTO SI HAY ALGUIEN LOGUEADO///
        if (authentication != null) {
//            int dssd = 5;
            GamePlayer GPcolocandoSalvos = repoGamePlayer.findOne(gamePID);
            int ultimoTurnoSalvo = GPcolocandoSalvos.getSalvos().size();
            salvoTurno.setNumeroTurnoV(ultimoTurnoSalvo+1);

            GPcolocandoSalvos.addSalvos(salvoTurno);
            repoGamePlayer.save(GPcolocandoSalvos);
            repoSalvo.save(salvoTurno);
            return new ResponseEntity<>("Salvo añadido", HttpStatus.CREATED);
        }
        else return new ResponseEntity<>("Error, salvo no añadido, no login", HttpStatus.UNAUTHORIZED);
    }
}