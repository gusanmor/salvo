package salvo.salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private Game gameEnGamePlayer;

    @ManyToOne
    @JoinColumn(name="IDShip")
    private Ship shipEnGamePlayer;

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

    public String toString() {
        return playerEnGameplayer+" "+gameEnGamePlayer;
    }
}
