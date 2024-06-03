package MatchScoreController;

import model.MatchScore;
import model.Player;
import model.PlayerScore;
import model.Points;

import java.util.UUID;

import static model.Points.*;

public class MatchScoreCalculationService extends Service {

    private MatchScore currentScore;
    private boolean tiebreak;
    private Player winner;
    private Player loser;
    private PlayerScore winnerScore;
    private PlayerScore loserScore;

    public void playerWinPoint(boolean isFirst, UUID uuid) {
        currentScore = getMatchScore(uuid);
        getPlayers(isFirst);

        if (tiebreak) {
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
        int winnerTiebreakPoints = winnerScore.getTiebreakPoints();
        int loserTiebreakPoints = loserScore.getTiebreakPoints();

        winnerScore.setTiebreakPoints(winnerTiebreakPoints++);

        if (winnerTiebreakPoints > loserTiebreakPoints + 1 && winnerTiebreakPoints >= 7) {
            gameWin();
        }
    }

    private void gameWin() {
        winnerScore.setPoints(ZERO);
        loserScore.setPoints(ZERO);
        int winnerGames = winnerScore.getGames();
        int loserGames = loserScore.getGames();
        winnerScore.setGames(winnerGames + 1);
        if (winnerGames == 6 && loserGames == 6) {
            tiebreak = true;
        }
        if (winnerGames >= 6 && winnerGames > loserGames + 1) {
            setWin();
        }
    }

    private void setWin() {
        winnerScore.setSets(winnerScore.getSets() + 1);
        winnerScore.setGames(0);
        if (winnerScore.getSets() == 2) {
            currentScore.setWinner(winner);
            tiebreak = false;
            matchFinished();
        }
    }

    private void matchFinished() {
        OngoingMatchesService service = new OngoingMatchesService();
        service.matchEnd(currentScore.getUuid());
    }

}
