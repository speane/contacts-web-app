<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form method="post" action="/app/send-email">
        <header>
            <h1>Отправка email</h1>
        </header>
        <section>
            <h3>Получатели:</h3>
            <c:forEach var="recipient" items="${emails}">
                <label>
                        ${recipient}
                </label>
            </c:forEach>
            <label>
                Тема
                <input type="text" name="theme-text-input">
            </label>
            <select name="email-pattern-select" id="email-pattern-select">
                <option value="">Не выбран</option>
                <c:forEach var="pattern" items="${patterns}" varStatus="loop">
                    <option value="${pattern}">
                        Шаблон-${loop.index}
                    </option>
                </c:forEach>
            </select>
            <textarea id="email-text-area" name="email-text-area" cols="40" rows="6"></textarea>
            <input type="submit" id="send-email-button">
        </section>
    </form>
    <script src="/js/send-email-script.js"></script>
</body>
</html>
