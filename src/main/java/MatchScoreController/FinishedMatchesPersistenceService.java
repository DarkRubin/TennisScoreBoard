package MatchScoreController;

import DAO.DAO;
import model.Match;

import java.util.List;

public class FinishedMatchesPersistenceService {

    public static final int PAGE_SIZE = 10;
    DAO dao = new DAO();

    public void saveMatch(Match match) {
        dao.save(match);
    }

    public Match[] readFinishedMatches(int page) {
        Match[] result = new Match[PAGE_SIZE];
        List<Match> matches = dao.findAllMatches();
        int j = 0;
        for (int i = 10 * page; i < 10 * page + 10; i++) {
            result[j++] = matches.get(i - 10);
        }
        return result;
    }
}
