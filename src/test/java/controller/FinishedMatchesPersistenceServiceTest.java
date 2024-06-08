package controller;

import DTO.FinishedMatchDTO;
import model.FinishedMatch;
import model.Player;
import org.junit.jupiter.api.Test;
import service.FinishedMatchesPersistenceService;

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
        List<FinishedMatchDTO> matches = matchesService.matchesToDTO(matchesService.findFinishedMatches());
        List<FinishedMatchDTO> finishedMatches = matchesService.readPage(matches, 1);
        System.out.println(finishedMatches.toString());
    }

    @Test
    void pagination() {
        List<FinishedMatchDTO> finishedMatches = matchesService.matchesToDTO(matchesService.findFinishedMatches());
        List<FinishedMatchDTO> page = matchesService.readPage(finishedMatches, 3);
        System.out.println(page);
    }

}