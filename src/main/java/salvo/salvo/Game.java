package salvo.salvo;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @OneToMany(mappedBy="gameEnGameplayer", fetch=FetchType.EAGER)
    Set<GamePlayer> gamePlayers;

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
