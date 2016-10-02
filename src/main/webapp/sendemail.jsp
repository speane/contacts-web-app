<%@ page errorPage="error.jsp" %>
<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Отправка email</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
    <form id="send-email-form" class="centered main" method="post" action="/app/send-emails">
        <header>
            <h1>Отправка email</h1>
        </header>
        <section>
            <div class="recipients">
                <header><h4>Получатели</h4></header>
                <c:forEach var="recipient" items="${recipients}">
                    <label class="input-label">
                        ${recipient.firstName} ${recipient.lastName}
                        <input class="input-field" type="text" disabled value="${recipient.email}">
                    </label>
                    <input type="hidden" value="${recipient.id}" name="recipient-id">
                </c:forEach>
            </div>
            <div class="email-properties clearfix">
                <header><h4>Параметры сообщения</h4></header>
                <label class="input-label">
                    Тема сообщения
                    <input id="message-theme" class="input-field" type="text" name="message-theme">
                </label>
                <label class="input-label">
                    Шаблон сообщения
                    <select class="input-field" name="email-pattern-select" id="email-pattern-select">
                        <option value="">Свой шаблон</option>
                        <c:forEach var="template" items="${templates}" varStatus="loop">
                            <option value="${template}">
                                Шаблон-${loop.index + 1}
                            </option>
                        </c:forEach>
                    </select>
                </label>
                <label class="input-label">
                    Вставить элемент
                    <select class="input-field" name="pattern-element-select" id="pattern-element-select">
                        <option value="0">Элемент шаблона</option>
                        <c:forEach var="element" items="${elements}" varStatus="loop">
                            <option value="${element}">
                                ${element}
                            </option>
                        </c:forEach>
                    </select>
                </label>
                <label class="input-label">
                    Текст сообщения
                    <textarea id="message-text" class="input-field" name="message-text" cols="40" rows="6"></textarea>
                </label>
            </div>
            <div id="error-messages-modal" class="modal">
                <div class="modal-content">
                    <div id="input-error-messages"></div>
                    <input class="apply-button" type="button" id="ok-messages-button" value="Ок">
                </div>
            </div>
            <input class="centered block apply-button" type="button" id="send-email-button" value="Отправить">
        </section>
    </form>
    <script src="/js/send-email-script.js"></script>
</body>
</html>
