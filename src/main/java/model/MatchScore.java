package model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Setter
public class MatchScore {

    @UuidGenerator
    private UUID uuid;
    private Player player1;
    private Player player2;
    private PlayerScore firstPlayerScore;
    private PlayerScore secondPlayerScore;

    public MatchScore(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        firstPlayerScore = new PlayerScore();
        secondPlayerScore = new PlayerScore();
    }

    public int getPlayerPoints(boolean isFirst) {
        return isFirst ? firstPlayerScore.getPoints() : secondPlayerScore.getPoints();
    }

    public void setPlayerPoints(boolean isFirst, int points) {
        if (isFirst) {
            firstPlayerScore.setPoints(points);
        } else {
            secondPlayerScore.setPoints(points);
        }
    }

    @Override
    public String toString() {
        return "MatchScore{" +
                "secondPlayerScore=" + secondPlayerScore +
                ", firstPlayerScore=" + firstPlayerScore +
                ", player2=" + player2 +
                ", player1=" + player1 +
                '}';
    }
}
