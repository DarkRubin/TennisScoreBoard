<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <link rel="stylesheet" href="styles.css">
  <title>Matches</title>
</head>
<body>
<div class="head">
  <section>
	<article>
	  <a href="<c:url value="/MainPage.jsp"/>">Tennis Scoreboard</a>
	  <a href="<c:url value="/FinishedMatchesView.jsp"/>">Matches</a>
	  <a href="<c:url value="/NewMatchView.jsp"/>">New Match</a>
	</article>
  </section>
</div>

<%
  String url = request.getContextPath() + "/matches-controller";

  if (request.getAttribute("matchesToPrint") == null) {
	response.sendRedirect(url);
  }
%>

<div class="matches">
  <section>
	<form action="<%=url%>" method="get">
	  <label for="filter_by_player_name">
		<input type="text" name="filter_by_player_name" placeholder="Enter player name">
	  </label>
	  <button type="submit">Search</button>
	</form>
	<table id="table">
	  <thead>
	  <tr>
		<th scope="col">Player1</th>
		<th scope="col">Player2</th>
		<th scope="col">Winner</th>
	  </tr>
	  </thead>
	  <tbody>
	  <%--@elvariable id="matchesToPrint" type="java.util.List"--%>
	  <c:forEach items="${matchesToPrint}" var="match">
		<tr>
		  <td>${match.firstPlayerName}</td>
		  <td>${match.secondPlayerName}</td>
		  <td>${match.winnerName}</td>
		</tr>
	  </c:forEach>
	  </tbody>
	</table>
  </section>
  <article class="pagination">
	<%--@elvariable id="pages" type="java.util.List"--%>
	<%
	  String attribute = (String) request.getAttribute("filter_by_player_name");
	  if (attribute != null && !attribute.isEmpty()) {
          request.setAttribute("url", url + "?filter_by_player_name=" + attribute);
	  }
	%>
	<form action="${url}" method="get" id="page-form">
	  <input type="hidden" name="filter_by_player_name" form="page-form" id="filter_by_player_name" value="${pageContext.request.getParameter("filter_by_player_name")}">
	  <c:forEach items="${pages}" var="page">
		<input type="submit" name="pageNumber" form="page-form" id="pageNumber" value="${page}">
	  </c:forEach>
	</form>
  </article>
</div>


</body>
</html>
