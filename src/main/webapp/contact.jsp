<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:choose>
    <c:when test="${empty contact}">
        <c:set var="title" value="Создание контакта" />
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
    <link rel="stylesheet" type="text/css" href="/css/grid-system.css">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
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
                <input type="text" value="${not empty contact ? contact.firstName : ''}">
            </label>
            <label>
                Фамилия*
                <input type="text" value="${not empty contact ? contact.lastName : ''}">
            </label>
            <label>
                Отчество
                <input type="text" value="${not empty contact ? contact.patronymic : ''}">
            </label>
            <label>
                <h4>Дата рождения</h4>
                Год рождения
                <c:if test="${not empty contact}">
                    <input type="text" value="<fmt:formatDate value="${contact.birthday}" pattern="yyyy" />">
                </c:if>
                Месяц
                <c:if test="${not empty contact}">
                    <input type="text" value="<fmt:formatDate value="${contact.birthday}" pattern="MM" />">
                </c:if>
                День
                <c:if test="${not empty contact}">
                    <input type="text" value="<fmt:formatDate value="${contact.birthday}" pattern="dd" />">
                </c:if>
            </label>
            <label>
                Пол
                <select>
                    <option disabled>Выберите пол</option>
                    <option ${empty contact or contact.sex == 'f' ? '' : 'selected'} value="m">Мужчина</option>
                    <option ${empty contact or contact.sex == 'm' ? '' : 'selected'} value="f">Женщина</option>
                </select>
            </label>
            <label>
                Гражданство
                <input type="text" value="${not empty contact ? contact.nationality : ''}">
            </label>
            <label>
                Семейное положение
                <input type="text" value="${not empty contact ? contact.maritalStatus : ''}">
            </label>
            <label>
                Web Site
                <input type="text" value="${not empty contact ? not contact.website : ''}">
            </label>
            <label>
                Email
                <input type="text" value="${not empty contact ? contact.email : ''}">
            </label>
            <label>
                Текущее место работы
                <input type="text" value="${not empty contact ? contact.job : ''}">
            </label>
            <h5>Адрес</h5>
            <label>
                Страна
                <input type="text" value="${not empty contact ? contact.state : ''}">
            </label>
            <label>
                Город
                <input type="text" value="${not empty contact ? contact.city : ''}">
            </label>
            <label>
                Улица
                <input type="text" value="${not empty contact ? contact.street : ''}">
            </label>
            <label>
                Дом
                <input type="text" value="${not empty contact ? contact.house : ''}">
            </label>
            <label>
                Квартира
                <input type="text" value="${not empty contact ? contact.flat : ''}">
            </label>
            <h3>Контактные телефоны</h3>
            <input type="button" id="add-phone-button" value="Добавить">
            <input type="button" id="remove-phone-button" value="Удалить">
            <input type="button" id="edit-phone-button" value="Редактировать">
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
                <c:if test="${not empty contact}">
                    <c:forEach var="phone" items="${contact.phones}">
                        <div id="contact-phone-${phone.id}" class="row">
                            <div class="cell-1">
                                <label>
                                    <input type="checkbox" name="phone-check" value="${phone.id}">
                                </label>
                            </div>
                            <div id="phone-number-${phone.id}" class="cell-3">
                                <c:out value="+${phone.countryCode}(${phone.operatorCode})${phone.number}" />
                            </div>
                            <div id="phone-type-${phone.id}" class="cell-2">
                                <c:if test="${phone.type == 'm'}">
                                    Мобильный
                                </c:if>
                                <c:if test="${phone.type == 'h'}">
                                    Домашний
                                </c:if>
                            </div>
                            <div id="phone-commentary-${phone.id}" class="cell-6">
                                <c:out value="${phone.commentary}" />
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
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
            <div id="attachment-list">
                <c:if test="${not empty contact}">
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
                </c:if>
            </div>
            <input type="submit" id="save-contact-button" value="Сохранить">
        </section>
    </form>
    <script src="/js/edit-contact-script.js"></script>
</body>
</html>
