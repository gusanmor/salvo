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
                                      ShipRepository shipRepository, SalvoRepository salvoRepository,
                                      ScoreRepository scoreRepository) {

        return (args) -> {

            Player player1V = new Player("j.bauer@ctu.gov");
            Player player2V = new Player("c.obrian@ctu.gov");
            Player player3V = new Player("kim_bauer@gmail.com");
            Player player4V = new Player("t.almeida@ctu.gov");

            Date date1V = new Date();
            Date date2V = Date.from(date1V.toInstant().plusSeconds(3600));
            Date date3V = Date.from(date1V.toInstant().plusSeconds(7200));
            Date date4V = Date.from(date1V.toInstant().plusSeconds(10800));
            Date date5V = Date.from(date1V.toInstant().plusSeconds(14400));
            Date date6V = Date.from(date1V.toInstant().plusSeconds(18000));

            Game game1V = new Game(date1V);
            Game game2V = new Game(date2V);
            Game game3V = new Game(date3V);
            Game game4V = new Game(date4V);
            Game game5V = new Game(date5V);
            Game game6V = new Game(date6V);

            GamePlayer gamePlayer1Var = new GamePlayer(player1V, game1V);
            GamePlayer gamePlayer2Var = new GamePlayer(player2V, game1V);
            GamePlayer gamePlayer3Var = new GamePlayer(player1V, game2V);
            GamePlayer gamePlayer4Var = new GamePlayer(player2V, game2V);
            GamePlayer gamePlayer5Var = new GamePlayer(player2V, game3V);
            GamePlayer gamePlayer6Var = new GamePlayer(player4V, game3V);

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

            Score score1 = new Score(1.0, date2V);
            Score score2 = new Score(0.0, date2V);
            Score score3 = new Score(0.5, date3V);
            Score score4 = new Score(0.5, date3V);
            Score score5 = new Score();
            Score score6 = new Score();


gamePlayer1Var.addShips(ship1);
gamePlayer1Var.addShips(ship2);
gamePlayer2Var.addShips(ship3);

            gamePlayer1Var.addSalvos(salvo1);
            gamePlayer1Var.addSalvos(salvo2);
            gamePlayer2Var.addSalvos(salvo3);
            gamePlayer2Var.addSalvos(salvo4);
            game1V.addScore(score1);
            player1V.addScore(score1);
            game1V.addScore(score2);
            player2V.addScore(score2);

            game2V.addScore(score3);
            player1V.addScore(score3);
            game2V.addScore(score4);
            player2V.addScore(score4);

            game3V.addScore(score5);
            player2V.addScore(score5);

            game3V.addScore(score6);
            player4V.addScore(score6);

            playerRepository.save(player1V);
            playerRepository.save(player2V);
            playerRepository.save(player3V);
            playerRepository.save(player4V);

            gameRepository.save(game1V);
            gameRepository.save(game2V);
            gameRepository.save(game3V);

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

            scoreRepository.save(score1);
            scoreRepository.save(score2);
            scoreRepository.save(score3);
            scoreRepository.save(score4);
            scoreRepository.save(score5);
            scoreRepository.save(score6);
        };
    }


}
