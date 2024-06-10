package controller;

import exception.PlayerEqualsException;
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
            request.setAttribute("players_equals_error", new PlayerEqualsException());
            response.sendRedirect( request.getContextPath() + "/NewMatchView.jsp?players_equals_error=true");
        } else {
            MatchScore matchScore = matchesService.startNewMatch(player1, player2);
            request.setAttribute("matchScore", matchScore);
            response.sendRedirect(request.getContextPath() + "/match-score?uuid=%s" + matchScore.getUuid());
        }
    }

}