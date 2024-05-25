package servlets;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "MatchScore", value = "/match-score/*")
public class MatchScore extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String uuid = request.getParameter("uuid");


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


    }
}
