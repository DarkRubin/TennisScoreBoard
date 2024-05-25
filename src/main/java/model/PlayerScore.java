package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PlayerScore {
    private int sets;
    private int games;
    private int points;
}
