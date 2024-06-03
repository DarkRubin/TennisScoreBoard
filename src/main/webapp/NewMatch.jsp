<%@ page import="java.util.UUID" %>
<%@ page import="MatchScoreController.OngoingMatchesService" %><%--
  Created by IntelliJ IDEA.
  User: Vadim
  Date: 11.05.2024
  Time: 17:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
	<link rel="stylesheet" href="styles.css">
	<title>New Match</title>
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

<div class="block">

	<h2 class="heading">New Match</h2>

	<form method="post">
	  <label for="player1"> Enter First Player Name
		<input id="player1" name="player1" type="text" required>
	  </label>
	  <label for="player2"> Enter Second Player Name
		<input id="player2" name="player2" type="text" required>
	  </label>
	  <input type="submit" value="Start" />
	</form>

  <%
	String player1 = request.getParameter("player1");
	if (player1 == null) {
		return;
	}
	String player2 = request.getParameter("player2");
	if (player2 == null) {
	  return;
	}
	OngoingMatchesService matchesService = new OngoingMatchesService();
	UUID uuid = matchesService.startNewMatch(player1, player2);
	response.sendRedirect(String.format("/TennisScoreBoard/match-score?uuid=%s", uuid));

  %>

</div>
</body>
</html>
