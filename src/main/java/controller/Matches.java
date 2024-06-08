package controller;

import DTO.FinishedMatchDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import service.FinishedMatchesPersistenceService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Matches", value = "/Matches")
public class Matches extends HttpServlet {

    private final FinishedMatchesPersistenceService matchesService = new FinishedMatchesPersistenceService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<FinishedMatchDTO> matches;
        String pageParam = request.getParameter("pageNumber");
        String filterByPlayerName = request.getParameter("filter_by_player_name");
        if (filterByPlayerName != null) {
            matches = matchesService.findPlayerMatches(filterByPlayerName);
        } else {
            matches = matchesService.matchesToDTO(matchesService.findFinishedMatches());
        }
        int page = pageParam == null || pageParam.isEmpty() ? 1 : Integer.parseInt(pageParam);

        request.setAttribute("matchesToPrint", matchesService.readPage(matches, page));
        int pages = matchesService.countPages(matches);
        request.setAttribute("pages", pagesInList(pages));
        request.getRequestDispatcher(String.format("/finishedMatches?page=%s", page)).forward(request, response);
    }

    private List<Integer> pagesInList(int pagesCount) {
        List<Integer> pages = new ArrayList<>();
        for (int i = 1; i < pagesCount; i++) {
            pages.add(i);
        }
        return pages;
    }


}
