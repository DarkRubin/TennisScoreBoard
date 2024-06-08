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
	  <a href="${pageContext.request.contextPath}/MainPage.jsp">Tennis Scoreboard</a>
	  <a href="${pageContext.request.contextPath}/FinishedMatchesView.jsp">Matches</a>
	</article>
  </section>
</div>
<div class="block">
	<h2 class="heading">New Match</h2>
	<form method="post" action="${pageContext.request.contextPath}/new-match">
	  <label for="player1">Enter First Player Name: </label><input id="player1" name="player1" type="text" required/>
	  <label for="player2">Enter Second Player Name: </label><input id="player2" name="player2" type="text" required/>
	  <input type="submit" value="Start" />
	</form>
</div>
</body>
</html>
