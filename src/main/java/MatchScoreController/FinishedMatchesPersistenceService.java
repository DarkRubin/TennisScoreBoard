package MatchScoreController;

import DAO.DAO;
import model.FinishedMatch;
import model.MatchScore;

import java.util.ArrayList;
import java.util.List;

public class FinishedMatchesPersistenceService {

    public static final int PAGE_SIZE = 10;
    private final DAO dao = new DAO();


    public FinishedMatch finishMatch(MatchScore score) {
        return new FinishedMatch(score.getPlayer1(), score.getPlayer2(), score.getWinner());
    }

    public void saveMatch(FinishedMatch finishedMatch) {
        dao.saveMatch(finishedMatch);
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
