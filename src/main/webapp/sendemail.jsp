<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Отправка email</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
    <form class="centered main" method="post" action="/app/send-emails">
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
                    <input class="input-field" type="text" name="theme-text-input">
                </label>
                <label class="input-label">
                    Шаблон сообщения
                    <select class="input-field" name="email-pattern-select" id="email-pattern-select">
                        <option value="">Свой шаблон</option>
                        <c:forEach var="pattern" items="${patterns}" varStatus="loop">
                            <option value="${pattern}">
                                Шаблон-${loop.index + 1}
                            </option>
                        </c:forEach>
                    </select>
                </label>
                <label class="input-label">
                    Текст сообщения
                    <textarea class="input-field" id="email-text-area" name="email-text-area" cols="40" rows="6"></textarea>
                </label>
            </div>
            <input class="apply-button" type="submit" id="send-email-button">
        </section>
    </form>
    <script src="/js/send-email-script.js"></script>
</body>
</html>
