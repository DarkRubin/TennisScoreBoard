package MatchScoreController;


import model.MatchScore;
import model.Player;
import model.PlayerScore;
import model.Points;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class MatchScoreCalculationServiceTest {

    private final OngoingMatchesService ongoingService = new OngoingMatchesService();
    private final MatchScoreCalculationService service = new MatchScoreCalculationService();
    private final Player player1 = new Player("Alex");
    private final Player player2 = new Player("Roman");
    private final UUID uuid = ongoingService.startNewMatch("Alex", "Roman");

    @Test
    void pointAddTest() {

    }


    @Test
    void tiebreakTest() {
        MatchScore matchScore = Service.getMatchScore(uuid);
        matchScore.setFirstPlayerScore(new PlayerScore(1, 6, Points.ZERO));
        matchScore.setSecondPlayerScore(new PlayerScore(1, 6, Points.ZERO));
        service.playerWinPoint(true, uuid);
        PlayerScore score = matchScore.getThisPlayerScore(player1);
        PlayerScore score2 = matchScore.getThisPlayerScore(player2);
        int tiebreakPoints = score.getTiebreakPoints();
        int tiebreakPoints2 = score2.getTiebreakPoints();
        System.out.println(tiebreakPoints);
        System.out.println(tiebreakPoints2);
        assert tiebreakPoints == 1;
    }

}