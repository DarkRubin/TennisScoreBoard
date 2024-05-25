<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

  <head>
	  <link rel="stylesheet" href="styles.css">
	  <title>Tennis Scoreboard</title>
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
			<td>0</td>
			<td>0</td>
			<td>0</td>
		  </tr>
		  <tr>
			<th scope="row">Player2</th>
			<td>0</td>
			<td>0</td>
			<td>0</td>
		  </tr>

		  </tbody>
		</table>
	  </section>
	</div>

  <a href="NewMatch.jsp">New Match</a>

  </body>
</html>