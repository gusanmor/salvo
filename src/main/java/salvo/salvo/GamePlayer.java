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

//    @ManyToOne
//    @JoinColumn(name="IDGame")
//    private Game gameEnGameplayer;


//    private Player gamePlayerUserName;


    public GamePlayer() { }

    public GamePlayer(Player argGamePlayerUserName) {
        this.playerEnGameplayer = argGamePlayerUserName;

    }

    public Player getGamePlayerUserName() {
        return playerEnGameplayer;
    }

    public void setGamePlayerUserName(Player gamePlayerUserName) {

        this.playerEnGameplayer = gamePlayerUserName;
    }


//    public GamePlayer toString() {
//        return gamePlayerUserName;
////    }
}
