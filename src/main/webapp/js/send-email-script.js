var emailPatternSelect = document.getElementById('email-pattern-select');
var patternElementSelect = document.getElementById('pattern-element-select');
var sendEmailButton = document.getElementById('send-email-button');
var okMessagesButton = document.getElementById('ok-messages-button');
var errorMessagesList = document.getElementById('input-error-messages');
var sendEmailForm = document.getElementById('send-email-form');
var errorMessagesModal = document.getElementById('error-messages-modal');

emailPatternSelect.onchange = function() {
    messageTextInput.value = emailPatternSelect.options[emailPatternSelect.selectedIndex].value;
};

var messageThemeInput = document.getElementById('message-theme');
var messageTextInput = document.getElementById('message-text');

sendEmailButton.onclick = function() {
    var errorMessages = [];
    var error;
    if ((error = checkTheme()) != '') {
        errorMessages.push(error);
    }
    if ((error = checkMessage()) != '') {
        errorMessages.push(error);
    }
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
        sendEmailForm.submit();
    }
};

function checkTheme() {
    var messageTheme = messageThemeInput.value;
    if (isEmpty(messageTheme)) {
        return 'Укажите тему сообщения';
    }
    return '';
}

function checkMessage() {
    var messageText = messageTextInput.value;
    if (isEmpty(messageText)) {
        return 'Сообщение пусто';
    }
    return '';
}

function isEmpty(str) {
    return (!str || 0 === str.length);
}

patternElementSelect.onchange = function() {
    var elementValue = patternElementSelect.value;
    patternElementSelect.value = '0';
    var index = getCaret(messageTextInput);
    var mailText = messageTextInput.value;
    mailText = mailText.substring(0, index) + '<' + elementValue + '>' + mailText.substring(index);
    messageTextInput.value = mailText;
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

function getCaret(el) {
    if (el.selectionStart) {
        return el.selectionStart;
    } else if (document.selection) {
        el.focus();

        var r = document.selection.createRange();
        if (r == null) {
            return 0;
        }

        var re = el.createTextRange(),
            rc = re.duplicate();
        re.moveToBookmark(r.getBookmark());
        rc.setEndPoint('EndToStart', re);

        return rc.text.length;
    }
    return 0;
}