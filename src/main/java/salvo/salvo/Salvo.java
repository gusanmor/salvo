package salvo.salvo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Salvo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name="IDGameplayerSalvos")
    private GamePlayer gamePlayerSalvo;

    private int numeroTurnoV;

    @ElementCollection
    @Column(name="LocalSalvos")
    private List<String> locSalvoV = new ArrayList<>();

    public Salvo() {
    }

    public Salvo(int numeroTurnoP, ArrayList<String> locSalvoP) {
        this.numeroTurnoV = numeroTurnoP;
        this.locSalvoV = locSalvoP;
    }

    public int getNumeroTurnoV() {
        return numeroTurnoV;
    }

    public List<String> getLocSalvoV() {
        return locSalvoV;
    }

    public void setNumeroTurnoV(int numeroTurnoV) {
        this.numeroTurnoV = numeroTurnoV;
    }

    public void setGamePlayerSalvo(GamePlayer gamePlayerSalvo) {
        this.gamePlayerSalvo = gamePlayerSalvo;
    }
}
