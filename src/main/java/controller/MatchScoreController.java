package controller;

import java.io.*;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.MatchScore;
import service.MatchScoreCalculationService;
import service.OngoingMatchesService;

@WebServlet(name = "MatchScoreController", value = "/match-score")
public class MatchScoreController extends HttpServlet {

    private final OngoingMatchesService service = new OngoingMatchesService();
    private final MatchScoreCalculationService calculationService = new MatchScoreCalculationService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uuid1 = request.getParameter("uuid");
        System.out.println(uuid1);
        UUID uuid = UUID.fromString(uuid1);
        String idParam = request.getParameter("id");
        MatchScore matchScore = service.getMatchScore(uuid);
        request.setAttribute("matchScore", matchScore);
        if (idParam != null) {
            long id = Long.parseLong(idParam);
            calculationService.playerWinPoint(id, matchScore);
            if (matchScore.isFinished()) {
                request.getRequestDispatcher(String.format("/match-score/finish?uuid=%s", uuid)).forward(request, response);
            }
        }
        request.getRequestDispatcher(String.format("/match-score/view?uuid=%s", uuid)).forward(request, response);
    }

}
