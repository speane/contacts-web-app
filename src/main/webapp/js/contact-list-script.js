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
    contactListForm.action = "/app/send-email";
};