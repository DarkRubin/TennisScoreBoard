package MatchScoreController;

import DTO.FinishedMatchDTO;
import model.FinishedMatch;
import model.Player;
import org.junit.jupiter.api.Test;

import java.util.List;


class FinishedMatchesPersistenceServiceTest {

    private final FinishedMatchesPersistenceService matchesService = new FinishedMatchesPersistenceService();

    @Test
    void saveMatch() {
        Player player1 = new Player("Alex");
        Player player2 = new Player("Nasty");
        FinishedMatch finishedMatch = new FinishedMatch(player1, player2, player2);
        matchesService.saveMatch(finishedMatch);
    }

    @Test
    void readFinishedMatches() {
        List<FinishedMatchDTO> finishedMatches = matchesService.readPage(matchesService.findFinishedMatches(), 1);
        System.out.println(finishedMatches.toString());
    }

    @Test
    void pagination() {
        List<FinishedMatch> finishedMatches = matchesService.findFinishedMatches();
        List<FinishedMatchDTO> page = matchesService.readPage(finishedMatches, 3);
        System.out.println(page);
    }

}