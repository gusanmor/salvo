package salvo.salvo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Salvo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int numeroTurnoV;

    @ElementCollection
    @Column(name="LocalSalvos")
    private List<String> locSalvoV = new ArrayList<>();

    public Salvo() {
    }

    public Salvo(int numeroTurnoPar, ArrayList<String> locSalvoPar) {
        this.numeroTurnoV = numeroTurnoPar;
        this.locSalvoV = locSalvoPar;
    }
}
