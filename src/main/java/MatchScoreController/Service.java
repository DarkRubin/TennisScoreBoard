package MatchScoreController;

import DAO.DAO;
import model.MatchScore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class Service {

    public Map<UUID, MatchScore> ongoingMatches = new HashMap<>();
    protected final DAO dao = new DAO();

    public MatchScore getMatchScore(UUID uuid) {
        return ongoingMatches.get(uuid);
    }
}
