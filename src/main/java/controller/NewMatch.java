package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import match.MatchScore;
import service.OngoingMatchesService;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;

@WebServlet(name = "NewMatch", value = "/new-match")
public class NewMatch extends HttpServlet {

    final OngoingMatchesService matchesService = new OngoingMatchesService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String player1 = request.getParameter("player1");
        String player2 = request.getParameter("player2");
        if (player1.equals(player2)) {
            getServletContext().getRequestDispatcher(request.getContextPath()
                            + "/NewMatchView.jsp?players_equals_error=true")
                            .forward(request, response);
        } else {
            MatchScore matchScore = matchesService.startNewMatch(player1, player2);
            response.sendRedirect(request.getContextPath() + "/match-score?uuid=" + matchScore.getUuid());
        }
    }

}