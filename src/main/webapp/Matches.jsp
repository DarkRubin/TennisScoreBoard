<%@ page import="MatchScoreController.FinishedMatchesPersistenceService" %>
<%@ page import="java.util.List" %>
<%@ page import="DTO.FinishedMatchDTO" %>
<%@ page import="model.FinishedMatch" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
	<link rel="stylesheet" href="styles.css">
	<title>Matches</title>
</head>

<%
  int pageNumber;
  String pageParam = request.getParameter("page");
  if (pageParam != null) {
      pageNumber = Integer.parseInt(pageParam);
  } else pageNumber = 1;
  FinishedMatchesPersistenceService service = new FinishedMatchesPersistenceService();
  List<FinishedMatch> finishedMatchList = service.findFinishedMatches();
  List<FinishedMatchDTO> finishedMatches = service.readPage(finishedMatchList, pageNumber);
%>

<body>
<div class="head">
  <section>
	<article>
	  <a href="${pageContext.request.contextPath}/main">Tennis Scoreboard</a>
	  <a href="${pageContext.request.contextPath}/finishedMatches">Matches</a>
	  <a href="${pageContext.request.contextPath}/new-match">New Match</a>
	</article>
  </section>
</div>

<div class="matches">
  <section>
	<table id="table">
	  <thead>
	  <tr>
		<th scope="col">Player1</th>
		<th scope="col">Player2</th>
		<th scope="col">Winner</th>
	  </tr>
	  </thead>
	  <tbody>

	  <%
		for (FinishedMatchDTO finishedMatch : finishedMatches) {
		  out.println("<tr>");

		  out.println("<td>");
          out.println(finishedMatch.getFirstPlayerName());
		  out.println("</td>");
		  out.println("<td>");
		  out.println(finishedMatch.getSecondPlayerName());
		  out.println("</td>");
		  out.println("<td>");
		  out.println(finishedMatch.getWinnerPlayerName());
		  out.println("</td>");

		  out.println("</tr>");
		}
	  %>

	  </tbody>
	</table>
  </section>
  <article class="pagination">
	<%
	  int pages = service.countPages(finishedMatchList);
      for (int i = 0; i < pages; i++) {
          out.println("<button class=\"page-button\">" );
          out.println((i + 1) +"</button>");
	  }
	%>
  </article>
</div>


</body>
</html>
