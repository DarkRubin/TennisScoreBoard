package MatchScoreController;

import DAO.DAO;
import model.MatchScore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class Service {

    public static Map<UUID, MatchScore> ongoingMatches = new HashMap<>();
    protected final DAO dao = new DAO();

    public static MatchScore getMatchScore(UUID uuid) {
        return ongoingMatches.get(uuid);
    }
}
