package MatchScoreController;

import DTO.FinishedMatchDTO;
import model.FinishedMatch;
import org.junit.jupiter.api.Test;

import java.util.List;


class FinishedMatchesPersistenceServiceTest {

    private final FinishedMatchesPersistenceService matchesService = new FinishedMatchesPersistenceService();

    @Test
    void saveMatch() {

    }

    @Test
    void readFinishedMatches() {
        List<FinishedMatchDTO> finishedMatches = matchesService.readPage(matchesService.findFinishedMatches(), 1);
        System.out.println(finishedMatches.toString());
    }
}