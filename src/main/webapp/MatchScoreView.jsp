<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="match.MatchScore" %>
<%@ page import="match.PlayerScore" %>
<html>
<head>
  <link rel="stylesheet" href="styles.css">
  <title>Match Scoreboard</title>
</head>
<body>

<jsp:include page="/Header.jsp"/>

<%
  MatchScore matchScore = (MatchScore) request.getAttribute("matchScore");
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
		  <th scope="row">
			<%=matchScore.getPlayer1().getName()%>
		  </th>
		  <td>
			<%=firstPlayerScore.getSets()%>
		  </td>
		  <td>
			<%=firstPlayerScore.getGames()%>
		  </td>
		  <td>
			<% if (matchScore.isTiebreak()) {
                out.println(firstPlayerScore.getTiebreakPoints());
            } else {
                out.println(firstPlayerScore.getPointsInString());
			}%>
		  </td>
		</tr>
		<tr>
		  <th scope="row">
			<%=matchScore.getPlayer2().getName()%>
		  </th>
		  <td>
			<%=secondPlayerScore.getSets()%>
		  </td>
		  <td>
			<%=secondPlayerScore.getGames()%>
		  </td>
		  <td>
			<%if (matchScore.isTiebreak()) {
			  out.println(secondPlayerScore.getTiebreakPoints());
			} else {
			  out.println(secondPlayerScore.getPointsInString());
			}%>
		  </td>
		</tr>
	  </tbody>
	</table>
  </section>
  <section>
	<%--@elvariable id="uuid" type="java.util.UUID"--%>
	<form method="post"
		  action="${pageContext.request.contextPath}/match-score?uuid=<%=request.getParameter("uuid")%>&id=<%=matchScore.getPlayer1().getId()%>">
	  <button class="button-wrapper">1</button>
	</form>
	<form method="post"
		  action="${pageContext.request.contextPath}/match-score?uuid=<%=request.getParameter("uuid")%>&id=<%=matchScore.getPlayer2().getId()%>">
	  <button class="button-wrapper">2</button>
	</form>
  </section>
</div>
</body>
</html>
