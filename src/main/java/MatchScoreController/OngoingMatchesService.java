package MatchScoreController;

import DAO.DAO;
import model.MatchScore;
import model.Player;

public class OngoingMatchesService {

    private final DAO dao = new DAO();
    private MatchScore matchScore;

    public void startNewMatch(Player one, Player two) {
        matchScore = new MatchScore(one, two);


    }




}
