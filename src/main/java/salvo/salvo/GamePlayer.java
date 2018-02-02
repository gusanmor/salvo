package salvo.salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class GamePlayer {

    // CAMBIO 2

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name="IDPlayer")
    private Player playerEnGameplayer;

    @ManyToOne
    @JoinColumn(name="IDGame")
    private Game gameEnGamePlayer;

    @OneToMany(mappedBy="shipEnGamePlayer", fetch=FetchType.EAGER)
    Set<Ship> ships = new HashSet<>();


    public GamePlayer() { }

    public GamePlayer(Player argGamePlayerUserName,
                      Game argGameGamePlayer) {
        this.playerEnGameplayer = argGamePlayerUserName;
        this.gameEnGamePlayer = argGameGamePlayer;
    }

    public Player getGamePlayerUserName() {
        return playerEnGameplayer;
    }
    public void setGamePlayerUserName(Player gamePlayerUserName) {
        this.playerEnGameplayer = gamePlayerUserName;
    }

    public Game getGameEnGamePlayers() {
        return gameEnGamePlayer;
    }

    public void setGameEnGamePlayers(Game argGameNiIdea) {
        this.gameEnGamePlayer = argGameNiIdea;
    }

    public long getId() {
        return id;
    }

    public void addShips(Ship parShipenGamePlayer) {
        parShipenGamePlayer.setShipEnGamePlayer(this);
        this.ships.add(parShipenGamePlayer);
        }

    public String toString() {
        return playerEnGameplayer+" "+gameEnGamePlayer;
    }
}
