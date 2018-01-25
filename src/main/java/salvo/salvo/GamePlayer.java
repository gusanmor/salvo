package salvo.salvo;

import javax.persistence.*;

@Entity
public class GamePlayer {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name="GamePlayerUserName")


    private String GamePlayerUserName;


    public GamePlayer() { }

    public GamePlayer(String argGamePlayerUserName) {
        GamePlayerUserName = argGamePlayerUserName;

    }

    public String getGamePlayerUserName() {
        return GamePlayerUserName;
    }

    public void setGamePlayerUserName(String gamePlayerUserName) {
        this.GamePlayerUserName = gamePlayerUserName;
    }


    public String toString() {
        return GamePlayerUserName;
    }
}
