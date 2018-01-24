package salvo.salvo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private Date fechaVar;



    public Game() { }

    public Game(Date fechaArg) {
        this.fechaVar = fechaArg;

    }


    public Date getFechaVar() {
        return fechaVar;
    }

    public void setFechaVar(Date fechaVar) {
        this.fechaVar = fechaVar;
    }




}
