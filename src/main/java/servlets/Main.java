package servlets;

import MatchScoreController.OngoingMatchesService;
import model.MatchScore;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();
        UUID uuid = ongoingMatchesService.startNewMatch("Anton", "Alex");
        MatchScore matchScore = ongoingMatchesService.ongoingMatches.get(uuid);
        System.out.println(matchScore);
    }
}
