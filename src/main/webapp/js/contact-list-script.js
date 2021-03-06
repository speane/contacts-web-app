var dynamicPrefix = document.getElementById('dynamic-prefix').value;

var messageModalWindow = document.getElementById('message-modal-window');
var messageTextField = document.getElementById('message-info-field');
var messageOkButton = document.getElementById('message-ok-button');

var contactListForm = document.getElementById("contact-list-form");

var deleteContactsButton = document.getElementById('delete-contact-button');
var sendEmailButton = document.getElementById('send-email-button');
var editContactButton = document.getElementById("edit-checked-contact-button");

deleteContactsButton.onclick = function() {
    if (isAnyContactSelected()) {
        contactListForm.action = dynamicPrefix + "/app/delete-contact";
        contactListForm.submit();
    }
    else {
        showInfoMessage('Сначала выберите контакты для удаления');
    }
};

editContactButton.onclick = function() {
    if (isAnyContactSelected()) {
        contactListForm.action = dynamicPrefix + "/app/edit-checked-contact";
        contactListForm.submit();
    }
    else {
        showInfoMessage('Сначала выберите контакт для редактирования')
    }
};

sendEmailButton.onclick = function() {
    if (isAnyContactSelected()) {
        contactListForm.action = dynamicPrefix + "/app/send-email";
        contactListForm.submit();
    }
    else {
        showInfoMessage('Сначала выберите контакты для оправки сообщения');
    }
};

function getCheckedItems(checkName) {
    var checkBoxes = document.getElementsByName(checkName);
    var checked = [];
    for (var i = 0; i < checkBoxes.length; i++) {
        if (checkBoxes[i].checked) {
            checked.push(checkBoxes[i].value);
        }
    }
    return checked;
}

function isAnyContactSelected() {
    var checkedContacts = getCheckedItems('contact-check');
    return checkedContacts.length > 0;
}

function showModalForm(modal) {
    modal.style.display = 'block';
}

function hideModalForm(modal) {
    modal.style.display = 'none';
}

function showInfoMessage(message) {
    messageTextField.innerHTML = message;
    showModalForm(messageModalWindow);
    messageOkButton.focus();
}

messageOkButton.onclick = function() {
    hideModalForm(messageModalWindow);
};

window.onclick = function(event) {
    if (event.target == messageModalWindow) {
        hideModalForm(messageModalWindow);
    }
};

document.addEventListener("DOMContentLoaded", function() {
    var actionMessage = document.getElementById('action-message').value;
    if (!isEmpty(actionMessage)) {
        showInfoMessage(actionMessage);
    }
});

function isEmpty(str) {
    return (!str || 0 === str.length);
}