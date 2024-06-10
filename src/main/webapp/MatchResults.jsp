<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<%@ page import="model.MatchScore" %>
<%@ page import="model.PlayerScore" %>
<html>
<head>
  <title>Match Results</title>
  <link rel="stylesheet" href="styles.css">
</head>
<body>
<div class="head">
  <section>
    <article>
      <a href="${pageContext.request.contextPath}/MainPage.jsp">Tennis Scoreboard</a>
      <a href="${pageContext.request.contextPath}/FinishedMatchesView.jsp">Matches</a>
      <a href="${pageContext.request.contextPath}/NewMatchView.jsp">New Match</a>
    </article>
  </section>
</div>

  <%
  MatchScore matchScore = (MatchScore) request.getAttribute("matchScore");
  PlayerScore firstPlayerScore = matchScore.getFirstPlayerScore();
  PlayerScore secondPlayerScore = matchScore.getSecondPlayerScore();
  %>

<div class="board">
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
          <%=firstPlayerScore.getPointsInString()%>
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
          <%=secondPlayerScore.getPointsInString()%>
        </td>
      </tr>
      </tbody>
    </table>
    <section>
      <article>
        <h3>Match Finished!</h3>
        <h3>Winner: <%=matchScore.getWinner().getName()%></h3>
      </article>
    </section>
</div>
</body>
</html>
