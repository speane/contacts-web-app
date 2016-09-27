var contactListForm = document.getElementById("contact-list-form");
document.getElementById("delete-contact-button").onclick = function() {
    contactListForm.action = "/app/delete-contact";
    contactListForm.submit();
};

document.getElementById("edit-checked-contact-button").onclick = function() {
    contactListForm.action = "/app/edit-checked-contact"
};

var sendEmailButton = document.getElementById('send-email-button');
sendEmailButton.onclick = function() {
    if (isAnyContactSelected()) {
        contactListForm.action = "/app/send-email";
    }
    else {
        alert('SELECT A CONTACT')
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