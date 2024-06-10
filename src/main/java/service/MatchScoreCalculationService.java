package service;

import match.MatchScore;
import model.Player;
import match.PlayerScore;
import match.Points;

import static match.Points.*;

public class MatchScoreCalculationService {

    public static final int MIN_GAME_COUNT_FOR_WIN = 6;
    public static final int MIN_TIEBREAK_POINTS_FOR_WIN = 7;
    public static final int SETS_COUNT_FOR_WIN = 2;

    private MatchScore currentScore;
    private Player winner;
    private PlayerScore winnerScore;
    private PlayerScore loserScore;

    public void playerWinPoint(long id, MatchScore currentScore) {
        this.currentScore = currentScore;
        getPlayers(id);

        if (currentScore.isTiebreak()) {
            tiebreakPointsAdding();
        } else {
            defaultPointsAdding(winnerScore.getPoints(), loserScore.getPoints());
        }
    }

    private void defaultPointsAdding(Points winnerPoints, Points loserPoints) {
        switch (winnerPoints) {
            case ZERO -> winnerPoints = FIFTEEN;
            case FIFTEEN -> winnerPoints = THIRTY;
            case THIRTY -> {
                if (loserPoints != FORTY) {
                    winnerPoints = FORTY;
                } else {
                    winnerPoints = EXACTLY;
                    loserPoints = EXACTLY;
                }
            }
            case FORTY, MORE -> {
                gameWin();
                winnerPoints = ZERO;
                loserPoints = ZERO;
            }
            case EXACTLY -> {
                winnerPoints = MORE;
                loserPoints = LESS;
            }
            case LESS -> {
                winnerPoints = EXACTLY;
                loserPoints = EXACTLY;
            }
        }
        if (!currentScore.isFinished()) {
            winnerScore.setPoints(winnerPoints);
            loserScore.setPoints(loserPoints);
        }
    }

    private void getPlayers(long id) {
        if (id == 1) {
            winner = currentScore.getPlayer1();
            winnerScore = currentScore.getFirstPlayerScore();
            loserScore = currentScore.getSecondPlayerScore();
        } else {
            winner = currentScore.getPlayer2();
            winnerScore = currentScore.getSecondPlayerScore();
            loserScore = currentScore.getFirstPlayerScore();
        }
    }

    private void tiebreakPointsAdding() {
        int winnerTiebreakPoints = winnerScore.getTiebreakPoints() + 1;
        int loserTiebreakPoints = loserScore.getTiebreakPoints();

        winnerScore.setTiebreakPoints(winnerTiebreakPoints);

        if (winnerTiebreakPoints > loserTiebreakPoints + 1 && winnerTiebreakPoints >= MIN_TIEBREAK_POINTS_FOR_WIN) {
            gameWin();
            setWin();
        }
    }

    private void gameWin() {
        winnerScore.setTiebreakPoints(0);
        loserScore.setTiebreakPoints(0);
        int winnerGames = winnerScore.getGames() + 1;
        int loserGames = loserScore.getGames();
        winnerScore.setGames(winnerGames);
        if (winnerGames == loserGames && winnerGames >= MIN_GAME_COUNT_FOR_WIN) {
            currentScore.setTiebreak(true);
        }
        if (winnerGames >= MIN_GAME_COUNT_FOR_WIN && winnerGames > loserGames + 1 && !currentScore.isTiebreak()) {
            setWin();
        }
    }

    private void setWin() {
        winnerScore.setSets(winnerScore.getSets() + 1);
        currentScore.setTiebreak(false);
        winnerScore.setGames(0);
        loserScore.setGames(0);
        if (winnerScore.getSets() == SETS_COUNT_FOR_WIN) {
            currentScore.setFinished(true);
            currentScore.setWinner(winner);
            matchFinished();
        }
    }

    private void matchFinished() {
        OngoingMatchesService service = new OngoingMatchesService();
        service.matchEnd(currentScore);
    }
}
