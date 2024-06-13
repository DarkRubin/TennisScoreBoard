package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "PLAYERS")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "NAME")
    private String name;

    public Player(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "player1", targetEntity = FinishedMatch.class, fetch = FetchType.LAZY)
    private Collection<FinishedMatch> finishedMatch;

    @OneToMany(mappedBy = "player2", targetEntity = FinishedMatch.class, fetch = FetchType.LAZY)
    private Collection<FinishedMatch> finishedMatch2;

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}