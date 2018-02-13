package salvo.salvo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("api/scores")
    public Map scoresMetodo(Score score){
        Map scoreMap = new HashMap();
        scoreMap.put("hola", repoScore.findAll());
        return scoreMap;
    }

    @RequestMapping("api/games")
    public ArrayList IDyCreatedMetodo(Game game) {
        ArrayList IDyCreatedList = new ArrayList();
        List<Game> repoGamesfindAll = repoGames.findAll();

        Set<GamePlayer> gamePlayers = game.getGamePlayers();

        for(int i = 0; i<repoGamesfindAll.size(); i++){
            Map<String, Object> IDyCreatedMap = new HashMap<String, Object>();
            IDyCreatedMap.put("gameID", repoGamesfindAll.get(i).getId());
            IDyCreatedMap.put("gameCreated", repoGamesfindAll.get(i).getFechaVar());

            IDyCreatedMap.put("gamePlayers", repoGamesfindAll.get(i).getGamePlayers().stream()
                    .map(gameLambda -> datosGamePlayerMetodo(gameLambda))
                    .collect(Collectors.toList()));

            IDyCreatedList.add(IDyCreatedMap);
        }
        return IDyCreatedList;
    }

    @RequestMapping("api/game_view/{gamePlayerId}")
    public Map<String, Object> metodoGameView(@PathVariable Long gamePlayerId) {

        Map<String,Object> gameViewMap = new HashMap<>();
        gameViewMap.put("gameID", (repoGamePlayer.findOne(gamePlayerId).getGameEnGamePlayers().getId()));
        gameViewMap.put("creationDateGame", (repoGamePlayer.findOne(gamePlayerId).getGameEnGamePlayers().getFechaVar()));
        gameViewMap.put("gameplayers", (repoGamePlayer.findOne(gamePlayerId).getGameEnGamePlayers().getGamePlayers()
                .stream()
                .map(gpLambda -> datosGamePlayerMetodo(gpLambda))
                .collect(Collectors.toList())));
        gameViewMap.put("ships", (repoGamePlayer.findOne(gamePlayerId).getShips()
                .stream()
                .map(shLambda -> datosShipsMetodo(shLambda))
                .collect(Collectors.toList())));
        gameViewMap.put("salvoes", (repoGamePlayer.findOne(gamePlayerId).getGameEnGamePlayers().getGamePlayers()
                .stream()
                .map(salvoLambda -> datosSalvosMetodo(salvoLambda))
                .collect(Collectors.toList())));

        return gameViewMap;

    }

    public Map<String, Object> datosGamePlayerMetodo(GamePlayer gamePlayerParam) {
        Map<String,Object> gamePlayersMap = new HashMap<>();
        gamePlayersMap.put("gamePlayerID", gamePlayerParam.getId());
        gamePlayersMap.put("player", datosPlayersMetodo(gamePlayerParam.getPlayerEnGameplayer()));
        return gamePlayersMap;
    }

    public Map<String, Object> datosPlayersMetodo(Player playerParam) {
        Map<String, Object> playerMap = new HashMap<>();
        playerMap.put("playerId", playerParam.getId());
        playerMap.put("playerEmail", playerParam.getUserName());
        playerMap.put("playerScore", playerParam);

        return playerMap;
    }

    public Map<String, Object> datosShipsMetodo(Ship shipParam) {
        Map<String, Object> shipsMap = new HashMap<>();
        shipsMap.put("type", shipParam.getTipoBarcoV());
        shipsMap.put("locations", shipParam.getLocBarcoV());

        return shipsMap;
    }

    public Map<Object, Object> datosSalvosMetodo(GamePlayer salvoParam) {
        Map<Object, Object> shipsMap = new HashMap<>();
        shipsMap.put("playerId", salvoParam.getPlayerEnGameplayer().getId());
        shipsMap.put("turn", salvoParam.getSalvos()
                .stream()
                .map(salvo2Lambda -> datosSalvoMetodo2(salvo2Lambda))
                .collect(Collectors.toList()));
        shipsMap.put("locations", salvoParam.getSalvos()
                .stream()
                .map(salvo3Lambda -> datosSalvoMetodo3(salvo3Lambda))
                .collect(Collectors.toList()));

        return shipsMap;
    }

    public int datosSalvoMetodo2(Salvo salvoPar2) {
//        Map<Object, Object> shipsMap = new HashMap<>();
//        shipsMap.put(salvoPar2.getLocSalvoV(), salvoPar2.getNumeroTurnoV());
//        shipsMap.put(salvoPar2.getNumeroTurnoV(), salvoPar2.getLocSalvoV());
//        shipsMap.put("turn", salvoPar2.getNumeroTurnoV());

        return salvoPar2.getNumeroTurnoV();
    }

    public List datosSalvoMetodo3(Salvo salvoPar3) {
//        ArrayList shipsMap = new HashMap<>();
//        shipsMap.put(salvoPar2.getLocSalvoV(), salvoPar2.getNumeroTurnoV());
//        shipsMap.put("locations", salvoPar3.getLocSalvoV());

        return salvoPar3.getLocSalvoV();
    }


    @RequestMapping("api/prueba")
    public ArrayList prueba5() {
        int prueba6 = repoGames.findAll().size();
        List prueba7 = repoGames.findAll();
        long prueba8 = repoGames.findAll().get(1).getId();
        Set<GamePlayer> prueba9 = repoGames.findAll().get(1).getGamePlayers();

        ArrayList playerName = new ArrayList();

        for (GamePlayer gp : prueba9) {
            playerName.add(gp.getPlayerEnGameplayer().getUserName());
        }

        return playerName;
    }
}