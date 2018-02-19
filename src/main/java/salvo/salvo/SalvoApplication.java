package salvo.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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

            Player player1V = new Player("j.bauer@ctu.gov","24");
            Player player2V = new Player("c.obrian@ctu.gov","42");
            Player player3V = new Player("kim_bauer@gmail.com","kb");
            Player player4V = new Player("t.almeida@ctu.gov","mole");

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

            ArrayList<String> localBarco1 = new ArrayList<String>(Arrays.asList("H2", "H3", "H4"));
            ArrayList<String> localBarco2 = new ArrayList<String>(Arrays.asList("E1", "F1", "G1"));
            ArrayList<String> localBarco3 = new ArrayList<String>(Arrays.asList("B4", "B5"));
            ArrayList<String> localBarco4 = new ArrayList<String>(Arrays.asList("B5", "C5","D5"));
            ArrayList<String> localBarco5 = new ArrayList<String>(Arrays.asList("F1", "F2"));
            ArrayList<String> localBarco6 = new ArrayList<String>(Arrays.asList("B5", "C5","D5"));
            ArrayList<String> localBarco7 = new ArrayList<String>(Arrays.asList("C6", "C7"));
            ArrayList<String> localBarco8 = new ArrayList<String>(Arrays.asList("A2", "A3","A4"));
            ArrayList<String> localBarco9 = new ArrayList<String>(Arrays.asList("G6", "H6"));
            ArrayList<String> localBarco10 = new ArrayList<String>(Arrays.asList("B5", "C5","D5"));
            ArrayList<String> localBarco11 = new ArrayList<String>(Arrays.asList("C6", "C7"));
            ArrayList<String> localBarco12 = new ArrayList<String>(Arrays.asList("A2", "A3","A4"));
            ArrayList<String> localBarco13 = new ArrayList<String>(Arrays.asList("G6", "H6"));

            Ship ship1 = new Ship("Destroyer", localBarco1);
            gamePlayer1Var.addShips(ship1);
            Ship ship2 = new Ship("Submarine", localBarco2);
            gamePlayer1Var.addShips(ship2);
            Ship ship3 = new Ship("Patrol Boat", localBarco3);
            gamePlayer1Var.addShips(ship3);
            Ship ship4 = new Ship("Destroyer", localBarco4);
            gamePlayer2Var.addShips(ship4);
            Ship ship5 = new Ship("Patrol Boat", localBarco5);
            gamePlayer2Var.addShips(ship5);
            Ship ship6 = new Ship("Destroyer", localBarco6);
            gamePlayer3Var.addShips(ship6);
            Ship ship7 = new Ship("Patrol Boat", localBarco7);
            gamePlayer3Var.addShips(ship7);
            Ship ship8 = new Ship("Submarine", localBarco8);
            gamePlayer4Var.addShips(ship8);
            Ship ship9 = new Ship("Patrol Boat", localBarco9);
            gamePlayer4Var.addShips(ship9);
            Ship ship10 = new Ship("Destroyer", localBarco10);
            gamePlayer5Var.addShips(ship10);
            Ship ship11 = new Ship("Patrol Boat", localBarco11);
            gamePlayer5Var.addShips(ship11);
            Ship ship12 = new Ship("Submarine", localBarco12);
            gamePlayer6Var.addShips(ship12);
            Ship ship13 = new Ship("Patrol Boat", localBarco13);
            gamePlayer6Var.addShips(ship13);

            ArrayList<String> localSalvo1 = new ArrayList<String>(Arrays.asList("B5", "C5","F1"));
            ArrayList<String> localSalvo2 = new ArrayList<String>(Arrays.asList("F2", "D5"));
            ArrayList<String> localSalvo3 = new ArrayList<String>(Arrays.asList("A2", "A4","G6"));
            ArrayList<String> localSalvo4 = new ArrayList<String>(Arrays.asList("A3", "H6"));
            ArrayList<String> localSalvo5 = new ArrayList<String>(Arrays.asList("G6", "H6","A4"));
            ArrayList<String> localSalvo6 = new ArrayList<String>(Arrays.asList("A2", "A3","D8"));
            ArrayList<String> localSalvo7 = new ArrayList<String>(Arrays.asList("B4", "B5","B6"));
            ArrayList<String> localSalvo8 = new ArrayList<String>(Arrays.asList("E1", "H3","A2"));
            ArrayList<String> localSalvo9 = new ArrayList<String>(Arrays.asList("B5", "D5","C7"));
            ArrayList<String> localSalvo10 = new ArrayList<String>(Arrays.asList("C5", "C6"));
            ArrayList<String> localSalvo11 = new ArrayList<String>(Arrays.asList("H1", "H2","H3"));
            ArrayList<String> localSalvo12 = new ArrayList<String>(Arrays.asList("E1", "F2","G3"));

            Salvo salvo1 = new Salvo(1, localSalvo1);
            gamePlayer1Var.addSalvos(salvo1);
            Salvo salvo2 = new Salvo(2, localSalvo2);
            gamePlayer1Var.addSalvos(salvo2);
            Salvo salvo3 = new Salvo(1, localSalvo3);
            gamePlayer3Var.addSalvos(salvo3);
            Salvo salvo4 = new Salvo(2, localSalvo4);
            gamePlayer3Var.addSalvos(salvo4);
            Salvo salvo5 = new Salvo(1, localSalvo5);
            gamePlayer5Var.addSalvos(salvo5);
            Salvo salvo6 = new Salvo(2, localSalvo6);
            gamePlayer5Var.addSalvos(salvo6);
            Salvo salvo7 = new Salvo(1, localSalvo7);
            gamePlayer2Var.addSalvos(salvo7);
            Salvo salvo8 = new Salvo(2, localSalvo8);
            gamePlayer2Var.addSalvos(salvo8);
            Salvo salvo9 = new Salvo(1, localSalvo9);
            gamePlayer4Var.addSalvos(salvo9);
            Salvo salvo10 = new Salvo(2, localSalvo10);
            gamePlayer4Var.addSalvos(salvo10);
            Salvo salvo11 = new Salvo(1, localSalvo11);
            gamePlayer6Var.addSalvos(salvo11);
            Salvo salvo12 = new Salvo(2, localSalvo12);
            gamePlayer6Var.addSalvos(salvo12);

            Score score1 = new Score(1.0, date2V);
            game1V.addScore(score1);
            player1V.addScore(score1);
            Score score2 = new Score(0.0, date2V);
            game1V.addScore(score2);
            player2V.addScore(score2);
            Score score3 = new Score(0.5, date3V);
            game2V.addScore(score3);
            player1V.addScore(score3);
            Score score4 = new Score(0.5, date3V);
            game2V.addScore(score4);
            player2V.addScore(score4);
