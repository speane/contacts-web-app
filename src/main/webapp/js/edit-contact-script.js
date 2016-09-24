var phoneEditModal = document.getElementById('phone-edit-modal');

var addPhoneButton = document.getElementById('add-phone-button');

var editPhone = false;


function hideModalForm(form) {
    form.style.display = "none";
}

function showModalForm(form) {
    form.style.display = "block";
}

addPhoneButton.onclick = function() {
    editPhone = false;
    showModalForm(phoneEditModal);
};

var createdPhones = {};
var removedPhones = [];

var lastCreatedPhoneId = 0;

function createNewPhone() {
    lastCreatedPhoneId++;
    var phoneTypeSelect = document.getElementById('phone-type-select');
    var phoneTypeValue = phoneTypeSelect.options[phoneTypeSelect.selectedIndex].value;
    var phone = {
        id : lastCreatedPhoneId,
        countryCode : document.getElementById('country-code').value,
        operatorCode : document.getElementById('operator-code').value,
        number : document.getElementById('phone-number').value,
        type : phoneTypeValue,
        commentary : document.getElementById('phone-commentary').value
    };
    createdPhones[phone.id] = phone;

    var newPhone = document.createElement("div");
    newPhone.className = "row";
    newPhone.id = "created-phone-" + phone.id;

    var checkCell = document.createElement("div");
    checkCell.className = "cell-1";

    var checkbox = document.createElement("input");
    checkbox.type = "checkbox";
    checkbox.name = "created-phone-check";
    checkbox.value = phone.id;

    var checkLabel = document.createElement("label");
    newPhone.appendChild(checkCell);
    checkLabel.appendChild(checkbox);
    checkCell.appendChild(checkLabel);
    newPhone.appendChild(checkCell);

    var numberCell = document.createElement("div");
    numberCell.className = "cell-3";
    numberCell.id = "created-phone-number-" + phone.id;
    var fullNumber = '+' + phone.countryCode + '(' + phone.operatorCode + ')' + phone.number;
    var fullNumberTextNode = document.createTextNode(fullNumber);
    numberCell.appendChild(fullNumberTextNode);
    newPhone.appendChild(numberCell);

    var typeCell = document.createElement("div");
    typeCell.className = "cell-2";
    typeCell.id = "created-phone-type-" + phone.id;
    var phoneType = phoneTypeSelect.options[phoneTypeSelect.selectedIndex].text;
    var typeTextNode = document.createTextNode(phoneType);
    typeCell.appendChild(typeTextNode);

    var commentaryCell = document.createElement("div");
    commentaryCell.className = "cell-6";
    commentaryCell.id = "created-phone-commentary-" + phone.id;
    var phoneCommentaryTextNode = document.createTextNode(phone.commentary);
    commentaryCell.appendChild(phoneCommentaryTextNode);

    newPhone.appendChild(typeCell);
    newPhone.appendChild(commentaryCell);

    document.getElementById('phone-list').appendChild(newPhone);
}

var savePhoneButton = document.getElementById('save-phone-button');
savePhoneButton.onclick = function() {
    if (editPhone) {
        updatePhone();
    }
    else {
        createNewPhone()
    }
};

var editCreatedPhone = false;
var editPhoneId;

var updatedPhones = {};

function updatePhone() {
    if (editCreatedPhone) {
        createdPhones[editPhoneId].number = document.getElementById('phone-number').value;
        createdPhones[editPhoneId].countryCode = document.getElementById('country-code').value;
        createdPhones[editPhoneId].operatorCode = document.getElementById('operator-code').value;
        createdPhones[editPhoneId].type = document.getElementById('phone-type-select').value;
        createdPhones[editPhoneId].commentary = document.getElementById('phone-commentary').value;
        document.getElementById('created-phone-number-' + editPhoneId).innerHTML = '+' + document.getElementById('country-code').value + '('
            + document.getElementById('operator-code').value + ')' + document.getElementById('phone-number').value;
        document.getElementById('created-phone-type-' + editPhoneId).innerHTML = document.getElementById('phone-type-select').value;
        document.getElementById('created-phone-commentary-' + editPhoneId).innerHTML = document.getElementById('phone-commentary').value;
    }
    else {
        var phoneTypeSelect = document.getElementById('phone-type-select');
        var phoneTypeValue = phoneTypeSelect.options[phoneTypeSelect.selectedIndex].value;
        var phone = {
            id : lastCreatedPhoneId,
            countryCode : document.getElementById('country-code').value,
            operatorCode : document.getElementById('operator-code').value,
            number : document.getElementById('phone-number').value,
            type : phoneTypeValue,
            commentary : document.getElementById('phone-commentary').value
        };
        updatedPhones[editPhoneId] = phone;
        document.getElementById('phone-number-' + editPhoneId).innerHTML = '+' + document.getElementById('country-code').value + '('
            + document.getElementById('operator-code').value + ')' + document.getElementById('phone-number').value;
        document.getElementById('phone-type-' + editPhoneId).innerHTML = phone.type;
        document.getElementById('phone-commentary-' + editPhoneId).innerHTML = document.getElementById('phone-commentary').value;
    }

}

