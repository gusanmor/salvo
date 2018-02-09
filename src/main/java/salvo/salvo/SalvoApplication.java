package salvo.salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class SalvoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SalvoApplication.class, args);
    }


    @Bean
    public CommandLineRunner initData(PlayerRepository playerRepository,
                                      GameRepository gameRepository,
                                      GamePlayerRepository gamePlayerRepository,
                                      ShipRepository shipRepository, SalvoRepository salvoRepository) {

        return (args) -> {

            Player jugador1Var = new Player("j.bauer@ctu.gov");
            Player jugador2Var = new Player("c.obrian@ctu.gov");
            Player jugador3Var = new Player("kim_bauer@gmail.com");
            Player jugador4Var = new Player("t.almeida@ctu.gov");

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

            GamePlayer gamePlayer1Var = new GamePlayer(jugador1Var, partida1Var);
            GamePlayer gamePlayer2Var = new GamePlayer(jugador2Var, partida1Var);
            GamePlayer gamePlayer3Var = new GamePlayer(jugador1Var, partida2Var);
            GamePlayer gamePlayer4Var = new GamePlayer(jugador2Var, partida2Var);
            GamePlayer gamePlayer5Var = new GamePlayer(jugador2Var, partida3Var);
            GamePlayer gamePlayer6Var = new GamePlayer(jugador4Var, partida3Var);

            ArrayList<String> localBarco1 = new ArrayList<String>(Arrays.asList("A1", "A2", "A3"));
            ArrayList<String> localBarco2 = new ArrayList<String>(Arrays.asList("E1", "F1", "G1"));
            ArrayList<String> localBarco3 = new ArrayList<String>(Arrays.asList("C1", "D1", "E1"));

            Ship ship1 = new Ship("Destroyer", localBarco1);
            Ship ship2 = new Ship("Submarine", localBarco2);
            Ship ship3 = new Ship("Submarine", localBarco3);

            ArrayList<String> localSalvo1 = new ArrayList<String>(Arrays.asList("C9", "E9"));
            ArrayList<String> localSalvo2 = new ArrayList<String>(Arrays.asList("D1", "D3"));
            ArrayList<String> localSalvo3 = new ArrayList<String>(Arrays.asList("E1", "E3"));
            ArrayList<String> localSalvo4 = new ArrayList<String>(Arrays.asList("A1", "A3"));

            Salvo salvo1 = new Salvo(1, localSalvo1);
            Salvo salvo2 = new Salvo(2, localSalvo2);
            Salvo salvo3 = new Salvo(1, localSalvo3);
            Salvo salvo4 = new Salvo(2, localSalvo4);


gamePlayer1Var.addShips(ship1);
gamePlayer1Var.addShips(ship2);
gamePlayer2Var.addShips(ship3);

            gamePlayer1Var.addSalvos(salvo1);
            gamePlayer1Var.addSalvos(salvo2);
            gamePlayer2Var.addSalvos(salvo3);
            gamePlayer2Var.addSalvos(salvo4);

            playerRepository.save(jugador1Var);
            playerRepository.save(jugador2Var);
            playerRepository.save(jugador3Var);
            playerRepository.save(jugador4Var);

            gameRepository.save(partida1Var);
            gameRepository.save(partida2Var);
            gameRepository.save(partida3Var);

            gamePlayerRepository.save(gamePlayer1Var);
            gamePlayerRepository.save(gamePlayer2Var);
            gamePlayerRepository.save(gamePlayer3Var);
            gamePlayerRepository.save(gamePlayer4Var);
            gamePlayerRepository.save(gamePlayer5Var);
            gamePlayerRepository.save(gamePlayer6Var);

            shipRepository.save(ship1);
            shipRepository.save(ship2);
            shipRepository.save(ship3);

            salvoRepository.save(salvo1);
            salvoRepository.save(salvo2);
            salvoRepository.save(salvo3);
            salvoRepository.save(salvo4);
        };
    }


}
