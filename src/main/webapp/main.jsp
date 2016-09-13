<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Список контактов</title>
    </head>
    <body>
        <form class="center all-contacts" method="post">
            <h1 class="center">Список контактов</h1>
            <div class="clearfix">
                <a class="nav-button left" href="#">Search</a>
                <a class="nav-button left" href="#">Send e-mail</a>
                <button class="action-button right" formaction="#">ADD</button>
                <button class="action-button right" formaction="#">RMV</button>
                <button class="action-button right" formaction="#">EDT</button>
            </div>
            <div class="row">
                <div class="cell-1">Check</div>
                <div class="cell-3">Name</div>
                <div class="cell-2">Date of birth</div>
                <div class="cell-4">Address</div>
                <div class="cell-2">Company</div>
            </div>
            <c:forEach var="contact" items="${contacts}" varStatus="loop">
                <div class="row">
                    <div class="cell-1">
                        <input type="checkbox" name="check-${loop.index}">
                    </div>
                    <div class="cell-3">${contact.firstName} ${contact.lastName}</div>
                    <div class="cell-2">${contact.birthday}</div>
                    <div class="cell-4">Address</div>
                    <div class="cell-2">${contact.job}</div>
                </div>
            </c:forEach>
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
        </form>
    </body>

    <link rel="stylesheet" href="css/grid-system.css">
    <link rel="stylesheet" href="css/style.css">
</html>
