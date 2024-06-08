package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@Table(name = "matches")
public class FinishedMatch extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "player1", referencedColumnName = "id")
    private Player player1;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "player2", referencedColumnName = "id")
    private Player player2;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "winner", referencedColumnName = "id")
    private Player winner;

    public FinishedMatch(Player player1, Player player2, Player winner) {
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
    }
}