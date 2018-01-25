package salvo.salvo;

import javax.persistence.*;

@Entity
public class GamePlayer {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name="IDPlayer")
    private Player playerEnGameplayer;

    @ManyToOne
    @JoinColumn(name="IDGame")
    private Player gameEnGameplayer;


    private String gamePlayerUserName;


    public GamePlayer() { }

    public GamePlayer(String argGamePlayerUserName) {
        gamePlayerUserName = argGamePlayerUserName;

    }

    public String getGamePlayerUserName() {
        return gamePlayerUserName;
    }

    public void setGamePlayerUserName(String gamePlayerUserName) {
        this.gamePlayerUserName = gamePlayerUserName;
    }


    public String toString() {
        return gamePlayerUserName;
    }
}
