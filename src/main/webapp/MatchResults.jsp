<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<%@ page import="match.MatchScore" %>
<%@ page import="match.PlayerScore" %>
<html>
<head>
  <title>Match Results</title>
  <link rel="stylesheet" href="styles.css">
</head>
<body>
<jsp:include page="/Header.jsp" flush="true"/>
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
      <article class="winner-show">
        <h3>Match Finished!</h3>
        <h3>Winner: <%=matchScore.getWinner().getName()%></h3>
      </article>
    </section>
</div>
</body>
</html>
