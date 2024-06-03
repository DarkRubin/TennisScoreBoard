package MatchScoreController;

import model.MatchScore;
import model.Player;
import model.PlayerScore;
import model.Points;

import java.util.UUID;

import static model.Points.*;

public class MatchScoreCalculationService extends Service {

    public static final int MIN_GAME_COUNT_FOR_WIN = 6;
    public static final int MIN_TIEBREAK_POINTS_FOR_WIN = 7;
    public static final int SETS_COUNT_FOR_WIN = 2;

    private MatchScore currentScore;
    private Player winner;
    private Player loser;
    private PlayerScore winnerScore;
    private PlayerScore loserScore;

    public void playerWinPoint(boolean isFirst, UUID uuid) {
        currentScore = getMatchScore(uuid);
        getPlayers(isFirst);

        if (currentScore.isTiebreak()) {
            tiebreakPointsAdding();
        } else {
            defaultPointsAdding(currentScore.getPlayerPoints(winner),
                    currentScore.getPlayerPoints(loser));
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
                return;
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
            currentScore.setPlayerPoints(winner, winnerPoints);
            currentScore.setPlayerPoints(loser, loserPoints);
        }
    }

    private void getPlayers(boolean isFirst) {
        if (isFirst) {
            winner = currentScore.getPlayer1();
            loser = currentScore.getPlayer2();
            winnerScore = currentScore.getFirstPlayerScore();
            loserScore = currentScore.getSecondPlayerScore();
        } else {
            winner = currentScore.getPlayer2();
            loser = currentScore.getPlayer1();
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
        }
    }

    private void gameWin() {
        winnerScore.setPoints(ZERO);
        loserScore.setPoints(ZERO);
        winnerScore.setTiebreakPoints(0);
        loserScore.setTiebreakPoints(0);
        int winnerGames = winnerScore.getGames() + 1;
        int loserGames = loserScore.getGames();
        winnerScore.setGames(winnerGames);
        if (winnerGames == loserGames && winnerGames >= MIN_GAME_COUNT_FOR_WIN) {
            currentScore.setTiebreak(true);
        }
        if (winnerGames >= MIN_GAME_COUNT_FOR_WIN && winnerGames > loserGames + 1) {
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
        service.matchEnd(currentScore.getUuid());
    }
}
