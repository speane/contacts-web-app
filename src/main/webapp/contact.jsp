<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:choose>
    <c:when test="${contact == null}">
        <c:set var="title" value="Создание контакта" />
        <c:set var="first-name" value="" />
        <c:set var="last-name" value="" />
        <c:set var="patronymic" value="" />
        <c:set var="submitAction" value="/app/create-contact" />
    </c:when>
    <c:otherwise>
        <c:set var="title" value="Редактирование контакта" />
        <c:set var="submitAction" value="/app/update-contact" />
    </c:otherwise>
</c:choose>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title><c:out value="${title}" /></title>
</head>
<body>
    <form method="post" action="${submitAction}" id="contact-form" class="centered contact">
        <header>
            <h2><c:out value="${title}" /></h2>
        </header>
        <section>
            <h3>Фото</h3>
            <img id="photo-edit-image" src="#">
            <div id="choose-photo-modal" class="modal">
                <div class="modal-content">
                    <h4>Выбор фото</h4>
                    <label>
                        Путь к фото на диске
                        <input type="text" name="photo-disk-path">
                    </label>
                    <button type="button">Найти</button>
                    <button type="button">Сохранить</button>
                    <button type="button">Отменить</button>
                </div>
            </div>
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
            <button type="button" id="add-phone-button">Добавить</button>
            <button type="button" id="remove-phone-button">Удалить</button>
            <button type="button" id="edit-phone-button">Редактировать</button>
            <div id="phone-edit-modal" class="modal">
                <div class="modal-content">
                    <h4>Редактирование телефонного номера</h4>
                    <label>
                        Код страны
                        <input type="text" id="country-code">
                    </label>
                    <label>
                        Код оператора
                        <input type="text" id="operator-code">
                    </label>
                    <label>
                        Номер
                        <input type="text" id="phone-number">
                    </label>
                    <label>
                        Тип телефон
                        <input type="text" id="phone-type">
                    </label>
                    <label>
                        Комментарий
                        <input type="text" id="phone-commentary">
                    </label>
                    <button type="button" id="save-phone-button">
                        Сохранить
                    </button>
                    <button type="button" id="cancel-phone-edit-button">
                        Отменить
                    </button>
                </div>
            </div>
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
            <div id="phone-list">
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
            </div>
            <h3>Присоединения</h3>
            <button type="button" id="add-attachment-button">Добавить</button>
            <button type="button" id="remove-attachment-button">Удалить</button>
            <button type="button" id="edit-attachment-button">Редактировать</button>
            <div id="attachment-edit-modal" class="modal">
                <div class="modal-content">
                    <h4>Редактирование присоединения</h4>
                    <label>
                        Имя файла присоединения
                        <input type="text" name="attachment-name">
                    </label>
                    <label>
                        Комментарий
                        <input type="text" name="attachment-commentary">
                    </label>
                    <button type="button" name="save-attachment-button">Созранить</button>
                    <button type="button" name="cancel-attachment-edit-button">Отменить</button>
                </div>
            </div>
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
    <link rel="stylesheet" type="text/css" href="/css/grid-system.css">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <script src="/js/edit-contact-script.js"></script>

</body>
</html>
