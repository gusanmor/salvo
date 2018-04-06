package salvo.salvo;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @OneToMany(mappedBy="playerEnGameplayer", fetch=FetchType.EAGER)
    Set<GamePlayer> gamePlayers;

    @OneToMany(mappedBy="playerEnScore", fetch=FetchType.EAGER)
    Set<Score> scores = new HashSet<>();



    private String userName;
    private String password;


    public Player() { }

    public Player(String userNameP, String passwordP) {
        this.userName = userNameP;
        this.password = passwordP;
    }

    public void addScore(Score scoreP) {
        scoreP.setPlayerEnScore(this);
        this.scores.add(scoreP);
    }

//    public void setScores(Set<Score> scores) {
//        this.scores = scores;
//    }

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Score> getScores() {
        return scores;
    }

    public Score get1Score(Game game) {
        return scores
                .stream()
                .filter(score -> score.getGameEnScore().getId() == game.getId())
                .findFirst()
                .orElse(null);
    }

//    public Set<Score> getScoresdeGame(Game gamePar) {
////        gamePar.filter
//        return scores;
//    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String toString() {
        return userName;
    }
}
