<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Поиск</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
    <form class="centered main" action="/app/search" method="post">
        <header>
            <h2>Поиск контактов</h2>
        </header>
        <section>
            <header><h3>Параметры поиска</h3></header>
            <div class="search-data-fields">
                <h5>Основные сведения</h5>
                <label class="input-label">
                    Имя
                    <input class="input-field" type="text" name="first-name">
                </label>
                <label class="input-label">
                    Фамилия
                    <input class="input-field" type="text" name="last-name">
                </label>
                <label class="input-label">
                    Отчество
                    <input class="input-field" type="text" name="patronymic">
                </label>
                <h5>Дата рождения</h5>
                <label class="input-label">
                    Год рождения
                    <input class="input-field" type="text" name="year">
                </label>
                <label class="input-label">
                    Месяц
                    <select class="input-field" name="month">
                        <option selected value="0">Не выбран</option>
                        <c:forEach var="tempMonth" items="${months}" varStatus="loop">
                            <option value="${loop.index}">${tempMonth}</option>
                        </c:forEach>
                    </select>
                </label>
                <label class="input-label">
                    День
                    <input class="input-field" type="text" value="${day}" name="day">
                </label>
                <h5>Дополнительные сведения</h5>
                <label class="input-label">
                    Пол
                    <select class="input-field" name="sex">
                        <option selected value="x">Не выбран</option>
                        <option value="m">Мужчина</option>
                        <option value="f">Женщина</option>
                    </select>
                </label>
                <label class="input-label">
                    Семейное положение
                    <select class="input-field" name="marital-status">
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
                    <input class="input-field" type="text" name="nationality">
                </label>
                <h5>Адрес</h5>
                <label class="input-label">
                    Страна
                    <input class="input-field" type="text" name="state">
                </label>
                <label class="input-label">
                    Город
                    <input class="input-field" type="text" name="city">
                </label>
                <label class="input-label">
                    Улица
                    <input class="input-field" type="text" name="street">
                </label>
                <label class="input-label">
                    Дом
                    <input class="input-field" type="text" name="house">
                </label>
                <label class="input-label">
                    Квартира
                    <input class="input-field" type="text" name="flat">
                </label>
                <label class="input-label">
                    Почтовый индекс
                    <input class="input-field" type="text" name="zipcode">
                </label>
            </div>
            <button class="centered block apply-button" type="submit" id="find-contacts-button">Найти</button>
        </section>
    </form>
</body>
</html>
