<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:choose>
    <c:when test="${empty contact}">
        <c:set var="title" value="Создание контакта" />
        <c:set var="submitAction" value="/app/create-contact" />
        <c:set var="contactImagePath" value="/images/default.png" />
    </c:when>
    <c:otherwise>
        <c:set var="title" value="Редактирование контакта" />
        <c:set var="submitAction" value="/app/update-contact?id=${contact.id}" />
        <c:set var="contactImagePath" value="/images/${not empty contact.imageFileName ? contact.imageFileName : 'default.png'}" />
    </c:otherwise>
</c:choose>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title><c:out value="${title}" /></title>
</head>
<body>
    <link rel="stylesheet" type="text/css" href="/css/grid-system.css">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <form method="post" action="${submitAction}" id="contact-form" class="centered contact" enctype="multipart/form-data" >
        <header>
            <h2><c:out value="${title}" /></h2>
        </header>
        <section>
            <h3>Фото</h3>
            <div id="contact-photo-select-area">
                <img id="contact-photo-image" src="${contactImagePath}">
                <input type="file" id="uploaded-contact-photo">
            </div>
            <div id="select-photo-modal" class="modal">
                <div id="photo-select-form" class="modal-content">
                    <h4>Выбор фото</h4>
                    <input type="button" id="clear-photo-button" value="Удалить">
                    Путь к фото на диске
                    <input type="file" id="photo-file-input" value="Выберите фото" accept="image/jpeg,image/png,image/gif">
                    <input type="button" id="save-photo-button" value="Сохранить">
                    <input type="button" id="cancel-photo-select-button" value="Отменить">
                </div>
            </div>
            <c:if test="${not empty errorMessage}">
                <h4>
                    <c:out value="${errorMessage}" />
                </h4>
            </c:if>
            <h3>Основная информация</h3>
            <label>
                Имя*
                <input name="first-name" type="text" value="${not empty contact ? contact.firstName : ''}">
            </label>
            <label>
                Фамилия*
                <input name="last-name" type="text" value="${not empty contact ? contact.lastName : ''}">
            </label>
            <label>
                Отчество
                <input name="patronymic" type="text" value="${not empty contact ? contact.patronymic : ''}">
            </label>
            <h4>Дата рождения</h4>
            <c:if test="${not empty contact and not empty contact.birthday}">
                <fmt:formatDate value="${contact.birthday}" pattern="yyyy" var="year" />
                <fmt:formatDate value="${contact.birthday}" pattern="M" var="month" />
                <fmt:formatDate value="${contact.birthday}" pattern="dd" var="day" />
            </c:if>
            <c:if test="${empty contact or empty contact.birthday}">
                <c:set var="year" value="" />
                <c:set var="month" value="0" />
                <c:set var="day" value="" />
            </c:if>
            <label>
                Год рождения
                <input type="text" value="${year}" name="year">
            </label>
            <label>
                <select name="month">
                    <c:forEach var="tempMonth" items="${months}" varStatus="loop">
                        <option ${month == loop.index ? 'selected' : ''} value="${loop.index}">${tempMonth}</option>
                    </c:forEach>
                </select>
            </label>
            <label>
                День
                <input type="text" value="${day}" name="day">
            </label>
            <label>
                Пол
                <select name="sex">
                    <option disabled>Выберите пол</option>
                    <option ${empty contact or contact.sex == 'f' ? '' : 'selected'} value="m">Мужчина</option>
                    <option ${empty contact or contact.sex == 'm' ? '' : 'selected'} value="f">Женщина</option>
                </select>
            </label>
            <label>
                Гражданство
                <input name="nationality" type="text" value="${not empty contact ? contact.nationality : ''}">
            </label>
            <label>
                Семейное положение
                <select name="marital-status">
                    <option ${empty contact or empty contact.maritalStatus ? 'selected' : ''} value="0">
                        Не выбрано
                    </option>
                    <c:forEach var="maritalStatus" items="${maritalStatuses}">
                        <option ${empty contact or contact.maritalStatus != maritalStatus.id ? '' : 'selected'} value="${maritalStatus.id}">
                            ${maritalStatus.name}
                        </option>
                    </c:forEach>
                </select>
            </label>
            <label>
                Web Site
                <input name="website" type="url" value="${not empty contact ? contact.website : ''}">
            </label>
            <label>
                Email
                <input name="email" type="email" value="${not empty contact ? contact.email : ''}">
            </label>
            <label>
                Текущее место работы
                <input name="job" type="text" value="${not empty contact ? contact.job : ''}">
            </label>
            <h5>Адрес</h5>
            <label>
                Страна
                <input name="state" type="text" value="${not empty contact ? contact.state : ''}">
            </label>
            <label>
                Город
                <input name="city" type="text" value="${not empty contact ? contact.city : ''}">
            </label>
            <label>
                Улица
                <input name="street" type="text" value="${not empty contact ? contact.street : ''}">
            </label>
            <label>
                Дом
                <input name="house" type="text" value="${not empty contact ? contact.house : ''}">
            </label>
            <label>
                Квартира
                <input name="flat" type="text" value="${not empty contact ? contact.flat : ''}">
            </label>
            <label>
                Почтовый индекс
                <input name="zipcode" type="text" value="${not empty contact ? contact.zipCode : ''}">
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
                        <select id="phone-type-select">
                            <c:forEach var="phoneType" items="${phoneTypes}">
                                <option value="${phoneType.name}">${phoneType.name}</option>
                            </c:forEach>
                        </select>
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
                                <c:out value="${phone.type}" />
                            </div>
                            <div id="phone-commentary-${phone.id}" class="cell-6">
                                <c:out value="${phone.commentary}" />
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
            </div>
            <h3>Присоединения</h3>
            <input type="button" id="add-attachment-button" value="Добавить">
            <input type="button" id="remove-attachment-button" value="Удалить">
            <input type="button" id="edit-attachment-button" value="Редактировать">
            <div id="attachment-edit-modal" class="modal">
                <div id="attachment-form" class="modal-content">
                    <h4>Редактирование присоединения</h4>
                    <label>
                        Имя файла присоединения
                        <input type="text" id="attachment-file-name">
                    </label>
                    <label>
                        Комментарий
                        <input type="text" id="attachment-commentary">
                    </label>
                    <input type="file" id="attachment-file-input" value="Выберите файл">
                    <input type="button" id="save-attachment-button" value="Сохранить">
                    <input type="button" id="cancel-attachment-edit-button" value="Отменить">
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
                        <div id="attachment-${attachment.id}" class="row">
                            <div class="cell-1">
                                <label>
                                    <input type="checkbox" name="attachment-check" value="${attachment.id}">
                                </label>
                            </div>
                            <div id="attachment-filename-${attachment.id}" class="cell-3">
                                <c:out value="${attachment.filename}" />
                            </div>
                            <div id="attachment-upload-date-${attachment.id}" class="cell-2">
                                <c:out value="${attachment.uploadDate}" />
                            </div>
                            <div id="attachment-commentary-${attachment.id}" class="cell-6">
                                <c:out value="${attachment.commentary}" />
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
            </div>
            <input type="button" id="save-contact-button" value="Сохранить">
        </section>
    </form>
    <script src="/js/edit-contact-script.js"></script>
</body>
</html>
