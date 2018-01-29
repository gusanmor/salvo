package salvo.salvo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class SalvoController {

    @Autowired
    private GameRepository repoGames;

    @RequestMapping("api/games")
    public ArrayList IDyCreatedMetodo(Game game) {
        ArrayList IDyCreatedList = new ArrayList();

        Set<GamePlayer> gamePlayers = game.getGamePlayers();

        for(int i = 0; i<repoGames.findAll().size(); i++){
            Map<String, Object> IDyCreatedMap = new HashMap<String, Object>();
            IDyCreatedMap.put("ID", repoGames.findAll().get(i).getId());
            IDyCreatedMap.put("Created", repoGames.findAll().get(i).getFechaVar());
            IDyCreatedMap.put("gamePlayers", repoGames.findAll().get(i).getGamePlayers());
            IDyCreatedList.add(IDyCreatedMap);
        }


        return IDyCreatedList;

//        ArrayList IDsGamesArr = new ArrayList<>();
//        for(int i = 0; i<repoGames.findAll().size(); i++){
//            IDsGamesArr.add(repoGames.findAll().get(i).getId());
//        }
//        return IDsGamesArr;
    }

    @RequestMapping("api/gustavo")
    public List<Object> giveMeTheIDS(){

        return repoGames.findAll()
                .stream()

                .map(game -> gameIDS(game))

                .collect(Collectors.toList());

    }

    public Map<String,Object> gameIDS(Game game){
    Map<String,Object> gameMap = new LinkedHashMap<>();
    gameMap.put("id", game.getId());
    gameMap.put("date", game.getFechaVar());
    gameMap.put("gamePlayers", game.getGamePlayers().stream()
                                    .map(gamePlayer -> getGPlayers(gamePlayer) )                                            .collect(Collectors.toList()));
        return gameMap;
    }

    public Map<String, Object> getGPlayers (GamePlayer gamePlayer) {
        Map<String,Object> gamePlayersMap = new LinkedHashMap<>();

        gamePlayersMap.put("id", gamePlayer.getId());
        gamePlayersMap.put("players", playersInfo(gamePlayer.getGamePlayerUserName()));
        return gamePlayersMap;
    }

    public Map<String, Object> playersInfo (Player player) {
        Map<String, Object> playerMap = new LinkedHashMap<>();
        playerMap.put("id", player.getId());
        playerMap.put("email", player.getUserName());

        return playerMap;
    }
}
