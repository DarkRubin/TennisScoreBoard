<%@ page import="MatchScoreController.OngoingMatchesService" %>
<%@ page import="java.util.UUID" %>
<%@ page import="model.MatchScore" %>
<%@ page import="model.PlayerScore" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
  <link rel="stylesheet" href="styles.css">
  <title>Match Scoreboard</title>
</head>

<body>
<div class="head">
  <section>
	<article>
	  <a href="${pageContext.request.contextPath}/main">Tennis Scoreboard</a>
	  <a href="${pageContext.request.contextPath}/finishedMatches">Matches</a>
	</article>
  </section>
</div>

<%
  OngoingMatchesService service = new OngoingMatchesService();
  UUID uuid = UUID.fromString(request.getParameter("uuid"));
  MatchScore matchScore = service.getMatchScore(uuid);
  PlayerScore firstPlayerScore = matchScore.getFirstPlayerScore();
  PlayerScore secondPlayerScore = matchScore.getSecondPlayerScore();
%>

<div class="board">
  <section>
	<table>
	  <thead>
		<tr>
		  <th scope="col">PLAYER</th>
		  <th scope="col">SETS</th>
		  <th scope="col">GAME</th>
		  <th scope="col">POINTS</th>
		</tr>
	  </thead>
	  <tbody>
		<tr>
		  <th scope="row">Player1</th>
		  <td><%=firstPlayerScore.getSets()%>
		  </td>
		  <td><%=firstPlayerScore.getGames()%>
		  </td>
		  <td><%=firstPlayerScore.getPoints()%>
		  </td>
		</tr>
		<tr>
		  <th scope="row">Player2</th>
		  <td><%=secondPlayerScore.getSets()%>
		  </td>
		  <td><%=secondPlayerScore.getGames()%>
		  </td>
		  <td><%=secondPlayerScore.getPoints()%>
		  </td>
		</tr>
	  </tbody>
	</table>
	<form action="<% service.firstPlayerWinPoint(uuid); %>" id="1">
	  <button class="button-wrapper" form="1">First Player Win Point</button>
	</form>

	<form action="<% service.secondPlayerWinPoint(uuid); response.sendRedirect(request.getPathInfo());%>" id="2">
	  <button class="button-wrapper" form="2"></button>
	</form>
  </section>
</div>

<a href="NewMatch.jsp">New Match</a>

<%

%>

</body>
</html>
