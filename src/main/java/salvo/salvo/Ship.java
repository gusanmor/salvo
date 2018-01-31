package salvo.salvo;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Ship {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @OneToMany(mappedBy="shipEnGamePlayer", fetch=FetchType.EAGER)
    Set<GamePlayer> gamePlayers;

    private String tipoBarco;

    public Ship() { }

    public Ship(String tipoBarcoPar) {
        tipoBarco = tipoBarcoPar;

    }

    public long getId() {
        return id;
    }

    public String getTipoBarco() {
        return tipoBarco;
    }

}
