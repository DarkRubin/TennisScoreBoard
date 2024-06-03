package MatchScoreController;

import model.FinishedMatch;
import model.MatchScore;
import model.Player;
import java.util.UUID;


public class OngoingMatchesService extends Service {

    private final MatchScoreCalculationService scoreService = new MatchScoreCalculationService();
    private final FinishedMatchesPersistenceService finishedMatchesService = new FinishedMatchesPersistenceService();

    public UUID startNewMatch(String one, String two) {

        Player player1 = dao.findOrSavePlayer(one);
        Player player2 = dao.findOrSavePlayer(two);

        MatchScore matchScore = new MatchScore(player1, player2);

        ongoingMatches.put(matchScore.getUuid(), matchScore);
        return matchScore.getUuid();
    }

    public void firstPlayerWinPoint(UUID uuid) {
        scoreService.playerWinPoint(true, uuid);
    }

    public void secondPlayerWinPoint(UUID uuid) {
        scoreService.playerWinPoint(false, uuid);
    }

    protected void matchEnd(UUID uuid) {
        FinishedMatch finishedMatch = finishedMatchesService.finishMatch(ongoingMatches.get(uuid));
        finishedMatchesService.saveMatch(finishedMatch);
    }

}
