package service;

import DAO.DAO;
import model.FinishedMatch;
import match.MatchScore;
import model.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class OngoingMatchesService {

    private final DAO dao = new DAO();
    private final FinishedMatchesPersistenceService finishedMatchesService = new FinishedMatchesPersistenceService();
    private static final Map<UUID, MatchScore> ONGOING_MATCHES = new HashMap<>();

    public MatchScore startNewMatch(String one, String two) {
        Player player1 = findOrSavePlayer(one);
        Player player2 = findOrSavePlayer(two);

        MatchScore matchScore = new MatchScore(player1, player2);

        ONGOING_MATCHES.put(matchScore.getUuid(), matchScore);
        return matchScore;
    }

    public MatchScore getMatchScore(UUID uuid) {
        return ONGOING_MATCHES.get(uuid);
    }

    protected void matchEnd(MatchScore matchScore) {
        FinishedMatch finishedMatch = finishedMatchesService.finishMatch(matchScore);
        finishedMatchesService.saveMatch(finishedMatch);
    }

    private Player findOrSavePlayer(String name) {
        return dao.findPlayer(name).orElse(dao.savePlayer(name));
    }

}
