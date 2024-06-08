package controller;

import model.MatchScore;
import service.OngoingMatchesService;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;

@WebServlet(name = "NewMatch", value = "/new-match")
public class NewMatch extends HttpServlet {

    final OngoingMatchesService matchesService = new OngoingMatchesService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String player1 = request.getParameter("player1");
        String player2 = request.getParameter("player2");
        if (player1.equals(player2)) {
            response.sendRedirect("/NewMatchView.jsp");
        }

        MatchScore matchScore = matchesService.startNewMatch(player1, player2);
        response.sendRedirect(String.format("/match-score/view?uuid=%s", matchScore.getUuid()));
    }

}