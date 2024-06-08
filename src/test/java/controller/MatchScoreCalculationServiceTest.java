package controller;


import model.MatchScore;
import model.Player;
import model.PlayerScore;
import model.Points;
import org.junit.jupiter.api.Test;
import service.MatchScoreCalculationService;
import service.OngoingMatchesService;

import static model.Points.*;

class MatchScoreCalculationServiceTest {

    public static final String PLAYER_NAME = "Roman";
    private final OngoingMatchesService ongoingService = new OngoingMatchesService();
    private final MatchScoreCalculationService service = new MatchScoreCalculationService();
    private final MatchScore matchScore = ongoingService.startNewMatch("Alex", PLAYER_NAME);


    MatchScore getExampleMatch() {
        matchScore.setFinished(false);
        matchScore.setTiebreak(false);
        matchScore.setWinner(null);
        matchScore.setFirstPlayerScore(new PlayerScore(0, 4, THIRTY));
        matchScore.setSecondPlayerScore(new PlayerScore(0, 5, FORTY));
        return matchScore;
    }

    @Test
    void matchWinning() {
        matchScore.setFirstPlayerScore(new PlayerScore(1, 4, THIRTY));
        matchScore.setSecondPlayerScore(new PlayerScore(1, 5, FORTY));
        service.playerWinPoint(2, matchScore);
        Player winner = matchScore.getWinner();
        assert PLAYER_NAME.equals(winner.getName());
        assert matchScore.isFinished();
        assert matchScore.getSecondPlayerScore().getSets() == 2;
    }

    @Test
    void setFinishing() {
        MatchScore exampleMatch = getExampleMatch();
        service.playerWinPoint(2, exampleMatch);
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
        service.playerWinPoint(1, exampleMatch);
        assert exampleMatch.getFirstPlayerScore().getPointsInString().equals("0");
        assert exampleMatch.getSecondPlayerScore().getPointsInString().equals("0");
        assert exampleMatch.getFirstPlayerScore().getGames() == 1;
    }

    @Test
    void firstPlayerWinPoint() {
        MatchScore exampleMatch = getExampleMatch();
        exampleMatch.setFirstPlayerScore(new PlayerScore(0, 0, ZERO));
        exampleMatch.setSecondPlayerScore(new PlayerScore(0, 0, ZERO));
        service.playerWinPoint(1, exampleMatch);
        Points firstPlayerPoint = exampleMatch.getPlayerPoints(exampleMatch.getPlayer1());
        Points secondPlayerPoint = exampleMatch.getPlayerPoints(exampleMatch.getPlayer2());
        assert firstPlayerPoint == FIFTEEN;
        assert secondPlayerPoint == ZERO;
    }

    @Test
    void secondPlayerWinPoint() {
        MatchScore exampleMatch = getExampleMatch();
        exampleMatch.setFirstPlayerScore(new PlayerScore(0, 0, ZERO));
        exampleMatch.setSecondPlayerScore(new PlayerScore(0, 0, ZERO));
        service.playerWinPoint(2, exampleMatch);
        Points firstPlayerPoint = exampleMatch.getPlayerPoints(exampleMatch.getPlayer1());
        Points secondPlayerPoint = exampleMatch.getPlayerPoints(exampleMatch.getPlayer2());
        assert firstPlayerPoint == ZERO;
        assert secondPlayerPoint == FIFTEEN;
    }

    @Test
    void tiebreakStarting() {
        MatchScore exampleMatch = getExampleMatch();
        exampleMatch.setFirstPlayerScore(new PlayerScore(0, 5, FORTY));
        exampleMatch.setSecondPlayerScore(new PlayerScore(0, 6, THIRTY));
        service.playerWinPoint(1, exampleMatch);
        Points firstPlayerPoint = exampleMatch.getPlayerPoints(exampleMatch.getPlayer1());
        Points secondPlayerPoint = exampleMatch.getPlayerPoints(exampleMatch.getPlayer2());
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
        service.playerWinPoint(2, exampleMatch);
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
        service.playerWinPoint(1, exampleMatch);
        PlayerScore score = exampleMatch.getFirstPlayerScore();
        PlayerScore score2 = exampleMatch.getSecondPlayerScore();

        assert score.getTiebreakPoints() == 1;
        assert score2.getTiebreakPoints() == 0;
    }

}