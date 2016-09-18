<jsp:useBean id="contact" scope="request" type="com.evgenyshilov.web.contacts.database.model.Contact"/>
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
            <label>
                Фамилия*
                <input type="text" value="${contact.lastName}">
            </label>
            <label>
                Отчество
                <input type="text" value="${contact.patronymic}">
            </label>
            <label>
                Дата рождения
                <input type="text" value="${contact.birthday}">
            </label>
            <label>
                Пол
                <input type="text" value="${contact.sex}">
            </label>
            <label>
                Гражданство
                <input type="text" value="${contact.nationality}">
            </label>
            <label>
                Семейное положение
                <input type="text" value="${contact.maritalStatus}">
            </label>
            <label>
                Web Site
                <input type="text" value="${contact.website}">
            </label>
            <label>
                Email
                <input type="text" value="${contact.email}">
            </label>
            <label>
                Текущее место работы
                <input type="text" value="${contact.job}">
            </label>
            <h5>Адрес</h5>
            <label>
                Страна
                <input type="text" value="${contact.state}">
            </label>
            <label>
                Город
                <input type="text" value="${contact.city}">
            </label>
            <label>
                Улица
                <input type="text" value="${contact.street}">
            </label>
            <label>
                Дом
                <input type="text" value="${contact.house}">
            </label>
            <label>
                Квартира
                <input type="text" value="${contact.flat}">
            </label>
            <h3>Контактные телефоны</h3>
            <div class="row">
                <div class="cell-1">
                    Выбрать
                </div>
                <div class="cell-3">
                    Номер телефона
                </div>
                <div class="cell-2">
                    Описание номера
                </div>
                <div class="cell-6">
                    Комментарий
                </div>
            </div>
            <c:forEach var="phone" items="${contact.phones}">
                <div class="row">
                    <div class="cell-1">
                        <label>
                            <input type="checkbox" name="check-phone-${phone.id}">
                        </label>
                    </div>
                    <div class="cell-3">
                        <c:out value="+${phone.countryCode}(${phone.operatorCode})${phone.number}" />
                    </div>
                    <div class="cell-2">
                        <c:if test="${phone.type == 'm'}">
                            Мобильный
                        </c:if>
                        <c:if test="${phone.type == 'h'}">
                            Домашний
                        </c:if>
                    </div>
                    <div class="cell-6">
                        <c:out value="${phone.commentary}" />
                    </div>
                </div>
            </c:forEach>
            <h3>Присоединения</h3>
            <div class="row">
                <div class="cell-1">
                    Выбрать
                </div>
                <div class="cell-3">
                    Имя файла
                </div>
                <div class="cell-2">
                    Дата загрузки
                </div>
                <div class="cell-6">
                    Комментарий
                </div>
            </div>
            <c:forEach var="attachment" items="${contact.attachments}">
                <div class="row">
                    <div class="cell-1">
                        <label>
                            <input type="checkbox" name="attachment-check-${attachment.id}">
                        </label>
                    </div>
                    <div class="cell-3">
                        <c:out value="${attachment.filename}" />
                    </div>
                    <div class="cell-2">
                        <c:out value="${attachment.uploadDate}" />
                    </div>
                    <div class="cell-6">
                        <c:out value="${attachment.commentary}" />
                    </div>
                </div>
            </c:forEach>
        </section>
    </form>
    <link rel="stylesheet" type="text/css" href="css/grid-system.css">
    <link rel="stylesheet" type="text/css" href="css/style.css">
</body>
</html>
