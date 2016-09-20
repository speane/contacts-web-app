<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="contacts" scope="request" type="java.util.List"/>
<!DOCTYPE html>
<html>
    <head>
        <title>Список контактов</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    </head>
    <body>
        <form id="contact-list-form" class="centered contact" method="post">
            <header>
                <h1 class="centered">Список контактов</h1>
            </header>
            <section>
                <div class="clearfix">
                    <a class="nav-button left" href="/app/contact-search">Search</a>
                    <a class="nav-button left" href="#">Send e-mail</a>
                    <button class="action-button right" formaction="/app/add-contact">ADD</button>
                    <input type="submit" id="delete-contact-button" value="Удалить" />
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
                            <label>
                                <input type="checkbox" name="contact-check" value="${contact.id}">
                            </label>
                        </div>
                        <div class="cell-3">
                            <a href="/app/edit-contact?id=${contact.id}">
                                <c:out value="${contact.firstName} ${contact.lastName}" />
                            </a>
                        </div>
                        <div class="cell-2">
                            <c:out value="${contact.birthday}" />
                        </div>
                        <div class="cell-4">
                            <c:if test="${contact.city != null}">
                                г.
                            </c:if><c:out value="${contact.city}" />
                            <c:if test="${contact.street != null}">
                                ул.
                            </c:if><c:out value="${contact.street}" />
                            <c:if test="${contact.house != null}">
                                д.
                            </c:if><c:out value="${contact.house}" />
                            <c:if test="${contact.flat != null}">
                                кв.
                            </c:if><c:out value="${contact.flat}" />
                        </div>
                        <div class="cell-2">${contact.job}</div>
                    </div>
                </c:forEach>
                <c:if test="${pagination != null}">
                    <div class="center">
                        <ul class="pagination">
                            <c:if test="${pagination.activePage > pagination.startPage}">
                                <li>
                                    <a href="/app/contact-list?page=${pagination.activePage - 1}">
                                        Назад
                                    </a>
                                </li>
                            </c:if>
                            <c:forEach var="i" begin="${pagination.startPage}" end="${pagination.activePage - 1}">
                                <li>
                                    <a href="/app/contact-list?page=${i}">
                                        <c:out value="${i}" />
                                    </a>
                                </li>
                            </c:forEach>
                            <li>
                                <a class="active" href="/app/contact-list?page=${pagination.activePage}">
                                    <c:out value="${pagination.activePage}" />
                                </a>
                            </li>
                            <c:forEach var="i" begin="${pagination.activePage + 1}" end="${pagination.endPage}">
                                <li>
                                    <a href="/app/contact-list?page=${i}">
                                        <c:out value="${i}" />
                                    </a>
                                </li>
                            </c:forEach>
                            <c:if test="${pagination.activePage < pagination.endPage}">
                                <li>
                                    <a href="/app/contact-list?page=${pagination.activePage + 1}">
                                        Вперед
                                    </a>
                                </li>
                            </c:if>
                        </ul>
                    </div>
                </c:if>
            </section>
        </form>
        <link rel="stylesheet" type="text/css" href="/css/grid-system.css">
        <link rel="stylesheet" type="text/css" href="/css/style.css">
        <script src="/js/contact-list-script.js"></script>
    </body>
</html>
