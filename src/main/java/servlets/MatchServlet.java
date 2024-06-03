package servlets;

import MatchScoreController.OngoingMatchesService;
import MatchScoreController.Service;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.MatchScore;

import java.io.IOException;
import java.util.UUID;

@WebServlet(urlPatterns = "/match-score/calculation")
public class MatchServlet extends HttpServlet {

    private final OngoingMatchesService service = new OngoingMatchesService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));
        long id = Long.parseLong(req.getParameter("id"));
        MatchScore matchScore = Service.getMatchScore(uuid);

        if (!matchScore.isFinished()) {
            if (matchScore.getPlayer1().getId() == id) {
                service.firstPlayerWinPoint(uuid);
            } else {
                service.secondPlayerWinPoint(uuid);
            }
        }

        resp.sendRedirect(String.format("/TennisScoreBoard/match-score?uuid=%s", uuid));
    }
}
