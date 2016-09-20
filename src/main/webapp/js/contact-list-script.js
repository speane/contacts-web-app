var contactListForm = document.getElementById("contact-list-form");
document.getElementById("delete-contact-button").onclick = function() {
    contactListForm.action = "/app/delete-contact";
    contactListForm.submit();
};