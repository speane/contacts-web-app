<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>
        <c:choose>
            <c:when test="${contact == null}">
                <c:set var="title" value="Создание контакта" />
                <c:out value="${title}" />
            </c:when>
            <c:otherwise>
                <c:set var="title" value="Редактирование контакта" />
                <c:out value="${title}" />
            </c:otherwise>
        </c:choose>
    </title>
</head>
<body>
    <form class="centered contact">
        <header>
            <h2><c:out value="${title}" /></h2>
        </header>
        <section>
            <h3>Основная информация</h3>
            <label>
                Имя*
                <input type="text" value="${contact.firstName}">
            </label>
            <label class="clearfix">Фамилия* <input type="text" value="${contact.lastName}"></label>
            <label>Отчество <input type="text" value="${contact.patronymic}"></label>
            <label>Дата рождения <input type="text" value="${contact.birthday}"></label>
            <label>Пол <input type="text" value="${contact.sex}"></label>
            <label>Гражданство <input type="text" value="${contact.nationality}"></label>
            <label>Семейное положение <input type="text" value="${contact.maritalStatus}"></label>
            <label>Web Site <input type="text" value="${contact.website}"></label>
            <label>Email <input type="text" value="${contact.email}"></label>
            <label>Текущее место работы <input type="text" value="${contact.job}"></label>
            <fieldset>
                <legend>Адрес</legend>
                <label>Страна <input type="text" value="${contact.address.country}"></label>
                <label>Город <input type="text" value="${contact.address.city}"></label>
                <label>Улица <input type="text" value="${contact.address.street}"></label>
                <label>Дом <input type="text" value="${contact.address.house}"></label>
                <label>Квартира <input type="text" value="${contact.address.flat}"></label>
            </fieldset>
        </section>
        <div>
            <h3>Контактные телефоны</h3>
        </div>
    </form>
    <link rel="stylesheet" type="text/css" href="css/grid-system.css">
    <link rel="stylesheet" type="text/css" href="css/style.css">
</body>
</html>
