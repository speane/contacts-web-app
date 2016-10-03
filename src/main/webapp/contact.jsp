<%@ page errorPage="error.jsp" %>
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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/grid-system.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <form class="centered main" method="post" action="${submitAction}" id="contact-form" class="centered contact" enctype="multipart/form-data" >
        <header>
            <h2><c:out value="${title}" /></h2>
        </header>
        <section>
            <div id="input-message-window" class="modal">
                <div class="modal-content">
                    <div id="input-messages">

                    </div>
                    <input type="button" id="message-ok-button" value="Ок" class="apply-button">
                </div>
            </div>
            <article class="edit-photo">
                <header><h3>Фото</h3></header>
                <div class="centered" id="contact-photo-select-area">
                    <img id="contact-photo-image" src="${contactImagePath}">
                    <input class="hidden" type="file" id="uploaded-contact-photo">
                </div>
                <div id="select-photo-modal" class="modal">
                    <div id="photo-select-form" class="modal-content">
                        <header>
                            <h4>Выбор фото</h4>
                        </header>
                        <input class="centered block" type="file" id="photo-file-input" accept="image/jpeg,image/png,image/gif">
                        <div id="apply-buttons">
                            <input class="centered block apply-button" type="button" id="save-photo-button" value="Сохранить">
                            <input class="centered block apply-button" type="button" id="cancel-photo-select-button" value="Отменить">
                        </div>
                    </div>
                </div>
            </article>
            <article class="main-info">
                <header><h3>Основная информация</h3></header>
                <label class="input-label">
                    Фамилия*
                    <input id="last-name" class="input-field" name="last-name" type="text" value="${not empty contact ? contact.lastName : ''}">
                </label>
                <label class="input-label">
                    Имя*
                    <input id="first-name" class="input-field" name="first-name" type="text" value="${not empty contact ? contact.firstName : ''}">
                </label>
                <label class="input-label">
                    Отчество
                    <input id="patronymic" class="input-field" name="patronymic" type="text" value="${not empty contact ? contact.patronymic : ''}">
                </label>
                <label class="input-label">
                    Пол
                    <select id="sex" class="input-field" name="sex">
                        <option ${empty contact or empty contact.sex ? 'selected' : ''} value="x">Не выбран</option>
                        <option ${not empty contact and contact.sex == 'm' ? 'selected' : ''} value="m">Мужчина</option>
                        <option ${not empty contact and contact.sex == 'f' ? 'selected' : ''} value="f">Женщина</option>
                    </select>
                </label>
                <label class="input-label">
                    Гражданство
                    <input id="nationality" class="input-field" name="nationality" type="text" value="${not empty contact ? contact.nationality : ''}">
                </label>
                <label class="input-label">
                    Семейное положение
                    <select id="marital-status" class="input-field" name="marital-status">
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
                <label class="input-label">
                    Web Site
                    <input id="website" class="input-field" name="website" type="url" value="${not empty contact ? contact.website : ''}">
                </label>
                <label class="input-label">
                    Email
                    <input id="email" class="input-field" name="email" type="email" value="${not empty contact ? contact.email : ''}">
                </label>
                <label class="input-label">
                    Текущее место работы
                    <input id="job" class="input-field" name="job" type="text" value="${not empty contact ? contact.job : ''}">
                </label>
                <h5>Дата рождения</h5>
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
                <label class="input-label">
                    Год рождения
                    <input id="year" class="input-field" type="text" value="${year}" name="year">
                </label>
                <label class="input-label">
                    Месяц
                    <select id="month" class="input-field" name="month">
                        <option ${month == 0 ? 'selected' : ''} value="0">Не выбран</option>
                        <c:forEach var="tempMonth" items="${months}" varStatus="loop">
                            <option ${month == (loop.index + 1) ? 'selected' : ''} value="${loop.index + 1}">${tempMonth}</option>
                        </c:forEach>
                    </select>
                </label>
                <label class="input-label">
                    День
                    <input id="day" class="input-field" type="text" value="${day}" name="day">
                </label>
                <h5>Адрес</h5>
                <label class="input-label">
                    Страна
                    <input id="state" class="input-field" name="state" type="text" value="${not empty contact ? contact.state : ''}">
                </label>
                <label class="input-label">
                    Город
                    <input id="city" class="input-field" name="city" type="text" value="${not empty contact ? contact.city : ''}">
                </label>
                <label class="input-label">
                    Улица
                    <input id="street" class="input-field" name="street" type="text" value="${not empty contact ? contact.street : ''}">
                </label>
                <label class="input-label">
                    Дом
                    <input id="house" class="input-field" name="house" type="text" value="${not empty contact ? contact.house : ''}">
                </label>
                <label class="input-label">
                    Квартира
                    <input id="flat" class="input-field" name="flat" type="text" value="${not empty contact ? contact.flat : ''}">
                </label>
                <label class="input-label">
                    Почтовый индекс
                    <input id="zipcode" class="input-field" name="zipcode" type="text" value="${not empty contact ? contact.zipCode : ''}">
                </label>
            </article>
            <article class="contact-phones">
                <header><h3 class="centered">Контактные телефоны</h3></header>
                <input class="action-button remove" type="button" id="remove-phone-button" value="Удалить">
                <input class="action-button edit" type="button" id="edit-phone-button" value="Редактировать">
                <input class="action-button add" type="button" id="add-phone-button" value="Добавить">
                <div id="phone-edit-modal" class="modal">
                    <div class="modal-content">
                        <article class="edit-phone">
                            <header>
                                <h4>Редактирование телефонного номера</h4>
                            </header>
                            <div class="data-fields">
                                <label class="input-label">
                                    Код страны
                                    <input class="input-field" type="text" id="country-code">
                                </label>
                                <label class="input-label">
                                    Код оператора
                                    <input class="input-field" type="text" id="operator-code">
                                </label>
                                <label class="input-label">
                                    Номер
                                    <input class="input-field" type="text" id="phone-number">
                                </label>
                                <label class="input-label">
                                    Тип телефона
                                    <select class="input-field" id="phone-type-select">
                                        <option disabled>Тип</option>
                                        <c:forEach var="phoneType" items="${phoneTypes}">
                                            <option value="${phoneType.name}">${phoneType.name}</option>
                                        </c:forEach>
                                    </select>
                                </label>
                                <label class="input-label">
                                    Комментарий
                                    <textarea class="input-field" id="phone-commentary"></textarea>
                                </label>
                            </div>
                        </article>
                        <button class="centered block apply-button" type="button" id="save-phone-button">
                            Сохранить
                        </button>
                        <button class="centered block apply-button" type="button" id="cancel-phone-edit-button">
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
            </article>
            <article class="contact-attachments">
                <header>
                    <h3 class="centered">Присоединения</h3>
                </header>
                <input class="action-button remove" type="button" id="remove-attachment-button" value="Удалить">
                <input class="action-button edit" type="button" id="edit-attachment-button" value="Редактировать">
                <input class="action-button add" type="button" id="add-attachment-button" value="Добавить">
                <div id="attachment-edit-modal" class="modal">
                    <div id="attachment-form" class="modal-content">
                        <header><h4>Редактирование присоединения</h4></header>
                        <div class="data-fields">
                            <label class="input-label">
                                Имя файла присоединения
                                <input class="input-field" type="text" id="attachment-file-name">
                            </label>
                            <label class="input-label">
                                Комментарий
                                <textarea class="input-field" id="attachment-commentary"></textarea>
                            </label>
                        </div>
                        <input class="centered block" type="file" id="attachment-file-input" value="Выберите файл">
                        <input class="centered block apply-button" type="button" id="save-attachment-button" value="Сохранить">
                        <input class="centered block apply-button" type="button" id="cancel-attachment-edit-button" value="Отменить">
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
            </article>
            <input class="centered block save-button" type="button" id="save-contact-button" value="Сохранить">
        </section>
    </form>
    <script src="${pageContext.request.contextPath}/js/edit-contact-script.js"></script>
</body>
</html>