window.onclick = function(event) {
    if (event.target == phoneEditModal) {
        hideModalForm(phoneEditModal);
    }
    else if (event.target == attachmentEditModal) {
        hideModalForm(attachmentEditModal);
    } else if (event.target == selectPhotoModal) {
        hideModalForm(selectPhotoModal);
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
        removedPhones.push(checkedPhones[i]);
    }
    var createdCheckedPhones = getCheckedItems('created-phone-check');

    for (var i = 0; i < createdCheckedPhones.length; i++) {
        var deletePhone = document.getElementById('created-phone-' + createdCheckedPhones[i]);
        deletePhone.parentNode.removeChild(deletePhone);
        delete createdPhones[createdCheckedPhones[i]];
    }
};

document.getElementById('edit-phone-button').onclick = function() {
    editPhone = true;
    var checkedPhones = getCheckedItems('phone-check');

    if (checkedPhones.length > 0) {
        editPhoneId = checkedPhones[0];
        var phoneNumber = document.getElementById('phone-number-' + editPhoneId).innerHTML;
        document.getElementById('phone-number').value = phoneNumber.split(')', 2)[1];
        document.getElementById('country-code').value = phoneNumber.split('+', 2)[1].split('(', 2)[0];
        document.getElementById('operator-code').value = phoneNumber.split(')', 2)[0].split('(', 2)[1];
        document.getElementById('phone-type-select').value = document.getElementById('phone-type-' + editPhoneId).innerHTML.trim();
        document.getElementById('phone-commentary').value = document.getElementById('phone-commentary-' + editPhoneId).innerHTML.trim();
        editCreatedPhone = false;

        showModalForm(phoneEditModal);
    }
    else {
        var checkedCreatedPhones = getCheckedItems("created-phone-check");
        if (checkedCreatedPhones.length > 0) {
            editPhoneId = checkedCreatedPhones[0];
            var phoneNumber = document.getElementById('created-phone-number-' + editPhoneId).innerHTML;
            document.getElementById('phone-number').value = phoneNumber.split(')', 2)[1];
            document.getElementById('country-code').value = phoneNumber.split('+', 2)[1].split('(', 2)[0];
            document.getElementById('operator-code').value = phoneNumber.split(')', 2)[0].split('(', 2)[1];
            document.getElementById('phone-type-select').value = document.getElementById('created-phone-type-' + editPhoneId).innerHTML.trim();
            document.getElementById('phone-commentary').value = document.getElementById('created-phone-commentary-' + editPhoneId).innerHTML.trim();
            showModalForm(phoneEditModal);
            editCreatedPhone = true;
        }
    }
};

document.getElementById('cancel-phone-edit-button').onclick = function() {
    hideModalForm(phoneEditModal);
};


var attachmentFileNameField = document.getElementById('attachment-file-name');
var attachmentCommentaryField = document.getElementById('attachment-commentary');

var editAttachment;
var editCreatedAttachment;
var editAttachmentId;

var lastAttachmentId = 0;
var createdAttachments = {};
var removedAttachments = [];
var updatedAttachments = {};

var attachmentList = document.getElementById('attachment-list');
var attachmentEditModal = document.getElementById('attachment-edit-modal');
var addAttachmentButton = document.getElementById('add-attachment-button');
var editAttachmentButton = document.getElementById('edit-attachment-button');
var removeAttachmentButton = document.getElementById('remove-attachment-button');
var saveAttachmentButton = document.getElementById('save-attachment-button');
var cancelAttachmentButton = document.getElementById('cancel-attachment-edit-button');
var attachmentForm = document.getElementById('attachment-form');
var attachmentFileInput = document.getElementById('attachment-file-input');

