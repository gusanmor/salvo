package salvo.salvo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

                .map(gamePor -> gameMetodo(gamePor))

                .collect(Collectors.toList());

    }

    public Map<String,Object> gameMetodo(Game gameParam){
    Map<String,Object> gameMap = new LinkedHashMap<>();
    gameMap.put("id", gameParam.getId());
    gameMap.put("date", gameParam.getFechaVar());
    gameMap.put("gamePlayers", gameParam.getGamePlayers().stream()
                                    .map(gamePlayer -> getGPlayers(gamePlayer) )                                            .collect(Collectors.toList()));
        return gameMap;
    }

    public Map<String, Object> getGPlayers (GamePlayer gamePlayerParam) {
        Map<String,Object> gamePlayersMap = new LinkedHashMap<>();

        gamePlayersMap.put("id", gamePlayerParam.getId());
        gamePlayersMap.put("players", playersInfo(gamePlayerParam.getGamePlayerUserName()));
        return gamePlayersMap;
    }

    public Map<String, Object> playersInfo (Player playerParam) {
        Map<String, Object> playerMap = new LinkedHashMap<>();
        playerMap.put("id", playerParam.getId());
        playerMap.put("email", playerParam.getUserName());

        return playerMap;
    }
}
