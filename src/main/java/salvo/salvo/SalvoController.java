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
        List<Game> repoGamesfindAll = repoGames.findAll();

        Set<GamePlayer> gamePlayers = game.getGamePlayers();

        for(int i = 0; i<repoGamesfindAll.size(); i++){
            Map<String, Object> IDyCreatedMap = new HashMap<String, Object>();
            IDyCreatedMap.put("ID", repoGamesfindAll.get(i).getId());
            IDyCreatedMap.put("Created", repoGamesfindAll.get(i).getFechaVar());

            Set<GamePlayer> input = repoGamesfindAll.get(i).getGamePlayers();
            List<Map<String, Object>> output = new ArrayList<>();

            // Option 1
            for (GamePlayer gameNoEntiendo : input) {
                Map<String, Object> gpDTO = gamePlayerMetodo(gameNoEntiendo);
                output.add(gpDTO);
            }

            // Option 2
//            output = input.stream()
//                    .map(gameNoEntiendo -> gamePlayerMetodo(gameNoEntiendo))
//                    .collect(Collectors.toList());


//            IDyCreatedMap.put("gamePlayers", games.get(i).getGamePlayers().stream()
//                    .map(gameNoEntiendo -> gamePlayerMetodo(gameNoEntiendo))
//                    .collect(Collectors.toList()));

            IDyCreatedMap.put("gamePlayers", output);


            IDyCreatedList.add(IDyCreatedMap);
        }
        return IDyCreatedList;

//        ArrayList IDsGamesArr = new ArrayList<>();
//        for(int i = 0; i<repoGames.findAll().size(); i++){
//            IDsGamesArr.add(repoGames.findAll().get(i).getId());
//        }
//        return IDsGamesArr;
    }
    @RequestMapping("api/prueba")
    public ArrayList prueba5() {
        int prueba6 = repoGames.findAll().size();
        List prueba7 = repoGames.findAll();
        long prueba8 = repoGames.findAll().get(1).getId();
        Set<GamePlayer> prueba9 = repoGames.findAll().get(1).getGamePlayers();
//        List prueba10 = repoGames.findAll().get(1).getGamePlayers().stream().map(gamePlayer -> gamePlayer.getId()).collect(Collectors.toList());
//        List prueba11 = repoGames.findAll().get(1).getGamePlayers().stream().map(gamePlayer -> gamePlayer.getGamePlayerUserName()).collect(Collectors.toList());
        ArrayList playerName = new ArrayList();
//        String VarInterm = "";

        for (GamePlayer gp : prueba9) {
//            VarInterm = gp.getGamePlayerUserName().getUserName();
            playerName.add(gp.getGamePlayerUserName().getUserName());
        }

        return playerName;
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

    @RequestMapping("api/game_view/1")
    public String pruebaGameView() {
        String pruebaGameView1 = "GAME VIEW";
        return pruebaGameView1;
    }
}
