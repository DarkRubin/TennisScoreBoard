package controller;


import match.MatchScore;
import model.Player;
import match.PlayerScore;
import match.Points;
import org.junit.jupiter.api.Test;
import service.MatchScoreCalculationService;
import service.OngoingMatchesService;

import static match.Points.*;

class MatchScoreCalculationServiceTest {

    public static final String FIRST = "Alex";
    public static final String SECOND = "Roman";
    private final OngoingMatchesService ongoingService = new OngoingMatchesService();
    private final MatchScoreCalculationService service = new MatchScoreCalculationService();
    private final MatchScore matchScore = ongoingService.startNewMatch(FIRST, SECOND);


    MatchScore getExampleMatch() {
        matchScore.setFinished(false);
        matchScore.setTiebreak(false);
        matchScore.setWinner(null);
        matchScore.setFirstPlayerScore(new PlayerScore(0, 4, THIRTY));
        matchScore.setSecondPlayerScore(new PlayerScore(0, 5, FORTY));
        return matchScore;
    }

    Points getPlayerPoints(MatchScore matchScore,boolean isFirst) {
        if (isFirst) {
            return matchScore.getFirstPlayerScore().getPoints();
        } else {
            return matchScore.getSecondPlayerScore().getPoints();
        }
    }

    @Test
    void matchWinning() {
        matchScore.setFirstPlayerScore(new PlayerScore(1, 4, THIRTY));
        matchScore.setSecondPlayerScore(new PlayerScore(1, 5, FORTY));
        service.playerWinPoint(matchScore.getPlayer2().getId(), matchScore);
        Player winner = matchScore.getWinner();
        assert SECOND.equals(winner.getName());
        assert matchScore.isFinished();
        assert matchScore.getSecondPlayerScore().getSets() == 2;
    }

    @Test
    void setFinishing() {
        MatchScore exampleMatch = getExampleMatch();
        service.playerWinPoint(exampleMatch.getPlayer2().getId(), exampleMatch);
        assert exampleMatch.getSecondPlayerScore().getSets() == 1;
        assert exampleMatch.getFirstPlayerScore().getSets() == 0;
        assert exampleMatch.getFirstPlayerScore().getGames() == 0;
        assert exampleMatch.getSecondPlayerScore().getGames() == 0;
    }

    @Test
    void gameFinishing() {
        MatchScore exampleMatch = getExampleMatch();
        exampleMatch.setFirstPlayerScore(new PlayerScore(0, 0, FORTY));
        exampleMatch.setSecondPlayerScore(new PlayerScore(0, 0, FIFTEEN));
        service.playerWinPoint(exampleMatch.getPlayer1().getId(), exampleMatch);
        assert exampleMatch.getFirstPlayerScore().getPoints() == ZERO;
        assert exampleMatch.getSecondPlayerScore().getPoints() == ZERO;
        assert exampleMatch.getFirstPlayerScore().getGames() == 1;
    }

    @Test
    void firstPlayerWinPoint() {
        MatchScore exampleMatch = getExampleMatch();
        exampleMatch.setFirstPlayerScore(new PlayerScore(0, 0, ZERO));
        exampleMatch.setSecondPlayerScore(new PlayerScore(0, 0, ZERO));
        service.playerWinPoint(exampleMatch.getPlayer1().getId(), exampleMatch);
        Points firstPlayerPoint = getPlayerPoints(exampleMatch, true);
        Points secondPlayerPoint = getPlayerPoints(exampleMatch, false);
        assert firstPlayerPoint == FIFTEEN;
        assert secondPlayerPoint == ZERO;
    }

    @Test
    void secondPlayerWinPoint() {
        MatchScore exampleMatch = getExampleMatch();
        exampleMatch.setFirstPlayerScore(new PlayerScore(0, 0, ZERO));
        exampleMatch.setSecondPlayerScore(new PlayerScore(0, 0, ZERO));
        service.playerWinPoint(exampleMatch.getPlayer2().getId(), exampleMatch);
        Points firstPlayerPoint = getPlayerPoints(exampleMatch, true);
        Points secondPlayerPoint = getPlayerPoints(exampleMatch, false);
        assert firstPlayerPoint == ZERO;
        assert secondPlayerPoint == FIFTEEN;
    }

    @Test
    void tiebreakStarting() {
        MatchScore exampleMatch = getExampleMatch();
        exampleMatch.setFirstPlayerScore(new PlayerScore(0, 5, FORTY));
        exampleMatch.setSecondPlayerScore(new PlayerScore(0, 6, THIRTY));
        service.playerWinPoint(exampleMatch.getPlayer1().getId(), exampleMatch);
        Points firstPlayerPoint = getPlayerPoints(exampleMatch, true);
        Points secondPlayerPoint = getPlayerPoints(exampleMatch, false);
        assert firstPlayerPoint == ZERO;
        assert secondPlayerPoint == ZERO;
        assert exampleMatch.isTiebreak();
        assert exampleMatch.getFirstPlayerScore().getGames() == 6;
    }

    @Test
    void tiebreakFinishing() {
        MatchScore exampleMatch = getExampleMatch();
        exampleMatch.setTiebreak(true);
        exampleMatch.setFirstPlayerScore(new PlayerScore(0, 7, ZERO));
        exampleMatch.setSecondPlayerScore(new PlayerScore(0, 8, ZERO));
        exampleMatch.getFirstPlayerScore().setTiebreakPoints(7);
        exampleMatch.getSecondPlayerScore().setTiebreakPoints(8);
        service.playerWinPoint(exampleMatch.getPlayer2().getId(), exampleMatch);
        assert !exampleMatch.isTiebreak();
        assert exampleMatch.getFirstPlayerScore().getGames() == 0;
        assert exampleMatch.getSecondPlayerScore().getGames() == 0;
        assert exampleMatch.getSecondPlayerScore().getSets() == 1;
    }

    @Test
    void tiebreakPointAdding() {
        MatchScore exampleMatch = getExampleMatch();
        exampleMatch.setTiebreak(true);
        exampleMatch.setFirstPlayerScore(new PlayerScore(1, 6, ZERO));
        exampleMatch.setSecondPlayerScore(new PlayerScore(1, 6, ZERO));
        service.playerWinPoint(exampleMatch.getPlayer1().getId(), exampleMatch);
        PlayerScore score = exampleMatch.getFirstPlayerScore();
        PlayerScore score2 = exampleMatch.getSecondPlayerScore();

        assert score.getTiebreakPoints() == 1;
        assert score2.getTiebreakPoints() == 0;
    }

}