addAttachmentButton.onclick = function() {
    editAttachment = false;
    clearAttachmentFields();
    attachmentFileInput.style.display = 'block';
    showModalForm(attachmentEditModal);
};

function clearAttachmentFields() {
    attachmentFileNameField.value = '';
    attachmentCommentaryField.value = '';
}

editAttachmentButton.onclick = function() {
    editAttachment = true;
    setAttachmentEditParameters();
    attachmentFileInput.style.display = 'none';
    if (editAttachmentId != -1) {
        setAttachmentFields();
        showModalForm(attachmentEditModal);
    }
};

function setAttachmentEditParameters() {
    editAttachmentId = -1;
    var checkedAttachments = getCheckedItems('attachment-check');
    if (checkedAttachments.length > 0) {
        editCreatedAttachment = false;
        editAttachmentId = checkedAttachments[0];
    } else {
        var checkedCreatedAttachments = getCheckedItems('created-attachment-check');
        if (checkedCreatedAttachments.length > 0) {
            editCreatedAttachment = true;
            editAttachmentId = checkedCreatedAttachments[0];
        }
    }
}

function setAttachmentFields() {
    var attachmentPrefix;
    attachmentPrefix = editCreatedAttachment ? 'created-attachment-' : 'attachment-';
    attachmentFileNameField.value = document.getElementById(attachmentPrefix + 'filename-' + editAttachmentId).innerHTML.trim();
    attachmentCommentaryField.value = document.getElementById(attachmentPrefix + 'commentary-' + editAttachmentId).innerHTML.trim();
}

removeAttachmentButton.onclick = function() {
    var checkedAttachments = getCheckedItems('attachment-check');
    var checkedCreatedAttachments = getCheckedItems('created-attachment-check');
    for (var i = 0; i < checkedAttachments.length; i++) {
        removeAttachment(checkedAttachments[i]);
    }
    for (var i = 0; i < checkedCreatedAttachments.length; i++) {
        removeCreatedAttachment(checkedCreatedAttachments[i]);
    }
};

function removeAttachment(id) {
    removedAttachments.push(id);
    var attachmentRow = document.getElementById('attachment-' + id);
    attachmentList.removeChild(attachmentRow);
}

function removeCreatedAttachment(id) {
    delete createdAttachments[id];
    var createdAttachmentRow = document.getElementById('created-attachment-' + id);
    attachmentList.removeChild(createdAttachmentRow);
}

saveAttachmentButton.onclick = function() {
    if (editAttachment) {
        updateAttachment();
    }
    else {
        createAttachment();
    }
};

function createAttachment() {
    lastAttachmentId++;
    var attachment = {
        id : lastAttachmentId,
        filename : attachmentFileNameField.value,
        commentary : attachmentCommentaryField.value,
        uploadDate : getDateString()
    };
    createdAttachments[lastAttachmentId] = attachment;
    addAttachmentToList(attachment);
    createAttachmentFileInput();
}

function addAttachmentToList(attachment) {
    var attachmentRow = document.createElement('div');
    attachmentRow.className = 'row';
    attachmentRow.id = 'created-attachment-' + attachment.id;

    var attachmentCheckBoxCell = document.createElement('div');
    attachmentCheckBoxCell.className = 'cell-1';
    var attachmentCheckBoxLabel = document.createElement('label');
    var attachmentCheckBox = document.createElement('input');
    attachmentCheckBox.type = 'checkbox';
    attachmentCheckBox.name = 'created-attachment-check';
    attachmentCheckBox.value = attachment.id;
    attachmentCheckBoxLabel.appendChild(attachmentCheckBox);
    attachmentCheckBoxCell.appendChild(attachmentCheckBoxLabel);

    var attachmentFileNameCell = document.createElement('div');
    attachmentFileNameCell.className = 'cell-3';
    attachmentFileNameCell.id = 'created-attachment-filename-' + attachment.id;
    attachmentFileNameCell.innerHTML = attachment.filename;

    var attachmentUploadDateCell = document.createElement('div');
    attachmentUploadDateCell.className = 'cell-2';
    attachmentUploadDateCell.id = 'created-attachment-upload-date-' + attachment.id;
    attachmentUploadDateCell.innerHTML = attachment.uploadDate;

    var attachmentCommentaryCell = document.createElement('div');
    attachmentCommentaryCell.className = 'cell-6';
    attachmentCommentaryCell.id = 'created-attachment-commentary-' + attachment.id;
    attachmentCommentaryCell.innerHTML = attachment.commentary;

    attachmentFileInput.id = 'created-attachment-file-input-' + attachment.id;
    attachmentFileInput.style.display = 'block';
    attachmentRow.appendChild(attachmentFileInput);

    attachmentRow.appendChild(attachmentCheckBoxCell);
    attachmentRow.appendChild(attachmentFileNameCell);
    attachmentRow.appendChild(attachmentUploadDateCell);
    attachmentRow.appendChild(attachmentCommentaryCell);
    attachmentRow.appendChild(attachmentFileInput);

    attachmentList.appendChild(attachmentRow);
}

