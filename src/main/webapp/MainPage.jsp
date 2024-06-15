<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
  <head>
	<base>
	<link rel="stylesheet" href="styles.css">
	<title>Tennis Scoreboard</title>
  </head>
  <body>
	<jsp:include page="Header.jsp"/>
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
			<th scope="row">Player1</th><td>0</td><td>0</td><td>0</td>
		  </tr>
		  <tr>
			<th scope="row">Player2</th><td>0</td><td>0</td><td>0</td>
		  </tr>
		  </tbody>
		</table>
	  </section>
	</div>
  </body>
</html>