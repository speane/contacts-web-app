<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Список контактов</title>
    </head>
    <body>
        <form class="centered contact" method="post">
            <h1 class="centered">Список контактов</h1>
            <div class="clearfix">
                <a class="nav-button left" href="#">Search</a>
                <a class="nav-button left" href="#">Send e-mail</a>
                <button class="action-button right" formaction="#">ADD</button>
                <button class="action-button right" formaction="#">RMV</button>
                <button class="action-button right" formaction="#">EDT</button>
            </div>
            <div class="row">
                <div class="cell-1">Выбор</div>
                <div class="cell-3">Полное имя</div>
                <div class="cell-2">Дата рождения</div>
                <div class="cell-4">Адрес</div>
                <div class="cell-2">Место работы</div>
            </div>
            <c:forEach var="contact" items="${contacts}" varStatus="loop">
                <div class="row">
                    <div class="cell-1">
                        <input type="checkbox" name="check-${loop.index}">
                    </div>
                    <div class="cell-3"><a href="/edit-contact?id=${contact.id}">${contact.firstName} ${contact.lastName}</a></div>
                    <div class="cell-2">${contact.birthday}</div>
                    <c:set var="address" value="${contact.address}" />
                    <div class="cell-4">
                        <c:if test="${address.city != null}">г.</c:if>${address.city}
                        <c:if test="${address.street != null}">ул.</c:if>${address.street}
                        <c:if test="${address.house != null}">д.</c:if>${address.house}
                        <c:if test="${address.flat != null}">кв.</c:if>${address.flat}
                    </div>
                    <div class="cell-2">${contact.job}</div>
                </div>
            </c:forEach>
            <div class="center">
                <ul class="pagination">
                    <c:if test="${pagination.activePage > pagination.startPage}">
                        <li><a href="/contact-list?page=${pagination.activePage - 1}">Назад</a></li>
                    </c:if>
                    <c:forEach var="i" begin="${pagination.startPage}" end="${pagination.activePage - 1}">
                        <li><a href="/contact-list?page=${i}">${i}</a></li>
                    </c:forEach>
                    <li><a class="active" href="/contact-list?page=${pagination.activePage}">${pagination.activePage}</a></li>
                    <c:forEach var="i" begin="${pagination.activePage + 1}" end="${pagination.endPage}">
                        <li><a href="/contact-list?page=${i}">${i}</a></li>
                    </c:forEach>
                    <c:if test="${pagination.activePage < pagination.endPage}">
                        <li><a href="/contact-list?page=${pagination.activePage + 1}">Вперед</a></li>
                    </c:if>
                </ul>
            </div>
        </form>
    </body>

    <link rel="stylesheet" href="css/grid-system.css">
    <link rel="stylesheet" href="css/style.css">
</html>
