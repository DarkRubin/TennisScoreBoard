package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "players")
public class Player extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @ColumnDefault("nextval('players_id_seq'")
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", length = Integer.MAX_VALUE)
    private String name;

}