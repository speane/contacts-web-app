var phoneEditModal = document.getElementById('phone-edit-modal');

var addPhoneButton = document.getElementById('add-phone-button');

function hideModalForm(form) {
    form.style.display = "none";
}

function showModalForm(form) {
    form.style.display = "block";
}

addPhoneButton.onclick = function() {
    showModalForm(phoneEditModal);
};

var createdPhones = [];

var savePhoneButton = document.getElementById('save-phone-button');
savePhoneButton.onclick = function() {
    var phone = {
        countryCode : document.getElementById('country-code').value,
        operatorCode : document.getElementById('operator-code').value,
        number : document.getElementById('phone-number').value,
        type : document.getElementById('phone-type').value,
        commentary : document.getElementById('phone-commentary').value
    };
    createdPhones.push(phone);
    var newPhone = document.createElement("div");
    newPhone.className = "row";
    var checkCell = document.createElement("div");
    checkCell.className = "cell-1";
    var checkbox = document.createElement("input");
    checkbox.type = "checkbox";
    var checkLabel = document.createElement("label");
    newPhone.appendChild(checkCell);
    checkLabel.appendChild(checkbox);
    checkCell.appendChild(checkLabel);
    newPhone.appendChild(checkCell);

    var numberCell = document.createElement("div");
    numberCell.className = "cell-3";
    var fullNumber = '+' + phone.countryCode + '(' + phone.operatorCode + ')' + phone.number;
    var fullNumberTextNode = document.createTextNode(fullNumber);
    numberCell.appendChild(fullNumberTextNode);
    newPhone.appendChild(numberCell);
    document.getElementById('phone-list').appendChild(newPhone);
};

var attachmentEditModal = document.getElementById('attachment-edit-modal');

var addAttachmentButton = document.getElementById('add-attachment-button');
addAttachmentButton.onclick = function() {
    showModalForm(attachmentEditModal);
};

var photoEditButton = document.getElementById('photo-edit-image');
var choosePhotoModal = document.getElementById('choose-photo-modal');
photoEditButton.onclick = function() {
    showModalForm(choosePhotoModal);
};

window.onclick = function(event) {
    if (event.target == phoneEditModal) {
        hideModalForm(phoneEditModal);
    }
    else if (event.target == attachmentEditModal) {
        hideModalForm(attachmentEditModal);
    } else if (event.target == choosePhotoModal) {
        hideModalForm(choosePhotoModal);
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

document.getElementById('remove-phone-button').onclick = function() {
    var checkedPhones = getCheckedItems('phone-check');
    for (var i = 0; i < checkedPhones.length; i++) {
        var deletePhone = document.getElementById('contact-phone-' + checkedPhones[i]);
        deletePhone.parentNode.removeChild(deletePhone);
    }
};

document.getElementById('edit-phone-button').onclick = function() {
    var checkedPhones = getCheckedItems('phone-check');

    if (checkedPhones.length > 0) {
        var editPhoneId = checkedPhones[0];
        var phoneNumber = document.getElementById('phone-number-' + editPhoneId).innerHTML;
        document.getElementById('phone-number').value = phoneNumber.split(')', 2)[1];
        document.getElementById('country-code').value = phoneNumber.split('+', 2)[1].split('(', 2)[0];
        document.getElementById('operator-code').value = phoneNumber.split(')', 2)[0].split('(', 2)[1];
        document.getElementById('phone-type').value = document.getElementById('phone-type-' + editPhoneId).innerHTML.trim();
        document.getElementById('phone-commentary').value = document.getElementById('phone-commentary-' + editPhoneId).innerHTML.trim();
        showModalForm(phoneEditModal);
    }
};