package servlets;

import MatchScoreController.OngoingMatchesService;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "newMatch", value = "/new-match/*")
public class NewMatch extends HttpServlet {

    OngoingMatchesService matchesService = new OngoingMatchesService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String player1 = req.getParameter("player1");
        String player2 = req.getParameter("player2");


        UUID uuid = matchesService.startNewMatch(player1, player2);
        resp.sendRedirect(String.format("/TennisScoreBoard/match-score?uuid=%s", uuid));
    }
}