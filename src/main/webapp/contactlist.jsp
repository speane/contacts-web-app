<%@ page errorPage="error.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="contacts" scope="request" type="java.util.List"/>
<!DOCTYPE html>
<html>
    <head>
        <title>Список контактов</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
        <link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
    </head>
    <body>
        <div id="message-modal-window" class="modal">
            <div class="modal-content">
                <header><h3 id="message-info-field"></h3></header>
                <input id="message-ok-button" type="button" class="centered block apply-button" value="Понятно">
            </div>
        </div>
        <form id="contact-list-form" class="centered main" method="post">
            <header>
                <h1>Список контактов</h1>
            </header>
            <section>
                <nav>
                    <div class="clearfix">
                        <a class="nav-button" href="${pageContext.request.contextPath}/app/contact-list">Все контакты</a>
                        <a class="nav-button" href="${pageContext.request.contextPath}/app/contact-search">Поиск</a>
                        <input type="button" class="nav-button"  id="send-email-button" value="Отправить email">
                        <a class="nav-button" id="add-contact-button" href="${pageContext.request.contextPath}/app/add-contact">Добавить</a>
                        <input class="nav-button" type="button" id="delete-contact-button" value="Удалить" />
                        <input class="nav-button" type="button" id="edit-checked-contact-button" value="Редактировать" />
                    </div>
                </nav>
                <div class="contact-list">
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
                                <a class="edit-contact" href="${pageContext.request.contextPath}/app/edit-contact?id=${contact.id}">
                                    <c:out value="${contact.firstName} ${contact.lastName}" />
                                </a>
                            </div>
                            <div class="cell-2">
                                <c:out value="${contact.birthday}" />
                            </div>
                            <div class="cell-4">
                                <c:if test="${contact.city != null}">г.</c:if><c:out value="${contact.city}" />
                                <c:if test="${contact.street != null}">ул.</c:if><c:out value="${contact.street}" />
                                <c:if test="${contact.house != null}">д.</c:if><c:out value="${contact.house}" />
                                <c:if test="${contact.flat != null}">кв.</c:if><c:out value="${contact.flat}" />
                            </div>
                            <div class="cell-2">${contact.job}</div>
                        </div>
                    </c:forEach>
                </div>
                <div class="pagination">
                    <c:if test="${not empty pagination}">
                        <div class="center">
                            <ul class="pagination">
                                <c:if test="${pagination.activePage > pagination.startPage}">
                                    <li>
                                        <a href="${pageContext.request.contextPath}/app/contact-list?page=${pagination.activePage - 1}">
                                            Назад
                                        </a>
                                    </li>
                                </c:if>
                                <c:forEach var="i" begin="${pagination.startPage}" end="${pagination.activePage - 1}">
                                    <li>
                                        <a href="${pageContext.request.contextPath}/app/contact-list?page=${i}">
                                            <c:out value="${i}" />
                                        </a>
                                    </li>
                                </c:forEach>
                                <li>
                                    <a class="active" href="${pageContext.request.contextPath}/app/contact-list?page=${pagination.activePage}">
                                        <c:out value="${pagination.activePage}" />
                                    </a>
                                </li>
                                <c:forEach var="i" begin="${pagination.activePage + 1}" end="${pagination.endPage}">
                                    <li>
                                        <a href="${pageContext.request.contextPath}/app/contact-list?page=${i}">
                                            <c:out value="${i}" />
                                        </a>
                                    </li>
                                </c:forEach>
                                <c:if test="${pagination.activePage < pagination.endPage}">
                                    <li>
                                        <a href="${pageContext.request.contextPath}/app/contact-list?page=${pagination.activePage + 1}">
                                            Вперед
                                        </a>
                                    </li>
                                </c:if>
                            </ul>
                        </div>
                    </c:if>
                </div>
            </section>
            <input type="hidden" id="dynamic-prefix" value="${pageContext.request.contextPath}">
            <input type="hidden" id="action-message" value="${not empty actionMessage ? actionMessage : ''}">
        </form>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/grid-system.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
        <script src="${pageContext.request.contextPath}/js/contact-list-script.js"></script>
    </body>
</html>
