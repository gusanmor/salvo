package salvo.salvo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Ship {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

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
