var phoneEditModal = document.getElementById('phone-edit-modal');

var addPhoneButton = document.getElementById('add-phone-button');

addPhoneButton.onclick = function() {
    phoneEditModal.style.display = "block";
};

window.onclick = function(event) {
    if (event.target == phoneEditModal) {
        phoneEditModal.style.display = "none";
    }
};
