<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Поиск</title>
</head>
<body>
    <form>
        <header>
            Поиск контактов
        </header>
        <section>
            <h3>Параметры поиска</h3>
            <label>
                Имя
                <input type="text" name="first-name">
            </label>
            <label>
                Фамилия
                <input type="text" name="last-name">
            </label>
            <label>
                Отчество
                <input type="text" name="patronymic">
            </label>
            <label>
                Дата рождения
                <input type="text" name="birthday">
            </label>
            <label>
                Пол
                <input type="text" name="sex">
            </label>
            <label>
                Семейное положение
                <input type="text" name="marital-status">
            </label>
            <label>
                Гражданство
                <input type="text" name="nationality">
            </label>
            <label>
                Страна
                <input type="text" name="state">
            </label>
            <label>
                Город
                <input type="text" name="city">
            </label>
            <label>
                Улица
                <input type="text" name="street">
            </label>
            <label>
                Дом
                <input type="text" name="house">
            </label>
            <label>
                Квартира
                <input type="text" name="flat">
            </label>
            <button type="button" id="find-contacts-button">Найти</button>
        </section>
    </form>
</body>
</html>
