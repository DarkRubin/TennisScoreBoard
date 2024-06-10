package match;

import lombok.Getter;
import lombok.Setter;
import model.Player;

import java.util.UUID;

@Getter
@Setter
public class MatchScore {

    private UUID uuid;
    private Player player1;
    private Player player2;
    private PlayerScore firstPlayerScore;
    private PlayerScore secondPlayerScore;
    private boolean isFinished;
    private boolean isTiebreak;
    private Player winner;

    public MatchScore(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.firstPlayerScore = new PlayerScore();
        this.secondPlayerScore = new PlayerScore();
        this.uuid = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return "Match{" +
                "firstPlayerScore=" +  firstPlayerScore +
                ", secondPlayerScore=" + secondPlayerScore +
                ", player1=" + player1 +
                ", player2=" + player2 +
                '}';
    }
}
