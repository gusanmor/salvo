package salvo.salvo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GamePlayer_ {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private String GamePlayerUserName;


    public GamePlayer_() { }

    public GamePlayer_(String argGamePlayerUserName) {
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
