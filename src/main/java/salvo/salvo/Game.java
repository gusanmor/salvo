package salvo.salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private Date fechaV;

    @JsonIgnore
    @OneToMany(mappedBy="gameEnGamePlayer", fetch=FetchType.EAGER)
    Set<GamePlayer> gamePlayers;

    @OneToMany(mappedBy="gameEnScore", fetch=FetchType.EAGER)
    Set<Score> scores = new HashSet<>();

    public Game() { }

    public Game(Date fechaP) {
        this.fechaV = fechaP;
    }

    public long getId() {
        return id;
    }

    public Date getFechaV() {
        return fechaV;
    }

    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }


    public void addScore(Score scoreP) {
        scoreP.setGameEnScore(this);
        this.scores.add(scoreP);
    }


    public String toString() {
        return ""+ fechaV;
    }
}
