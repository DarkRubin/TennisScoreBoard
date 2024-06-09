package controller;

import DTO.FinishedMatchDTO;
import exception.PlayerNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import service.FinishedMatchesPersistenceService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Matches", value = "/matches-controller")
public class Matches extends HttpServlet {

    private final FinishedMatchesPersistenceService matchesService = new FinishedMatchesPersistenceService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<FinishedMatchDTO> matches;
        String pageParam = request.getParameter("pageNumber");
        String playerNameFilter = request.getParameter("filter_by_player_name");
        if (playerNameFilter != null && !playerNameFilter.isEmpty()) {
            try {
                matches = matchesService.findPlayerMatches(playerNameFilter);
            } catch (PlayerNotFoundException e) {
                matches = new ArrayList<>();
            }
        } else {
            matches = matchesService.matchesToDTO(matchesService.findFinishedMatches());
        }
        int pages = matchesService.countPages(matches);
        int page = pageParam == null || pageParam.isEmpty() ? 1 : Integer.parseInt(pageParam);
        request.setAttribute("matchesToPrint", matchesService.readPage(matches, page));
        request.setAttribute("pages", pagesInList(pages));
        getServletContext().getRequestDispatcher("/FinishedMatchesView").forward(request, response);
    }

    private List<Integer> pagesInList(int pagesCount) {
        List<Integer> pages = new ArrayList<>();
        for (int i = 1; i < pagesCount; i++) {
            pages.add(i);
        }
        return pages;
    }


}
