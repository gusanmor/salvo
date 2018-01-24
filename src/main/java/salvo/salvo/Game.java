package salvo.salvo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private String fechaVar;



    public Game() { }

    public Game(String fechaArg) {
        this.fechaVar = fechaArg;

    }


    public String getFechaVar() {
        return fechaVar;
    }

    public void setFechaVar(String fechaVar) {
        this.fechaVar = fechaVar;
    }


    public String toString() {
        return fechaVar;
    }

}
