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

window.onclick = function(event) {
    if (event.target == phoneEditModal) {
        hideModalForm(phoneEditModal);
    }
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

var submitButton = document.getElementById('submit-form-button');
submitButton.onclick = function() {
    alert("SDFSDF");
    var form = document.getElementById('contact-form');
    var createdPhonesInput = document.createElement("input");
    createdPhonesInput.setAttribute('type', 'hidden');
    createdPhonesInput.setAttribute('value', 'HELLO, WORLD');
    createdPhonesInput.setAttribute('name', 'created-phones');
    form.appendChild(createdPhonesInput);
    form.submit();
}

