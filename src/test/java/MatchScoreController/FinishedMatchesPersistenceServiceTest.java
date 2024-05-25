package MatchScoreController;

import org.junit.jupiter.api.Test;

import java.util.Arrays;


class FinishedMatchesPersistenceServiceTest {

    private final FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();

    @Test
    void saveMatch() {

    }

    @Test
    void readFinishedMatches() {
        System.out.println(finishedMatchesPersistenceService.findFinishedMatches().toString());
    }
}