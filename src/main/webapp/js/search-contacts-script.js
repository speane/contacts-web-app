var findContactsButton = document.getElementById('find-contacts-button');
var errorMessagesModal = document.getElementById('error-messages-modal');
var okMessagesButton = document.getElementById('ok-messages-button');
var errorMessagesList = document.getElementById('input-error-messages');
var searchForm = document.getElementById('search-form');

findContactsButton.onclick = function() {
    var errorMessages = checkInputFields();
    if (errorMessages.length > 0) {
        errorMessagesList.innerHTML = '';
        for (var i = 0; i < errorMessages.length; i++) {
            var tempMessage = document.createElement('h3');
            tempMessage.className = 'error-message';
            tempMessage.innerHTML = errorMessages[i];
            errorMessagesList.appendChild(tempMessage);
        }
        showModalForm(errorMessagesModal);
        okMessagesButton.focus();
    }
    else {
        alert('nice');
        //searchForm.submit();
    }
};

okMessagesButton.onclick = function() {
    hideModalForm(errorMessagesModal);
};

function showModalForm(form) {
    form.style.display = 'block';
}

function hideModalForm(form) {
    form.style.display = 'none';
}

window.onclick = function(event) {
    if (event.target == errorMessagesModal) {
        hideModalForm(errorMessagesModal);
    }
};

function checkInputFields() {
    var errorMessages = [];
    var error;
    if ((error = checkFirstName()) != '') {
        errorMessages.push(error);
    }
    if ((error = checkLastName()) != '') {
        errorMessages.push(error);
    }
    if ((error = checkPatronymic()) != '') {
        errorMessages.push(error);
    }
    if ((error = checkNationality()) != '') {
        errorMessages.push(error);
    }
    if ((error = checkDate()) != '') {
        errorMessages.push(error);
    }
    if ((error = checkState()) != '') {
        errorMessages.push(error);
    }
    if ((error = checkCity()) != '') {
        errorMessages.push(error);
    }
    if ((error = checkStreet()) != '') {
        errorMessages.push(error);
    }
    if ((error = checkHouse()) != '') {
        errorMessages.push(error);
    }
    if ((error = checkFlat()) != '') {
        errorMessages.push(error);
    }
    if ((error = checkZipcode()) != '') {
        errorMessages.push(error);
    }
    return errorMessages;
}

function checkFirstName() {
    var MIN_NAME_LENGTH = 3;
    var firstName = document.getElementById('first-name').value.trim();
    if (!isEmpty(firstName)) {
        if (!containsOnlyLetters(firstName)) {
            return 'Имя может содержать только буквы';
        }
        if (firstName.length < MIN_NAME_LENGTH) {
            return 'Имя должно состоять не менее, чем из ' + MIN_NAME_LENGTH + ' символов';
        }
    }
    return '';
}

function checkLastName() {
    var MIN_LAST_NAME_LENGTH = 3;
    var lastName = document.getElementById('last-name').value.trim();
    if (!isEmpty(lastName)) {
        if (!containsOnlyLetters(lastName)) {
            return 'Фамилия может содержать только буквы';
        }
        if (lastName.length < MIN_LAST_NAME_LENGTH) {
            return 'Фамилия должна состоять не менее, чем из ' + MIN_LAST_NAME_LENGTH + ' символов';
        }
    }
    return '';
}

function checkPatronymic() {
    var patronymic = document.getElementById('patronymic').value.trim();
    var MIN_PATRONYMIC_LENGTH = 5;
    if (!isEmpty(patronymic)) {
        if (!containsOnlyLetters(patronymic)) {
            return 'Отчество может содержать только буквы';
        }
        if (patronymic.length < MIN_PATRONYMIC_LENGTH) {
            return 'Отчество должно состоять не менее, чем из ' + MIN_PATRONYMIC_LENGTH + ' символов';
        }
    }
    return '';
}

function checkState() {
    var state = document.getElementById('state').value.trim();
    var STATE_SYMBOLS = "- ";
    if (!isEmpty(state)) {
        if (!containsOnlyCharsIgnoreCase(state, RU_ALPHABET_LOWER + EN_ALPHABET_LOWER + STATE_SYMBOLS)) {
            return 'Страна содержит недопустимые символы';
        }
    }
    return '';
}

