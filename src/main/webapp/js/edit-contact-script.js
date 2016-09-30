(function () {
    var phoneEditModal = document.getElementById('phone-edit-modal');

    var addPhoneButton = document.getElementById('add-phone-button');

    var editPhone = false;


    function hideModalForm(form) {
        form.style.display = "none";
    }

    function showModalForm(form) {
        form.style.display = "block";
    }

    addPhoneButton.onclick = function () {
        editPhone = false;
        clearPhoneInputFields();
        showModalForm(phoneEditModal);
    };

    var countryCodeField = document.getElementById('country-code');
    var operatorCodeField = document.getElementById('operator-code');
    var numberField = document.getElementById('phone-number');
    var phoneTypeSelect = document.getElementById('phone-type-select');
    var phoneCommentary = document.getElementById('phone-commentary');

    function clearPhoneInputFields() {
        countryCodeField.value = '';
        operatorCodeField.value = '';
        numberField.value = '';
        phoneTypeSelect.value = phoneTypeSelect[1].value;
        phoneCommentary.value = '';
    }

    var createdPhones = {};
    var removedPhones = [];

    var lastCreatedPhoneId = 0;

    function createNewPhone() {
        lastCreatedPhoneId++;
        var phoneTypeSelect = document.getElementById('phone-type-select');
        var phoneTypeValue = phoneTypeSelect.options[phoneTypeSelect.selectedIndex].value;
        var phone = {
            id: lastCreatedPhoneId,
            countryCode: document.getElementById('country-code').value.trim(),
            operatorCode: document.getElementById('operator-code').value.trim(),
            number: document.getElementById('phone-number').value.trim(),
            type: phoneTypeValue,
            commentary: document.getElementById('phone-commentary').value.trim()
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
    savePhoneButton.onclick = function () {
        var errorMessages = checkPhoneFields();
        if (errorMessages.length > 0) {
            showErrorMessages(errorMessages);
        }
        else {
            if (editPhone) {
                updatePhone();
            }
            else {
                createNewPhone()
            }
        }
    };

    function showErrorMessages(messages) {
        inputMessages.innerHTML = '';
        for (var i = 0; i < messages.length; i++) {
            var errorMessage = document.createElement('h3');
            errorMessage.className = 'error-message';
            errorMessage.innerHTML = messages[i];
            inputMessages.appendChild(errorMessage);
        }
        messageWindow.style.zIndex = 5;
        showModalForm(messageWindow);
    }

    function checkPhoneFields() {
        var errorMessages = [];
        var error;
        if ((error = checkNumberField(countryCodeField, 'Код страны пуст',
                'Код страны может содержать только цифры')) != '') {
            errorMessages.push(error);
        }
        if ((error = checkNumberField(operatorCodeField, 'Код оператора пуст',
                'Код оператора может содержать только цифры')) != '') {
            errorMessages.push(error);
        }
        if ((error = checkNumberField(numberField, 'Номер телефона не указан',
                'Номер телефона может содержать только цифры')) != '') {
            errorMessages.push(error);
        }
        if ((error = checkCommentary(phoneCommentary)) != '') {
            errorMessages.push(error);
        }
        return errorMessages;
    }

    function checkNumberField(field, emptyErrorMessage, notANumberErrorMessage) {
        var fieldValue = field.value;
        if (isEmpty(fieldValue)) {
            return emptyErrorMessage;
        }
        if (!isNumber(fieldValue)) {
            return notANumberErrorMessage;
        }
        return '';
    }

    function checkCommentary(commentaryField) {
        var commentary = commentaryField.value;
        var COMMENTARY_SYMBOLS = '+= /\\*,.!@#%^&()_-{}<>';
        if (!isEmpty(commentary)) {
            if (!containsOnlyCharsIgnoreCase(commentary, RU_ALPHABET_LOWER + EN_ALPHABET_LOWER + DIGITS + COMMENTARY_SYMBOLS)) {
                return 'Комментарий содержит недопустимые символы';
            }
        }
        return '';
    }

    function isAnyItemSelected(name) {
        var items = getCheckedItems(name);
        return items.length > 0;
    }

    var editCreatedPhone = false;
    var editPhoneId;

    var updatedPhones = {};

    function updatePhone() {
        if (editCreatedPhone) {
            createdPhones[editPhoneId].number = document.getElementById('phone-number').value.trim();
            createdPhones[editPhoneId].countryCode = document.getElementById('country-code').value.trim();
            createdPhones[editPhoneId].operatorCode = document.getElementById('operator-code').value.trim();
            createdPhones[editPhoneId].type = document.getElementById('phone-type-select').value;
            createdPhones[editPhoneId].commentary = document.getElementById('phone-commentary').value.trim();
            document.getElementById('created-phone-number-' + editPhoneId).innerHTML = '+' + document.getElementById('country-code').value.trim() + '('
                + document.getElementById('operator-code').value + ')' + document.getElementById('phone-number').value.trim();
            document.getElementById('created-phone-type-' + editPhoneId).innerHTML = document.getElementById('phone-type-select').value;
            document.getElementById('created-phone-commentary-' + editPhoneId).innerHTML = document.getElementById('phone-commentary').value.trim();
        }
        else {
            var phoneTypeSelect = document.getElementById('phone-type-select');
            var phoneTypeValue = phoneTypeSelect.options[phoneTypeSelect.selectedIndex].value;
            var phone = {
                id: editPhoneId,
                countryCode: document.getElementById('country-code').value.trim(),
                operatorCode: document.getElementById('operator-code').value.trim(),
                number: document.getElementById('phone-number').value.trim(),
                type: phoneTypeValue,
                commentary: document.getElementById('phone-commentary').value.trim()
            };
            updatedPhones[editPhoneId] = phone;
            document.getElementById('phone-number-' + editPhoneId).innerHTML = '+' + document.getElementById('country-code').value + '('
                + document.getElementById('operator-code').value + ')' + document.getElementById('phone-number').value;
            document.getElementById('phone-type-' + editPhoneId).innerHTML = phone.type;
            document.getElementById('phone-commentary-' + editPhoneId).innerHTML = document.getElementById('phone-commentary').value;
        }

    }

    window.onclick = function (event) {
        if (event.target == phoneEditModal) {
            hideModalForm(phoneEditModal);
        }
        else if (event.target == attachmentEditModal) {
            hideModalForm(attachmentEditModal);
        }
        else if (event.target == selectPhotoModal) {
            hideModalForm(selectPhotoModal);
        }
        else if (event.target == messageWindow) {
            hideModalForm(messageWindow);
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

    document.getElementById('remove-phone-button').onclick = function () {
        if (isAnyItemSelected('phone-check')) {
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
        }
        else {
            showMessage('Ни один телефон не выбран');
        }
    };

    function showMessage(message) {
        inputMessages.innerHTML = '';
        var errorMessage = document.createElement('h3');
        errorMessage.className = 'error-message';
        errorMessage.innerHTML = message;
        inputMessages.appendChild(errorMessage);
        messageWindow.style.zIndex = 5;
        showModalForm(messageWindow);
    }

    document.getElementById('edit-phone-button').onclick = function () {
        editPhone = true;
        var checkedPhones = getCheckedItems('phone-check');

        if (checkedPhones.length > 0) {
            editPhoneId = checkedPhones[0];
            var phoneNumber = document.getElementById('phone-number-' + editPhoneId).innerHTML.trim();
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
            else {
                showMessage('Ни один телефон не выбран');
            }
        }
    };

    document.getElementById('cancel-phone-edit-button').onclick = function () {
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

    addAttachmentButton.onclick = function () {
        editAttachment = false;
        clearAttachmentFields();
        attachmentFileInput.style.display = 'block';
        showModalForm(attachmentEditModal);
    };

    function clearAttachmentFields() {
        attachmentFileNameField.value = '';
        attachmentCommentaryField.value = '';
    }

    editAttachmentButton.onclick = function () {
        editAttachment = true;
        setAttachmentEditParameters();
        attachmentFileInput.style.display = 'none';
        if (editAttachmentId != -1) {
            setAttachmentFields();
            showModalForm(attachmentEditModal);
        }
        else {
            showMessage('Ни одно присоединение не выбрано');
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

    removeAttachmentButton.onclick = function () {
        var checkedAttachments = getCheckedItems('attachment-check');
        var checkedCreatedAttachments = getCheckedItems('created-attachment-check');
        if ((checkedAttachments.length > 0) || (checkedCreatedAttachments.length > 0)) {
            for (var i = 0; i < checkedAttachments.length; i++) {
                removeAttachment(checkedAttachments[i]);
            }
            for (var i = 0; i < checkedCreatedAttachments.length; i++) {
                removeCreatedAttachment(checkedCreatedAttachments[i]);
            }
        }
        else {
            showMessage('Ни одно присоединения не выбрано')
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

    saveAttachmentButton.onclick = function () {
        var errorMessages = checkAttachmentFields();
        if (errorMessages.length > 0) {
            showErrorMessages(errorMessages);
        }
        else {
            if (editAttachment) {
                updateAttachment();
            }
            else {
                createAttachment();
            }
        }
    };

    function checkAttachmentFields() {
        var errorMessages = [];
        var error;
        if ((error = checkFileName()) != '') {
            errorMessages.push(error);
        }
        if ((error = checkCommentary(attachmentCommentaryField)) != '') {
            errorMessages.push(error);
        }
        if ((error = checkAttachmentFile()) != '') {
            errorMessages.push(error);
        }
        return errorMessages;
    }

    function checkFileName() {
        var filename = attachmentFileNameField.value;
        var FILENAME_SYMBOLS = '-_.';
        if (isEmpty(filename)) {
            return 'Укажите имя файла присоединения';
        }
        if (!containsOnlyCharsIgnoreCase(filename, EN_ALPHABET_LOWER + RU_ALPHABET_LOWER + FILENAME_SYMBOLS + DIGITS)) {
            return 'Имя файла содержит недопустимые символы';
        }
        return '';
    }

    function checkAttachmentFile() {
        var attachmentFile = attachmentFileInput.files[0];
        var MAX_ATTACHMENT_SIZE_MB = 50;
        var MAX_ATTACHMENT_SIZE = MAX_ATTACHMENT_SIZE_MB * 1024 * 1024;
        if (!attachmentFile) {
            return 'Файл присоединения не выбран';
        }
        if (attachmentFile.size > MAX_ATTACHMENT_SIZE) {
            return 'Файл присоединения должен быть не более ' + MAX_ATTACHMENT_SIZE_MB + ' МБ';
        }
        return '';
    }

    function createAttachment() {
        lastAttachmentId++;
        var attachment = {
            id: lastAttachmentId,
            filename: attachmentFileNameField.value.trim(),
            commentary: attachmentCommentaryField.value.trim(),
            uploadDate: getDateString()
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
        attachmentFileInput.name = 'attachment-' + attachment.id;
        attachmentFileInput.style.display = 'none';
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
        newAttachmentFileInput.className = "centered block";
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
            id: editAttachmentId,
            filename: attachmentFileNameField.value.trim(),
            commentary: attachmentCommentaryField.value.trim(),
            uploadDate: getDateString()
        };
        var attachmentPrefix;
        if (editCreatedAttachment) {
            attachmentPrefix = 'created-attachment-';
            createdAttachments[attachment.id] = attachment;
        }
        else {
            attachmentPrefix = 'attachment-';
            updatedAttachments[attachment.id] = attachment;
        }
        attachment.uploadDate = document.getElementById(attachmentPrefix + 'upload-date-' + attachment.id).innerHTML.trim();
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

    cancelAttachmentButton.onclick = function () {
        hideModalForm(attachmentEditModal);
    };

    var photoFileInput = document.getElementById('photo-file-input');
    var savePhotoButton = document.getElementById('save-photo-button');
    var contactPhotoImage = document.getElementById('contact-photo-image');
    var contactPhotoSelectArea = document.getElementById('contact-photo-select-area');
    var selectPhotoModal = document.getElementById('select-photo-modal');
    var cancelPhotoSelectButton = document.getElementById('cancel-photo-select-button');
    var uploadedContactPhotoFileInput = document.getElementById('uploaded-contact-photo');
    var photoSelectForm = document.getElementById('photo-select-form');
    var applyButtons = document.getElementById('apply-buttons');

    contactPhotoSelectArea.onclick = function () {
        photoFileInput.value = '';
        showModalForm(selectPhotoModal);
    };

    savePhotoButton.onclick = function () {
        var fileReader = new FileReader;
        var imageFile = photoFileInput.files[0];
        var MAX_IMAGE_SIZE_MB = 10;
        var MAX_IMAGE_SIZE = MAX_IMAGE_SIZE_MB * 1024 * 1024;
        fileReader.onload = function () {
            contactPhotoImage.src = this.result;
        };
        if (imageFile) {
            if (imageFile.size > MAX_IMAGE_SIZE) {
                showMessage('Размер фото не может превышать ' + MAX_IMAGE_SIZE_MB + ' МБ');
            }
            else {
                fileReader.readAsDataURL(imageFile);
                uploadedContactPhotoFileInput.parentNode.removeChild(uploadedContactPhotoFileInput);
                photoFileInput.id = 'uploaded-contact-photo';
                photoFileInput.name = 'upload-photo';
                photoFileInput.style.display = 'none';
                contactPhotoSelectArea.appendChild(photoFileInput);
                uploadedContactPhotoFileInput = photoFileInput;
                photoFileInput = createPhotoFileInput();
                photoSelectForm.insertBefore(photoFileInput, applyButtons);
            }
        }
        else {
            inputMessages.innerHTML = '';
            var errorMessage = document.createElement('h3');
            errorMessage.className = 'error-message';
            errorMessage.innerHTML = 'Сначала выберите фото';
            inputMessages.appendChild(errorMessage);
            messageWindow.style.zIndex = 5;
            showModalForm(messageWindow);
        }
    };

    function createPhotoFileInput() {
        var newPhotoFileInput = document.createElement('input');
        newPhotoFileInput.type = 'file';
        newPhotoFileInput.id = 'photo-file-input';
        newPhotoFileInput.accept = 'image/jpeg,image/png,image/gif';
        newPhotoFileInput.className = "centered block";
        return newPhotoFileInput;
    }

    cancelPhotoSelectButton.onclick = function () {
        hideModalForm(selectPhotoModal);
    };

    var contactForm = document.getElementById('contact-form');
    var submitContactButton = document.getElementById('save-contact-button');

    var messageWindow = document.getElementById('input-message-window');
    var inputMessages = document.getElementById('input-messages');
    var okMessageWindowButton = document.getElementById('message-ok-button');

    okMessageWindowButton.onclick = function () {
        hideModalForm(messageWindow);
    };

    submitContactButton.onclick = function () {
        var errors = validateFormFields();
        if (errors.length > 0) {
            inputMessages.innerHTML = '';
            for (var i = 0; i < errors.length; i++) {
                var tempHeader = document.createElement("h3");
                tempHeader.className = "error-message";
                tempHeader.innerHTML = errors[i];
                inputMessages.appendChild(tempHeader);
            }
            showModalForm(messageWindow);
        }
        else {
            contactForm.appendChild(createHiddenCreatedPhonesField());
            contactForm.appendChild(createHiddenCreatedAttachmentsField());
            contactForm.appendChild(createHiddenInput('removed-phones', JSON.stringify(removedPhones)));
            contactForm.appendChild(createHiddenInput('removed-attachments', JSON.stringify(removedAttachments)));
            contactForm.appendChild(createHiddenInput('updated-phones', JSON.stringify(getValuesFromAssociativeArray(updatedPhones))));
            contactForm.appendChild(createHiddenInput('updated-attachments', JSON.stringify(getValuesFromAssociativeArray(updatedAttachments))));
            contactForm.submit();
        }
    };

    function validateFormFields() {
        var errorMessages = [];
        var message;
        if ((message = checkFirstName()) != '') {
            errorMessages.push(message);
        }
        if ((message = checkLastName()) != '') {
            errorMessages.push(message);
        }
        if ((message = checkPatronymic()) != '') {
            errorMessages.push(message);
        }
        if ((message = checkDate()) != '') {
            errorMessages.push(message);
        }
        if ((message = checkNationality()) != '') {
            errorMessages.push(message);
        }
        if ((message = checkWebsite()) != '') {
            errorMessages.push(message);
        }
        if ((message = checkJob()) != '') {
            errorMessages.push(message);
        }
        if ((message = checkEmail()) != '') {
            errorMessages.push(message);
        }
        if ((message = checkState()) != '') {
            errorMessages.push(message);
        }
        if ((message = checkCity()) != '') {
            errorMessages.push(message);
        }
        if ((message = checkStreet()) != '') {
            errorMessages.push(message);
        }
        if ((message = checkHouse()) != '') {
            errorMessages.push(message);
        }
        if ((message = checkFlat()) != '') {
            errorMessages.push(message);
        }
        if ((message = checkZipcode()) != '') {
            errorMessages.push(message);
        }
        return errorMessages;
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

    function checkJob() {
        var MIN_JOB_LENGTH = 2;
        var JOB_SYMBOLS = ":-. \"'";
        var job = document.getElementById('job').value.trim();
        if (!isEmpty(job)) {
            if (job.length < MIN_JOB_LENGTH) {
                return 'Место работы не может содержать менее ' + MIN_JOB_LENGTH + ' символов';
            }
            if (!containsOnlyCharsIgnoreCase(job, EN_ALPHABET_LOWER + RU_ALPHABET_LOWER + DIGITS + JOB_SYMBOLS)) {
                return 'Место работы содержит недопустимые символы';
            }
        }
        return '';
    }

    function checkFirstName() {
        var MIN_NAME_LENGTH = 3;
        var firstName = document.getElementById('first-name').value.trim();
        if (isEmpty(firstName)) {
            return 'Вы не ввели имя';
        }
        if (!containsOnlyLetters(firstName)) {
            return 'Имя может содержать только буквы';
        }
        if (firstName.length < MIN_NAME_LENGTH) {
            return 'Имя должно состоять не менее, чем из ' + MIN_NAME_LENGTH + ' символов';
        }
        return '';
    }

    function checkLastName() {
        var MIN_LAST_NAME_LENGTH = 3;
        var lastName = document.getElementById('last-name').value.trim();
        if (isEmpty(lastName)) {
            return 'Вы не ввели фамилию';
        }
        if (!containsOnlyLetters(lastName)) {
            return 'Фамилия может содержать только буквы';
        }
        if (lastName.length < MIN_LAST_NAME_LENGTH) {
            return 'Фамилия должна состоять не менее, чем из ' + MIN_LAST_NAME_LENGTH + ' символов';
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

    function checkWebsite() {
        var URL_SYMBOLS = ":/.?=&";
        var MIN_WEBSITE_LENGTH = 3;
        var website = document.getElementById('website').value.trim();
        if (!isEmpty(website)) {
            if (website.length < MIN_WEBSITE_LENGTH) {
                return 'Website должен содержать не менее ' + MIN_WEBSITE_LENGTH + ' символов';
            }
            if (!containsOnlyCharsIgnoreCase(website, EN_ALPHABET_LOWER + RU_ALPHABET_LOWER + URL_SYMBOLS)) {
                return 'Website содержит недопустимые символы';
            }
            var DOT_INDEX = website.indexOf('.');
            if ((DOT_INDEX == -1) || (DOT_INDEX < 2) || (DOT_INDEX >= website.length)) {
                return 'Website имеет некорректный формат';
            }
        }
        return '';
    }

    function checkEmail() {
        var EMAIL_SYMBOLS = "-@.";
        var MIN_EMAIL_LENGTH = 3;
        var email = document.getElementById('email').value.trim();
        if (!isEmpty(email)) {
            if (email.length < MIN_EMAIL_LENGTH) {
                return 'Email должен содержать не менее ' + MIN_EMAIL_LENGTH + ' символов';
            }
            if (!containsOnlyCharsIgnoreCase(email, EN_ALPHABET_LOWER + RU_ALPHABET_LOWER + DIGITS + EMAIL_SYMBOLS)) {
                return 'Email содержит недопустимые символы';
            }
            var AT_INDEX = email.indexOf('@');
            if ((AT_INDEX == -1) || (AT_INDEX < 2) || (AT_INDEX >= email.length)) {
                return 'Email имеет некорректный формат';
            }
        }
        return '';
    }

    function isEmpty(str) {
        return (!str || 0 === str.length);
    }

    function createHiddenInput(name, value) {
        var field = document.createElement('input');
        field.type = 'hidden';
        field.name = name;
        field.value = value;
        return field;
    }

    function createHiddenCreatedPhonesField() {
        var field = document.createElement('input');
        field.type = 'hidden';
        field.name = 'created-phones';
        field.value = JSON.stringify(getValuesFromAssociativeArray(createdPhones));
        return field;
    }

    function getValuesFromAssociativeArray(associativeArray) {
        var values = [];
        for (var key in associativeArray) {
            if (associativeArray.hasOwnProperty(key)) {
                values.push(associativeArray[key]);
            }
        }
        return values;
    }

    function createHiddenCreatedAttachmentsField() {
        var field = document.createElement('input');
        field.type = 'hidden';
        field.name = 'created-attachments';
        field.value = JSON.stringify(getValuesFromAssociativeArray(createdAttachments));
        return field;
    }

    /* VALIDATION */

    var EN_ALPHABET_LOWER = "abcdefghijklmnopqrstuvwxyz";
    var RU_ALPHABET_LOWER = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    var DIGITS = "1234567890";

    function containsLettersDigitsHyphen(value) {
        var HYPHEN = "-";
        return containsOnlyCharsIgnoreCase(value, EN_ALPHABET_LOWER + RU_ALPHABET_LOWER + DIGITS + HYPHEN)
    }

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
})();