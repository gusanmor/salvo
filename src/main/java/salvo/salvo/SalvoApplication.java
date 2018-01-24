package salvo.salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class SalvoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SalvoApplication.class, args);
    }


    @Bean
    public CommandLineRunner initData(PlayerRepository playerRepository,
                                      GameRepository gameRepository,
                                      GamePlayerRepository gamePlayerRepository) {

        return (args) -> {


            playerRepository.save(new Player("kim_bauer@gmail.com"));
            playerRepository.save(new Player("t.almeida@ctu.gov"));


            Date d1 = new Date();
            Date d2 = Date.from(d1.toInstant().plusSeconds(3600));
            Date d3 = Date.from(d1.toInstant().plusSeconds(7200));

            Game g1 = new Game(d1);
            Game g2 = new Game(d2);
            Game g3 = new Game(d3);
            gameRepository.save(g1);
            gameRepository.save(new Game(d2));
            gameRepository.save(g3);

            gamePlayerRepository.save(new GamePlayer("hola"));

        };
    }


}
