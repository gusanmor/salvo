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
    public Date fechaVar;

    @JsonIgnore
    @OneToMany(mappedBy="gameEnGamePlayer", fetch=FetchType.EAGER)
    Set<GamePlayer> gamePlayers;

    @OneToMany(mappedBy="gameEnScore", fetch=FetchType.EAGER)
    Set<Score> scores = new HashSet<>();

    public Game() { }

    public Game(Date fechaArg) {
        this.fechaVar = fechaArg;

    }

    public long getId() {
        return id;
    }

    public Date getFechaVar() {
        return fechaVar;
    }

    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public void setFechaVar(Date fechaVar) {
        this.fechaVar = fechaVar;
    }

    public void addScore(Score scorePar) {
        scorePar.setGameEnScore(this);
        this.scores.add(scorePar);
    }

    public String toString() {
        return ""+fechaVar;
    }
}
