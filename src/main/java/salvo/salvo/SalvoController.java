package salvo.salvo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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


    @RequestMapping("api/games")
    public Map<String, Object> IDyCreatedMetodo(Game game, Authentication authentication) {
        ArrayList IDyCreatedList = new ArrayList();
        List<Game> repoGamesfindAll = repoGames.findAll();
        String name = authentication.getName();
        List<Player> playerList = repoPlayers.findByUserName(name);
        Player player = playerList.get(0);
        Set<GamePlayer> gamePlayers = game.getGamePlayers();

        for(int i = 0; i<repoGamesfindAll.size(); i++){
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
        Map<String,Object> playerLogueado = new HashMap<>();

        playerLogueado.put("ID", player.getId());

        playerLogueado.put("name",player.getUserName());

        Map<String,Object> gamesYplayLog = new HashMap<String, Object>();

        gamesYplayLog.put("games",IDyCreatedList);

        gamesYplayLog.put("playerLogueado",playerLogueado);

//        IDyCreatedList.add(playerLogueado);

        return gamesYplayLog;
    }

//    @RequestMapping("/books")
    public List<Player> getAll(Authentication authentication) {
        return repoPlayers.findByUserName(authentication.getName());
    }

    @RequestMapping("api/game_view/{gamePlayerId}")
    public Map<String, Object> metodoGameView(@PathVariable Long gamePlayerId) {

        Map<String,Object> gameViewMap = new HashMap<>();
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

        return gameViewMap;

    }

    public Map<String, Object> gamePlayerDTO(GamePlayer gamePlayerParam) {
        Map<String,Object> gamePlayersMap = new HashMap<>();
        gamePlayersMap.put("gamePlayerID", gamePlayerParam.getId());
        gamePlayersMap.put("player", playersDTO(gamePlayerParam.getPlayerEnGameplayer()));
//        Double scoreGamePlayer = gamePlayerParam.getPlayerEnGameplayer().get1Score(gamePlayerParam.getGameEnGamePlayers()).getScoreV();
        if (gamePlayerParam.getPlayerEnGameplayer().get1Score(gamePlayerParam.getGameEnGamePlayers()) != null){
            gamePlayersMap.put("score", gamePlayerParam.getPlayerEnGameplayer().get1Score(gamePlayerParam.getGameEnGamePlayers()).getScoreV());
        }
        else gamePlayersMap.put("score", "null");
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