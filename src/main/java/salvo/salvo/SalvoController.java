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
            IDyCreatedMap.put("gamePlayers", repoGames.findAll().get(i).getGamePlayers().stream()
                    .map(gameNoEntiendo -> gamePlayerMetodo(gameNoEntiendo))
                    .collect(Collectors.toList()));

            IDyCreatedList.add(IDyCreatedMap);
        }


        return IDyCreatedList;

//        ArrayList IDsGamesArr = new ArrayList<>();
//        for(int i = 0; i<repoGames.findAll().size(); i++){
//            IDsGamesArr.add(repoGames.findAll().get(i).getId());
//        }
//        return IDsGamesArr;
    }

//    @RequestMapping("api/gustavo")
//    public List<Object> arrayObjGeneral(){
//
//        return repoGames.findAll()
//                .stream()
//
//                .map(gameNoEntiendo -> gameMetodo(gameNoEntiendo))
//
//                .collect(Collectors.toList());
//
//    }

//    public Map<String,Object> gameMetodo(Game gameParam){
//    Map<String,Object> gameMap = new HashMap<>();
//    gameMap.put("id", gameParam.getId());
//    gameMap.put("date", gameParam.getFechaVar());
//    gameMap.put("gamePlayers", gameParam.getGamePlayers().stream()
//                                    .map(gamePlayer -> gamePlayerMetodo(gamePlayer) )                                            .collect(Collectors.toList()));
//        return gameMap;
//    }

    public Map<String, Object> gamePlayerMetodo(GamePlayer gamePlayerParam) {
        Map<String,Object> gamePlayersMap = new HashMap<>();

        gamePlayersMap.put("id", gamePlayerParam.getId());
        gamePlayersMap.put("players", playersInfo(gamePlayerParam.getGamePlayerUserName()));
        return gamePlayersMap;
    }

    public Map<String, Object> playersInfo (Player playerParam) {
        Map<String, Object> playerMap = new HashMap<>();
        playerMap.put("id", playerParam.getId());
        playerMap.put("email", playerParam.getUserName());

        return playerMap;
    }
}
