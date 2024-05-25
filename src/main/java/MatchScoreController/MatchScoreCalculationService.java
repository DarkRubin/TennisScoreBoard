package MatchScoreController;

import model.MatchScore;

public class MatchScoreCalculationService {

    private MatchScore matchScore;

    public void newMatch(MatchScore matchScore) {
        this.matchScore = matchScore;
    }

    public MatchScore firstPlayerWinPoint() {
        playerWinPoint(true);
        return matchScore;
    }

    public MatchScore secondPlayerWinPoint() {
        playerWinPoint(false);
        return matchScore;
    }

    public void playerWinPoint(boolean isFirst) {
        int points;
        points = matchScore.getPlayerPoints(isFirst);

        switch (points) {
            case 0: points += 15;
            case 15: points += 15;
            case 30: points += 10;
            case 40: gameIsWin(isFirst);
        }

        matchScore.setPlayerPoints(isFirst, points);
    }

    private void gameIsWin(boolean isFirst) {


    }

    private void gameWin() {

    }

}