function createAttachmentFileInput() {
    var newAttachmentFileInput = document.createElement('input');
    newAttachmentFileInput.type = 'file';
    newAttachmentFileInput.style.display = 'block';
    newAttachmentFileInput.id = 'attachment-file-input';
    attachmentFileInput = newAttachmentFileInput;
    attachmentForm.insertBefore(newAttachmentFileInput, saveAttachmentButton);
}

function getDateString() {
    var date = new Date();
    var day = date.getDate();
    var month = date.getMonth() + 1;
    var year = date.getFullYear();
    return year + '-' + month + '-' + day;
}

function updateAttachment() {
    var attachment = {
        id : editAttachmentId,
        filename : attachmentFileNameField.value,
        commentary : attachmentCommentaryField.value,
        uploadDate : getDateString()
    };
    var attachmentPrefix;
    if (editCreatedAttachment) {
        createdAttachments[attachment.id] = attachment;
        attachmentPrefix = 'created-attachment-';
    }
    else {
        updatedAttachments[attachment.id] = attachment;
        attachmentPrefix = 'attachment-';
    }
    updateAttachmentRow(attachmentPrefix, attachment);
}

function updateAttachmentRow(attachmentPrefix, attachment) {
    var attachmentFileNameDiv = document.getElementById(attachmentPrefix + 'filename-' + attachment.id);
    attachmentFileNameDiv.innerHTML = attachment.filename;
    var attachmentUploadDateDiv = document.getElementById(attachmentPrefix + 'upload-date-' + attachment.id);
    attachmentUploadDateDiv.innerHTML = attachment.uploadDate;
    var attachmentCommentaryDiv = document.getElementById(attachmentPrefix + 'commentary-' + attachment.id);
    attachmentCommentaryDiv.innerHTML = attachment.commentary;
}

cancelAttachmentButton.onclick = function() {
    hideModalForm(attachmentEditModal);
};

var photoFileInput = document.getElementById('photo-file-input');
var savePhotoButton = document.getElementById('save-photo-button');
var contactPhotoImage = document.getElementById('contact-photo-image');
var contactPhotoSelectArea = document.getElementById('contact-photo-select-area');
var selectPhotoModal = document.getElementById('select-photo-modal');
var cancelPhotoSelectButton = document.getElementById('cancel-photo-select-button');
var clearPhotoButton = document.getElementById('clear-photo-button');
var uploadedContactPhotoFileInput = document.getElementById('uploaded-contact-photo');
var photoSelectForm = document.getElementById('photo-select-form');

contactPhotoSelectArea.onclick = function() {
    showModalForm(selectPhotoModal);
};

savePhotoButton.onclick = function() {
    var fileReader = new FileReader;
    var imageFile = photoFileInput.files[0];
    fileReader.onload = function() {
        contactPhotoImage.src = this.result;
    };
    if (imageFile) {
        fileReader.readAsDataURL(imageFile);
    }
    uploadedContactPhotoFileInput.parentNode.removeChild(uploadedContactPhotoFileInput);
    contactPhotoSelectArea.appendChild(photoFileInput);
    photoFileInput.id = 'uploaded-contact-photo';
    uploadedContactPhotoFileInput = photoFileInput;
    photoFileInput = createPhotoFileInput();
    photoSelectForm.insertBefore(photoFileInput, savePhotoButton);
};

function createPhotoFileInput() {
    var newPhotoFileInput = document.createElement('input');
    newPhotoFileInput.type = 'file';
    newPhotoFileInput.id = 'photo-file-input';
    return newPhotoFileInput;
}

cancelPhotoSelectButton.onclick = function() {
    hideModalForm(selectPhotoModal);
};

clearPhotoButton.onclick = function() {
    contactPhotoImage.src = '/images/default.png';
};


