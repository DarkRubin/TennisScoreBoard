package MatchScoreController;

import model.MatchScore;
import model.Player;
import model.PlayerScore;
import model.Points;

import java.util.UUID;

public class MatchScoreCalculationService extends Service {

    private MatchScore currentScore;
    private boolean tiebreak;

    public void playerWinPoint(boolean isFirst, UUID uuid) {
        if (tiebreak) {
            return;
        }
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
            case ZERO -> winnerPoints = Points.FIFTEEN;
            case FIFTEEN -> winnerPoints = Points.THIRTY;
            case THIRTY -> {
                if (loserPoints != Points.FORTY) {
                    winnerPoints = Points.FORTY;
                } else {
                    winnerPoints = Points.EXACTLY;
                    loserPoints = Points.EXACTLY;
                }
            }
            case FORTY, MORE -> {
                gameWin(winner, loser); return;
            }
            case EXACTLY -> {
                winnerPoints = Points.MORE;
                loserPoints = Points.LESS;
            }
            case LESS -> {
                winnerPoints = Points.EXACTLY;
                loserPoints = Points.EXACTLY;
            }
        }
        if (!currentScore.isFinished()) {
            currentScore.setPlayerPoints(winner, winnerPoints);
            currentScore.setPlayerPoints(loser, loserPoints);
        }
    }


    private void gameWin(Player winner, Player loser) {
        PlayerScore winnerScore = currentScore.getThisPlayerScore(winner);
        PlayerScore loserScore = currentScore.getThisPlayerScore(loser);
        winnerScore.setPoints(Points.ZERO);
        loserScore.setPoints(Points.ZERO);
        winnerScore.setGames(winnerScore.getGames() + 1);
        if (winnerScore.getGames() == 6) {
            if (loserScore.getGames() == 6) {
                tiebreak = true;
            } else {
                setWin(winnerScore, winner);
            }
        }
    }


    private void setWin(PlayerScore winnerScore, Player winner) {
        winnerScore.setSets(winnerScore.getSets() + 1);
        winnerScore.setGames(0);
        if (winnerScore.getSets() == 2) {
            currentScore.setWinner(winner);
            matchFinished();
        }
    }

    private void matchFinished() {
        OngoingMatchesService service = new OngoingMatchesService();
        service.matchEnd(currentScore.getUuid());
    }

}
