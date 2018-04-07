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

    public GamePlayer(Player gpUserNameP,
                      Game gameEnGPp) {
        this.playerEnGameplayer = gpUserNameP;
        this.gameEnGamePlayer = gameEnGPp;
    }

    public Player getPlayer() {
        return playerEnGameplayer;
    }

    public Game getGame() {
        return gameEnGamePlayer;
    }

    public Set<Ship> getShips() {
        return ships;
    }

    public long getId() {
        return id;
    }

    public void addShips(Ship shipenGPp) {
        shipenGPp.setGamePlayerShip(this);
        this.ships.add(shipenGPp);
        }

    public void addSalvos(Salvo salvoEnGPp) {
        salvoEnGPp.setGamePlayerSalvo(this);
        this.salvos.add(salvoEnGPp);
    }

    public Set<Salvo> getSalvos() {
        return salvos;
    }

    public String toString() {
        return ""+gameEnGamePlayer;
    }

}