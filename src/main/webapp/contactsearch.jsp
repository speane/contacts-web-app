<%@ page errorPage="error.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Поиск</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <a class="back-button" href="${pageContext.request.contextPath}/app/contact-list">К списку контактов</a>
    <form id="search-form" class="centered main" action="${pageContext.request.contextPath}/app/search" method="post">
        <header>
            <h2>Поиск контактов</h2>
        </header>
        <section>
            <header><h3>Параметры поиска</h3></header>
            <div class="search-data-fields">
                <h5>Основные сведения</h5>
                <label class="input-label">
                    Фамилия
                    <input id="last-name" class="input-field" type="text" name="last-name">
                </label>
                <label class="input-label">
                    Имя
                    <input id="first-name" class="input-field" type="text" name="first-name">
                </label>
                <label class="input-label">
                    Отчество
                    <input id="patronymic" class="input-field" type="text" name="patronymic">
                </label>
                <h5>Дата рождения</h5>
                <label class="input-label">
                    Год рождения
                    <input id="year" class="input-field" type="text" name="year">
                </label>
                <label class="input-label">
                    Месяц
                    <select id="month" class="input-field" name="month">
                        <option selected value="0">Не выбран</option>
                        <c:forEach var="tempMonth" items="${months}" varStatus="loop">
                            <option value="${loop.index + 1}">${tempMonth}</option>
                        </c:forEach>
                    </select>
                </label>
                <label class="input-label">
                    День
                    <input id="day" class="input-field" type="text" value="${day}" name="day">
                </label>
                <div class="date-params">
                    <label class="input-label">
                        Точная дата рождения
                        <input checked class="date-param" type="radio" name="date-param" value="equal">
                    </label>
                    <label class="input-label">
                        Старше
                        <input class="date-param" type="radio" name="date-param" value="older">
                    </label>
                    <label class="input-label">
                        Младше
                        <input class="date-param" type="radio" name="date-param" value="younger">
                    </label>
                </div>
                <h5>Дополнительные сведения</h5>
                <label class="input-label">
                    Пол
                    <select id="sex" class="input-field" name="sex">
                        <option selected value="x">Не выбран</option>
                        <option value="m">Мужчина</option>
                        <option value="f">Женщина</option>
                    </select>
                </label>
                <label class="input-label">
                    Семейное положение
                    <select id="marital-status" class="input-field" name="marital-status">
                        <option selected value="0">Не выбрано</option>
                        <c:forEach var="maritalStatus" items="${maritalStatuses}">
                            <option ${empty contact or contact.maritalStatus != maritalStatus.id ? '' : 'selected'} value="${maritalStatus.id}">
                                    ${maritalStatus.name}
                            </option>
                        </c:forEach>
                    </select>
                </label>
                <label class="input-label">
                    Гражданство
                    <input id="nationality" class="input-field" type="text" name="nationality">
                </label>
                <h5>Адрес</h5>
                <label class="input-label">
                    Страна
                    <input id="state" class="input-field" type="text" name="state">
                </label>
                <label class="input-label">
                    Город
                    <input id="city" class="input-field" type="text" name="city">
                </label>
                <label class="input-label">
                    Улица
                    <input id="street" class="input-field" type="text" name="street">
                </label>
                <label class="input-label">
                    Дом
                    <input id="house" class="input-field" type="text" name="house">
                </label>
                <label class="input-label">
                    Квартира
                    <input id="flat" class="input-field" type="text" name="flat">
                </label>
                <label class="input-label">
                    Почтовый индекс
                    <input id="zipcode" class="input-field" type="text" name="zipcode">
                </label>
            </div>
            <div id="error-messages-modal" class="modal">
                <div class="modal-content">
                    <div id="input-error-messages"></div>
                    <input class="apply-button" type="button" id="ok-messages-button" value="Ок">
                </div>
            </div>
            <input type="button" class="centered block apply-button" id="find-contacts-button" value="Найти">
        </section>
    </form>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/search-contacts-script.js"></script>
</body>
</html>
