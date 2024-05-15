<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="test.css">
	<title>Tennis Scoreboard</title>
</head>
<body>
<div class="head">
	<section>
		<article>
			<a href="${pageContext.request.contextPath}/main">Tennis Scoreboard</a>
			<a href="${pageContext.request.contextPath}/matches">Matches</a>
		</article>
	</section>
	<hr>
</div>

<div class="board">
	<section>
		<article>
			<p>PREVIOUS SETS</p><p>PLAYER</p><p>SETS</p><p>GAME</p><p>POINTS</p>
		</article>
	</section>
</div>
<br/>
<a href="NewMatch.jsp">New Match</a>
</body>
</html>