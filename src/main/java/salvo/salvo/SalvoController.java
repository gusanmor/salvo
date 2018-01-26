package salvo.salvo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SalvoController {

    @Autowired
    private GameRepository repoGames;

    @RequestMapping("api/games")
    public ArrayList metodoCogerGamesIds() {
        ArrayList IDsGamesArr = new ArrayList<>();
        for(int i = 0; i<repoGames.findAll().size(); i++){
            IDsGamesArr.add(repoGames.findAll().get(i).getId());
        }
        return IDsGamesArr;
    }
}
