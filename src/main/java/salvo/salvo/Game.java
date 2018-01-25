package salvo.salvo;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    public Date fechaVar;

    @OneToMany(mappedBy="gameEnGamePlayer", fetch=FetchType.EAGER)
    Set<GamePlayer> gamePlayers;

    public Game() { }

    public Game(Date fechaArg) {
        this.fechaVar = fechaArg;

    }

    public long getId() {
        return id;
    }

    public Date getFechaVar() {
        return fechaVar;
    }

    public void setFechaVar(Date fechaVar) {
        this.fechaVar = fechaVar;
    }

    public String toString() {
        return ""+fechaVar;
    }
}
