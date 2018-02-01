package salvo.salvo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Set;

@Entity
public class Ship {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @OneToMany(mappedBy="shipEnGamePlayer", fetch=FetchType.EAGER)
    Set<GamePlayer> gamePlayers;

    private String tipoBarcoV;
    private ArrayList<String> locBarcoV;

    public Ship() { }

    public Ship(String tipoBarcoPar, ArrayList<String> locBarcoPar) {
        this.tipoBarcoV = tipoBarcoPar;
        this.locBarcoV = locBarcoPar;

    }

    public long getId() {
        return id;
    }

    public String getTipoBarcoV() {
        return tipoBarcoV;
    }

    public ArrayList<String> getLocBarcoV() {
        return locBarcoV;
    }

//    public void addSubscription(Ship parAñadirBarcos) {
//        parAñadirBarcos.setGameplayer(this);
//        subscriptions.add(parAñadirBarcos);
//    }
}
