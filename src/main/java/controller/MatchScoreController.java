package controller;

import java.io.IOException;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.MatchScore;
import service.MatchScoreCalculationService;
import service.OngoingMatchesService;

@WebServlet(name = "MatchScoreController", value = "/match-score")
public class MatchScoreController extends HttpServlet {

    private final OngoingMatchesService service = new OngoingMatchesService();
    private final MatchScoreCalculationService calculationService = new MatchScoreCalculationService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UUID uuid = UUID.fromString(request.getParameter("uuid"));
        String idParam = request.getParameter("id");
        MatchScore matchScore = service.getMatchScore(uuid);
        request.setAttribute("matchScore", matchScore);
        long id = Long.parseLong(idParam);
        calculationService.playerWinPoint(id, matchScore);

        if (matchScore.isFinished()) {
            getServletContext().getRequestDispatcher("/match-score/finish").forward(request, response);
        }
        getServletContext().getRequestDispatcher("/match-score/view").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UUID uuid = UUID.fromString(request.getParameter("uuid"));
        MatchScore matchScore = service.getMatchScore(uuid);
        request.setAttribute("matchScore", matchScore);
        getServletContext().getRequestDispatcher("/match-score/view").forward(request, response);
    }
}
