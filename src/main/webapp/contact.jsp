<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>
        <c:choose>
            <c:when test="${contact = null}">
                Создание контакта
            </c:when>
            <c:otherwise>
                Редактирование контакта
            </c:otherwise>
        </c:choose>
    </title>
</head>
<body>
    <form class="center">
        <input type="text" value="${contact.firstName}"><br>
        <input type="text" value="${contact.lastName}"><br>
    </form>
</body>
</html>
