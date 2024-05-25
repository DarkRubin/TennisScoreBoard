package MatchScoreController;

import DAO.DAO;
import model.MatchScore;
import model.Player;
import model.PlayerScore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class OngoingMatchesService {

    public Map<UUID, MatchScore> ongoingMatches = new HashMap<>();

    private final DAO dao = new DAO();
    private final MatchScoreCalculationService service = new MatchScoreCalculationService();

    public UUID startNewMatch(String one, String two) {

        Player player1 = dao.findOrSavePlayer(one);
        Player player2 = dao.findOrSavePlayer(two);

        MatchScore matchScore = new MatchScore(player1, player2);

        service.newMatch(matchScore);
        ongoingMatches.put(matchScore.getUuid(), matchScore);
        return matchScore.getUuid();
    }







}
