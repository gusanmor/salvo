package salvo.salvo;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Score {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name="IDGame")
    private Game gameEnScore;

    @ManyToOne
    @JoinColumn(name="IDPlayer")
    private Player playerEnScore;

    private double scoreV;
    private Date finishDateV;

    public Score(){}

    public Score(Double parScore, Date parFinishDate){
        this.scoreV = parScore;
        this.finishDateV = parFinishDate;
    }
}
