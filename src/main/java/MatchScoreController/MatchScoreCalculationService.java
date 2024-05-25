package MatchScoreController;

import model.MatchScore;
import model.Player;
import model.PlayerScore;
import model.Points;

import java.util.UUID;

public class MatchScoreCalculationService extends Service {

    private MatchScore currentScore;

    public void playerWinPoint(boolean isFirst, UUID uuid) {
        currentScore = getMatchScore(uuid);
        Player winner;
        Player loser;
        if (isFirst) {
            winner = currentScore.getPlayer1();
            loser = currentScore.getPlayer2();
        } else {
            winner = currentScore.getPlayer2();
            loser = currentScore.getPlayer1();
        }

                Points winnerPoints = currentScore.getPlayerPoints(winner);
        Points loserPoints = currentScore.getPlayerPoints(loser);

        switch (winnerPoints) {
            case ZERO: winnerPoints = Points.FIFTEEN;
                break;
            case FIFTEEN: winnerPoints = Points.THIRTY;
                break;
            case THIRTY: winnerPoints = Points.FORTY;
                break;
            case FORTY:
                if (loserPoints != Points.FORTY) {
                    gameWin(winner,loser);
                } else {
                    winnerPoints = Points.EXACTLY;
                    loserPoints = Points.EXACTLY;
                }
                break;
            case EXACTLY:
                winnerPoints = Points.MORE;
                loserPoints = Points.LESS;
                break;
            case LESS:
                winnerPoints = Points.EXACTLY;
                loserPoints = Points.EXACTLY;
                break;
            case MORE: gameWin(winner, loser);
        }

        currentScore.setPlayerPoints(winner, winnerPoints);
        currentScore.setPlayerPoints(loser, loserPoints);
    }


    private void gameWin(Player winner, Player loser) {
        PlayerScore winnerScore = currentScore.getThisPlayerScore(winner);
        PlayerScore loserScore = currentScore.getThisPlayerScore(loser);
        winnerScore.setPoints(Points.ZERO);
        winnerScore.setGames(winnerScore.getGames() + 1);
        if (winnerScore.getGames() == 6) {
            if (loserScore.getGames() == 6) {
                tiebreak();
            }
            setWin(winnerScore, winner);
        }
    }

    private void tiebreak() {
    }


    private void setWin(PlayerScore winnerScore, Player winner) {
        winnerScore.setSets(winnerScore.getSets() + 1);
        if (winnerScore.getSets() == 2) {
            currentScore.setFinished(isMatchFinished());
            currentScore.setWinner(winner);
        }
    }

    private boolean isMatchFinished() {
        return currentScore.getFirstPlayerScore().getSets() == 2 && currentScore.getSecondPlayerScore().getSets() == 2;
    }

}