//            Score score5 = new Score();
//            game3V.addScore(score5);
//            player2V.addScore(score5);
//            Score score6 = new Score();
//            game3V.addScore(score6);
//            player4V.addScore(score6);

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
            shipRepository.save(ship4);
            shipRepository.save(ship5);
            shipRepository.save(ship6);
            shipRepository.save(ship7);
            shipRepository.save(ship8);
            shipRepository.save(ship9);
            shipRepository.save(ship10);
            shipRepository.save(ship11);
            shipRepository.save(ship12);
            shipRepository.save(ship13);

            salvoRepository.save(salvo1);
            salvoRepository.save(salvo2);
            salvoRepository.save(salvo3);
            salvoRepository.save(salvo4);
            salvoRepository.save(salvo5);
            salvoRepository.save(salvo6);
            salvoRepository.save(salvo7);
            salvoRepository.save(salvo8);
            salvoRepository.save(salvo9);
            salvoRepository.save(salvo10);
            salvoRepository.save(salvo11);
            salvoRepository.save(salvo12);

            scoreRepository.save(score1);
            scoreRepository.save(score2);
            scoreRepository.save(score3);
            scoreRepository.save(score4);
//            scoreRepository.save(score5);
//            scoreRepository.save(score6);
        };
    }


}

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    PlayerRepository playerRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Bean
    UserDetailsService userDetailsService() {
        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
                List<Player> people = playerRepository.findByUserName(name);
                if (!people.isEmpty()) {
                    Player person = people.get(0);
                    return new User(person.getUserName(), person.getPassword(),
                            AuthorityUtils.createAuthorityList("USER"));
                } else {
                    throw new UsernameNotFoundException("Unknown user: " + name);
                }
            }
        };
    }
}

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().fullyAuthenticated().
                and().httpBasic();
    }

}