function checkCity() {
    var city = document.getElementById('city').value.trim();
    var CITY_SYMBOLS = "- ";
    if (!isEmpty(city)) {
        if (!containsOnlyCharsIgnoreCase(city, RU_ALPHABET_LOWER + EN_ALPHABET_LOWER + DIGITS + CITY_SYMBOLS)) {
            return 'Город содержит недопустимые симоволы';
        }
    }
    return '';
}

function checkStreet() {
    var street = document.getElementById('street').value.trim();
    var STREET_SYMBOLS = "- ";
    if (!isEmpty(street)) {
        if (!containsOnlyCharsIgnoreCase(street, RU_ALPHABET_LOWER + EN_ALPHABET_LOWER + DIGITS + STREET_SYMBOLS)) {
            return 'Улица содержит недопустимые символы';
        }
    }
    return '';
}

function checkHouse() {
    var house = document.getElementById('house').value.trim();
    if (!isEmpty(house)) {
        if (!containsOnlyCharsIgnoreCase(house, RU_ALPHABET_LOWER + EN_ALPHABET_LOWER + DIGITS)) {
            return 'Дом содержит недопустимые символы';
        }
    }
    return '';
}

function checkFlat() {
    var flat = document.getElementById('flat').value.trim();
    if (!isEmpty(flat)) {
        if (!containsOnlyCharsIgnoreCase(flat, RU_ALPHABET_LOWER + EN_ALPHABET_LOWER + DIGITS)) {
            return 'Квартира содержит недопустимые символы';
        }
    }
    return '';
}

function checkZipcode() {
    var zipcode = document.getElementById('zipcode').value.trim();
    if (!isEmpty(zipcode)) {
        if (!containsOnlyCharsIgnoreCase(zipcode, RU_ALPHABET_LOWER + EN_ALPHABET_LOWER + DIGITS)) {
            return 'Индекс содержит недопустимые символы';
        }
    }
    return '';
}

function checkDate() {
    var MIN_YEAR = 1880;
    var MIN_CONTACT_AGE = 10;
    var MAX_YEAR = new Date().getFullYear() - MIN_CONTACT_AGE;
    var day = document.getElementById('day').value.trim();
    var month = document.getElementById('month').value.trim();
    var year = document.getElementById('year').value.trim();

    if (!isEmpty(day) || (month != 0) || !isEmpty(year)) {
        if (isEmpty(day) || (month == 0) || isEmpty(year)) {
            return 'Вы ввели дату рождения не полностью';
        }
        else {
            if (!isNumber(year)) {
                return 'Год может содержать только цифры';
            }
            if (!isNumber(day)) {
                return 'День может содержать только цифры';
            }
            if ((year < MIN_YEAR)) {
                return 'Контакт неправдоподобно стар';
            }
            if ((year > MAX_YEAR)) {
                return 'Контакту мешьше ' + MIN_CONTACT_AGE + ' лет';
            }
            var DAYS_IN_MONTH = new Date(year, month, 0).getDate();
            if ((day < 0) || (day > DAYS_IN_MONTH)) {
                return 'В указанном месяце столько дней не может быть';
            }
        }
    }
    return '';
}

function checkNationality() {
    var MIN_NATIONALITY_LENGTH = 4;
    var nationality = document.getElementById('nationality').value.trim();
    if (!isEmpty(nationality)) {
        if (nationality.length < MIN_NATIONALITY_LENGTH) {
            return 'Национальность должна содержать не менее ' + MIN_NATIONALITY_LENGTH + ' символов';
        }
        if (!containsOnlyLetters(nationality)) {
            return 'Национальность может содержать только буквы';
        }
    }
    return '';
}

var EN_ALPHABET_LOWER = "abcdefghijklmnopqrstuvwxyz";
var RU_ALPHABET_LOWER = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
var DIGITS = "1234567890";

function isNumber(value) {
    return containsOnlyCharsIgnoreCase(value, DIGITS);
}

function containsOnlyLetters(value) {
    return containsOnlyCharsIgnoreCase(value, EN_ALPHABET_LOWER + RU_ALPHABET_LOWER);
}

function containsOnlyCharsIgnoreCase(value, chars) {
    var lowerCaseChars = chars.toLowerCase();
    var lowerCaseValue = value.toLowerCase();
    for (var i = 0; i < value.length; i++) {
        if (lowerCaseChars.indexOf(lowerCaseValue.charAt(i)) == -1) {
            return false;
        }
    }
    return true;
}

function isEmpty(str) {
    return (!str || 0 === str.length);
}