package salvo.salvo;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @OneToMany(mappedBy="playerEnGameplayer", fetch=FetchType.EAGER)
    Set<GamePlayer> gamePlayers;

    private String userName;


    public Player() { }

    public Player(String argUserName) {
        userName = argUserName;

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
