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


    public Player() { }

    public Player(String argUserName) {
        userName = argUserName;

    }

    public void addScore(Score scorePar) {
        scorePar.setPlayerEnScore(this);
//        this.scores.add(scorePar);
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

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String toString() {
        return userName;
    }
}
