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


    private Player gamePlayerUserName;


    public GamePlayer() { }

    public GamePlayer(Player argGamePlayerUserName) {
        gamePlayerUserName = argGamePlayerUserName;

    }

    public Player getGamePlayerUserName() {
        return gamePlayerUserName;
    }

    public void setGamePlayerUserName(Player gamePlayerUserName) {
        this.gamePlayerUserName = gamePlayerUserName;
    }


//    public GamePlayer toString() {
//        return gamePlayerUserName;
//    }
}
