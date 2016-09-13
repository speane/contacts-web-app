<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Список контакто</title>
</head>
<body>

<h2>Results</h2>

<c:if test="${pagination.activePage > pagination.startPage}">
    <a href="#"><</a>
</c:if>
<c:forEach var="i" begin="${pagination.startPage}" end="${pagination.activePage - 1}">
    <a href="#">${i}</a>
</c:forEach>
<c:out value="A${pagination.activePage}A" />
<c:forEach var="i" begin="${pagination.activePage + 1}" end="${pagination.endPage}">
    <a href="#">${i}</a>
</c:forEach>
<c:if test="${pagination.activePage < pagination.endPage}">
    <a href="#">></a>
</c:if>
</body>
</html>