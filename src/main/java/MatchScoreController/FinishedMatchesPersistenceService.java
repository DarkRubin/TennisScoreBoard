package MatchScoreController;

import DAO.DAO;
import model.FinishedMatch;

import java.util.ArrayList;
import java.util.List;

public class FinishedMatchesPersistenceService {

    public static final int PAGE_SIZE = 10;

    private final DAO dao = new DAO();


    public void saveMatch(FinishedMatch finishedMatch) {
        dao.save(finishedMatch);
    }

    public List<FinishedMatch> findFinishedMatches() {
        return dao.findAllMatches();
    }

    public int countPages(ArrayList<FinishedMatch> finishedMatches) {
        int pages;
        return pages = finishedMatches.size() / 10;
    }

    public List<FinishedMatch> readPage(List<FinishedMatch> finishedMatches, int page) {
        int number = (page - 1) * 10;
        List<FinishedMatch> result = new ArrayList<>(PAGE_SIZE);
        for (int i = 0, matchesSize = finishedMatches.size(); i < PAGE_SIZE ; i++) {
            if (number < matchesSize) {
                result.add(finishedMatches.get(number));
                number++;
            } else return result;
        }
        return result;
    }




}
