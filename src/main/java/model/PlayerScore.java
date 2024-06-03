package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PlayerScore {
    private int sets;
    private int games;
    private Points points = Points.ZERO;
    private int tiebreakPoints;

    public PlayerScore(int sets, int games, Points points) {
        this.sets = sets;
        this.games = games;
        this.points = points;
    }

    public String getPointsInString() {
        return switch (points) {
            case ZERO -> "0";
            case FIFTEEN -> "15";
            case THIRTY -> "30";
            case FORTY -> "40";
            case MORE -> "More";
            case LESS -> "Less";
            case EXACTLY -> "Exactly";
        };
    }
}
