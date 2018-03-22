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
//            IDyCreatedMap.put("scores",repoGamesfindAll.get(i).getScores().stream()
//                    .map(scoreLambda -> gamePlayerDTO(scoreLambda))
//                    .collect(Collectors.toList()));

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
        gameViewMap.put("hits_and_sinks", hitsAndSinksDTO(gamePlayerId));

        return gameViewMap;

    }

    public ArrayList<String> hitsAndSinksDTO(Long gamePlaPar) {
//        String cont="";
//        GamePlayer gamePlaContrario = null;
//        Set<GamePlayer> GPgames = gamePlaPar.getGameEnGamePlayers().getGamePlayers();
//        long gamePlaContrario = 0;
        Set<GamePlayer> gamePlayers = repoGamePlayer.getOne(gamePlaPar).getGameEnGamePlayers().getGamePlayers();
        Long gamePlaContrario = null;
        for (GamePlayer GP : gamePlayers) {
            if (GP.getId() != gamePlaPar) {
                gamePlaContrario = GP.getId();
//                return "contrario";
//                cont = "contrario"+GP;

//                return GP;
            }
//            else {
//                cont = "no contrario";
//            }
        }
        Set<Ship> shipsContrario = repoGamePlayer.getOne(gamePlaContrario).getShips();
        List<List<String>> listLocCont = new ArrayList<>();
//        List tipBarcCont = new ArrayList<>();
//        Map mapBarcCont = new HashMap();
        for (Ship shipContrario : shipsContrario) {
            listLocCont.add(shipContrario.getLocBarcoV());
//            tipBarcCont.add(shipContrario.getTipoBarcoV());
//            mapBarcCont.put(shipContrario.getTipoBarcoV(),shipContrario.getLocBarcoV());
        }
        ArrayList<String> CadaLocCont = new ArrayList<>();
        Set<Salvo> locMisSalvos = repoGamePlayer.getOne(gamePlaPar).getSalvos();

        for (int j=0; j<listLocCont.size(); j++){
            for (int k=0; k<listLocCont.get(j).size(); k++){
                String UnaLocCont = listLocCont.get(j).get(k);
                CadaLocCont.add(UnaLocCont);
//                for (Salvo unSalvo : LocMisSalvos){
//                    ArrayList<String> unaLocSalvo = unSalvo.getLocSalvoV()
//                }
                for (Salvo unSalvo : locMisSalvos){
                    List<String> locUnSalvo = unSalvo.getLocSalvoV();
                    for (int l=0; l<locUnSalvo.size(); l++){
                        String cadaLocMisBarc = locUnSalvo.get(l);
                        if (cadaLocMisBarc == UnaLocCont){

                        }
                    }
                }
//
//                }
//
            }
//            Object pr = listLocCont.get(j);
        }

        return CadaLocCont;
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