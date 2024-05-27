package MatchScoreController;


import model.MatchScore;
import model.PlayerScore;
import model.Points;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class MatchScoreCalculationServiceTest {

    private final OngoingMatchesService ongoingService = new OngoingMatchesService();
    private final MatchScoreCalculationService service = new MatchScoreCalculationService();
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
    }

}