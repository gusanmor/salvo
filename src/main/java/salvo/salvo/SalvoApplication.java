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
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

            GamePlayer gamePlayer1V = new GamePlayer(player1V, game1V);
            GamePlayer gamePlayer2V = new GamePlayer(player2V, game1V);
            GamePlayer gamePlayer3V = new GamePlayer(player1V, game2V);
            GamePlayer gamePlayer4V = new GamePlayer(player2V, game2V);
            GamePlayer gamePlayer5V = new GamePlayer(player2V, game3V);
            GamePlayer gamePlayer6V = new GamePlayer(player4V, game3V);
            GamePlayer gamePlayer9V = new GamePlayer(player3V, game6V);

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
            gamePlayer1V.addShips(ship1);
            Ship ship2 = new Ship("Submarine", localBarco2);
            gamePlayer1V.addShips(ship2);
            Ship ship3 = new Ship("PatrolBoat", localBarco3);
            gamePlayer1V.addShips(ship3);
            Ship ship4 = new Ship("Destroyer", localBarco4);
            gamePlayer2V.addShips(ship4);
            Ship ship5 = new Ship("PatrolBoat", localBarco5);
            gamePlayer2V.addShips(ship5);
            Ship ship6 = new Ship("Destroyer", localBarco6);
            gamePlayer3V.addShips(ship6);
            Ship ship7 = new Ship("PatrolBoat", localBarco7);
            gamePlayer3V.addShips(ship7);
            Ship ship8 = new Ship("Submarine", localBarco8);
            gamePlayer4V.addShips(ship8);
            Ship ship9 = new Ship("PatrolBoat", localBarco9);
            gamePlayer4V.addShips(ship9);
            Ship ship10 = new Ship("Destroyer", localBarco10);
            gamePlayer5V.addShips(ship10);
            Ship ship11 = new Ship("PatrolBoat", localBarco11);
            gamePlayer5V.addShips(ship11);
            Ship ship12 = new Ship("Submarine", localBarco12);
            gamePlayer6V.addShips(ship12);
            Ship ship13 = new Ship("PatrolBoat", localBarco13);
            gamePlayer6V.addShips(ship13);

            ArrayList<String> localSalvo1 = new ArrayList<String>(Arrays.asList("B5s", "C5s","F1s"));
            ArrayList<String> localSalvo2 = new ArrayList<String>(Arrays.asList("F2s", "D5s"));
            ArrayList<String> localSalvo3 = new ArrayList<String>(Arrays.asList("A2s", "A4s","G6s"));
            ArrayList<String> localSalvo4 = new ArrayList<String>(Arrays.asList("A3s", "H6s"));
            ArrayList<String> localSalvo5 = new ArrayList<String>(Arrays.asList("G6s", "H6s","A4s"));
            ArrayList<String> localSalvo6 = new ArrayList<String>(Arrays.asList("A2s", "A3s","D8s"));
            ArrayList<String> localSalvo7 = new ArrayList<String>(Arrays.asList("B4s", "B5s","B6s"));
            ArrayList<String> localSalvo8 = new ArrayList<String>(Arrays.asList("E1s", "H3s","A2s"));
            ArrayList<String> localSalvo9 = new ArrayList<String>(Arrays.asList("B5s", "D5s","C7s"));
            ArrayList<String> localSalvo10 = new ArrayList<String>(Arrays.asList("C5s", "C6s"));
            ArrayList<String> localSalvo11 = new ArrayList<String>(Arrays.asList("H1s", "H2s","H3s"));
            ArrayList<String> localSalvo12 = new ArrayList<String>(Arrays.asList("E1s", "F2s","G3s"));

            Salvo salvo1 = new Salvo(1, localSalvo1);
            gamePlayer1V.addSalvos(salvo1);
            Salvo salvo2 = new Salvo(2, localSalvo2);
            gamePlayer1V.addSalvos(salvo2);
            Salvo salvo3 = new Salvo(1, localSalvo3);
            gamePlayer3V.addSalvos(salvo3);
            Salvo salvo4 = new Salvo(2, localSalvo4);
            gamePlayer3V.addSalvos(salvo4);
            Salvo salvo5 = new Salvo(1, localSalvo5);
            gamePlayer5V.addSalvos(salvo5);
            Salvo salvo6 = new Salvo(2, localSalvo6);
            gamePlayer5V.addSalvos(salvo6);
            Salvo salvo7 = new Salvo(1, localSalvo7);
            gamePlayer2V.addSalvos(salvo7);
            Salvo salvo8 = new Salvo(2, localSalvo8);
            gamePlayer2V.addSalvos(salvo8);
            Salvo salvo9 = new Salvo(1, localSalvo9);
            gamePlayer4V.addSalvos(salvo9);
            Salvo salvo10 = new Salvo(2, localSalvo10);
            gamePlayer4V.addSalvos(salvo10);
            Salvo salvo11 = new Salvo(1, localSalvo11);
            gamePlayer6V.addSalvos(salvo11);
            Salvo salvo12 = new Salvo(2, localSalvo12);
            gamePlayer6V.addSalvos(salvo12);

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

            playerRepository.save(player1V);
            playerRepository.save(player2V);
            playerRepository.save(player3V);
            playerRepository.save(player4V);

            gameRepository.save(game1V);
            gameRepository.save(game2V);
            gameRepository.save(game3V);
            gameRepository.save(game6V);

            gamePlayerRepository.save(gamePlayer1V);
            gamePlayerRepository.save(gamePlayer2V);
            gamePlayerRepository.save(gamePlayer3V);
            gamePlayerRepository.save(gamePlayer4V);
            gamePlayerRepository.save(gamePlayer5V);
            gamePlayerRepository.save(gamePlayer6V);
            gamePlayerRepository.save(gamePlayer9V);

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
            public UserDetails loadUserByUsername(String userNamePar) throws UsernameNotFoundException {
                List<Player> players = playerRepository.findByUserName(userNamePar);
                if (!players.isEmpty()) {
                    Player player = players.get(0);
                    return new User(player.getUserName(), player.getPassword(),
                            AuthorityUtils.createAuthorityList("USER"));
                } else {
                    throw new UsernameNotFoundException("Unknown user: " + userNamePar);
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
        http.authorizeRequests()
                .antMatchers("https://**/web/games.html","https://**/web/style.css","https://**/web/games.js","https://**/api/games","https://**/api/players","https://**/api/game/**","https://**/games/players/**","https://**/web/images/**").permitAll()
                .anyRequest().fullyAuthenticated();

        http.formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout");

        // turn off checking for CSRF tokens
        http.csrf().disable();

        // if user is not authenticated, just send an authentication failure response
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}