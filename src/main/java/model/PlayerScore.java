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
    private Points points = Points.ZERO;

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
