package salvo.salvo;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Score {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name="IDGame")
    private Game gameEnScore;

    @ManyToOne
    @JoinColumn(name="IDPlayer")
    private Player playerEnScore;

    private double scoreV;
    private Date finishDateV;

    public Score() {
    }

    public Score(Double scoreP, Date finishDateP){
        this.scoreV = scoreP;
        this.finishDateV = finishDateP;
    }

    public long getId() {
        return id;
    }

    public double getScoreV() {
        return scoreV;
    }

    public Date getFinishDateV() {
        return finishDateV;
    }

    public Game getGameEnScore() {
        return gameEnScore;
    }

    public void setGameEnScore(Game gameEnScore) {
        this.gameEnScore = gameEnScore;
    }

    public void setPlayerEnScore(Player playerEnScore) {
        this.playerEnScore = playerEnScore;
    }
}
