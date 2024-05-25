
<%@ page import="java.util.UUID" %>
<%@ page import="MatchScoreController.OngoingMatchesService" %><%--
  Created by IntelliJ IDEA.
  User: Vadim
  Date: 11.05.2024
  Time: 17:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<link rel="stylesheet" href="styles.css">
	<title>New Match</title>
</head>
<body>
<div class="block">

	<h2 class="heading">New Match</h2>

	<form method="post" action="${pageContext.request.contextPath}/new-match/*">
		<label for="player1"> Enter First Player Name
			<input id="player1" name="player1" type="text" required>
		</label>
		<label for="player2"> Enter Second Player Name
			<input id="player2" name="player1" type="text" required>
		</label>
			<input type="submit" value="Start" />
	</form>

</div>



</body>
</html>
