package salvo.salvo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SalvoController {

    @Autowired
    private GameRepository repoGames;

    @RequestMapping("api/games")
    public ArrayList IDyCreatedMetodo() {
        ArrayList IDyCreatedList = new ArrayList();

        for(int i = 0; i<repoGames.findAll().size(); i++){
            Map<String, Object> IDyCreatedMap = new HashMap<String, Object>();
            IDyCreatedMap.put("ID", repoGames.findAll().get(i).getId());
            IDyCreatedMap.put("Created", repoGames.findAll().get(i).getFechaVar());
            IDyCreatedList.add(IDyCreatedMap);
        }
        return IDyCreatedList;

//        ArrayList IDsGamesArr = new ArrayList<>();
//        for(int i = 0; i<repoGames.findAll().size(); i++){
//            IDsGamesArr.add(repoGames.findAll().get(i).getId());
//        }
//        return IDsGamesArr;
    }
}
