package salvo.salvo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ship {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name="IDGameplayer")
    private GamePlayer gamePlayerShip;

    private String tipoBarcoV;


//    private ArrayList<String> locBarcoV;

    @ElementCollection
    @Column(name="LocalBarcos")
    private List<String> locBarcoV = new ArrayList<>();

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

    public List<String> getLocBarcoV() {
        return locBarcoV;
    }

    public void setGamePlayerShip(GamePlayer gamePlayerShip) {
        this.gamePlayerShip = gamePlayerShip;
    }

    //    public void addSubscription(Ship parAñadirBarcos) {
//        parAñadirBarcos.setGameplayer(this);
//        subscriptions.add(parAñadirBarcos);
//    }
}
