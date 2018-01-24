package salvo.salvo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GamePlayer {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

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
