package salvo.salvo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SalvoController {

    @Autowired
    private GameRepository repo;

    @RequestMapping("api/games")
    public List<Game> getGames() {
//        System.out.println(repo);
        return repo.findAll();
    }
}
