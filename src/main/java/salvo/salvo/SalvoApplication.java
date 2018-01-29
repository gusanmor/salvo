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

            Player jugador1Var = new Player("j.bauer@ctu.gov");
            Player jugador2Var = new Player("c.obrian@ctu.gov");
            Player jugador3Var = new Player("kim_bauer@gmail.com");
            Player jugador4Var = new Player("t.almeida@ctu.gov");


            playerRepository.save(jugador1Var);
            playerRepository.save(jugador2Var);
            playerRepository.save(jugador3Var);
            playerRepository.save(jugador4Var);


            Date fecha1Var = new Date();
            Date fecha2Var = Date.from(fecha1Var.toInstant().plusSeconds(3600));
            Date fecha3Var = Date.from(fecha1Var.toInstant().plusSeconds(7200));
            Date fecha4Var = Date.from(fecha1Var.toInstant().plusSeconds(10800));
            Date fecha5Var = Date.from(fecha1Var.toInstant().plusSeconds(14400));
            Date fecha6Var = Date.from(fecha1Var.toInstant().plusSeconds(18000));

            Game partida1Var = new Game(fecha1Var);
            Game partida2Var = new Game(fecha2Var);
            Game partida3Var = new Game(fecha3Var);
            Game partida4Var = new Game(fecha4Var);
            Game partida5Var = new Game(fecha5Var);
            Game partida6Var = new Game(fecha6Var);

            gameRepository.save(partida1Var);
            gameRepository.save(partida2Var);
            gameRepository.save(partida3Var);
            gameRepository.save(partida4Var);
            gameRepository.save(partida5Var);
            gameRepository.save(partida6Var);

            GamePlayer gamePlayer1Var = new GamePlayer(jugador1Var, partida1Var);
            GamePlayer gamePlayer2Var = new GamePlayer(jugador2Var, partida1Var);

            gamePlayerRepository.save(gamePlayer1Var);
            gamePlayerRepository.save(gamePlayer2Var);


//            System.out.println(jugador1Var);
//            System.out.println(partida1Var);
//            System.out.println(gamePlayer1Var);

        };
    }


}
