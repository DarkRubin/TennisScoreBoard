<%@ page import="java.util.UUID" %>
<%@ page import="model.MatchScore" %>
<%@ page import="model.PlayerScore" %>
<%@ page import="MatchScoreController.Service" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
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
  UUID uuid = UUID.fromString(request.getParameter("uuid"));
  MatchScore matchScore = Service.getMatchScore(uuid);
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
		  <th scope="row"><%=matchScore.getPlayer1().getName()%></th>
		  <td><%=firstPlayerScore.getSets()%>
		  </td>
		  <td><%=firstPlayerScore.getGames()%>
		  </td>
		  <td><%=firstPlayerScore.getPointsInString()%>
		  </td>
		</tr>
		<tr>
		  <th scope="row"><%=matchScore.getPlayer2().getName()%></th>
		  <td><%=secondPlayerScore.getSets()%>
		  </td>
		  <td><%=secondPlayerScore.getGames()%>
		  </td>
		  <td><%=secondPlayerScore.getPointsInString()%>
		  </td>
		</tr>
	  </tbody>
	</table>
	<section>
	  <article>

		<form method="post" id="1"
			  action="<%=String.format("/TennisScoreBoard/match-score/calculation?uuid=%s&id=%s", uuid, matchScore.getPlayer1().getId()) %>">
		  <button class="button-wrapper" form="1" type="submit">First Player Win Point</button>
		  </form>

		<form method="post" id="2"
			  action="<%=String.format("/TennisScoreBoard/match-score/calculation?uuid=%s&id=%s", uuid, matchScore.getPlayer2().getId())%>">
		  <button class="button-wrapper" form="2" type="submit"></button>
		</form>

	  </article>
	</section>

  </section>
</div>

<a href="NewMatch.jsp">New Match</a>

</body>
</html>
