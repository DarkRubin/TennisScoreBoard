<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
	<link rel="stylesheet" href="styles.css">
	<title>New Match</title>
</head>
<body>

<jsp:include page="Header.jsp"/>
<div class="block">
	<h2 class="heading">New Match</h2>
	<form method="post" action="${pageContext.request.contextPath}/new-match" class="players-form">
	  <label class="labels" for="player1">Enter First Player Name: </label>
	  	<input id="player1" name="player1" type="text" required/>
	  <label class="labels" for="player2">Enter Second Player Name: </label>
	  	<input id="player2" name="player2" type="text" required/>
	  <input type="submit" value="Start"/>
	</form>
</div>
</body>
</html>
