package salvo.salvo;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy="gamePlayerShip", fetch=FetchType.EAGER)
    Set<Ship> ships = new HashSet<>();

    @OneToMany(mappedBy="gamePlayerSalvo", fetch=FetchType.EAGER)
    Set<Salvo> salvos = new HashSet<>();


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

    public Set<Ship> getShips() {
        return ships;
    }

    public void setGameEnGamePlayers(Game argGameNiIdea) {
        this.gameEnGamePlayer = argGameNiIdea;
    }

    public long getId() {
        return id;
    }

    public void addShips(Ship parShipenGamePlayer) {
        parShipenGamePlayer.setGamePlayerShip(this);
        this.ships.add(parShipenGamePlayer);
        }

    public Set<Salvo> getSalvos() {
        return salvos;
    }

    public String toString() {
        return ""+gameEnGamePlayer;
    }
}
