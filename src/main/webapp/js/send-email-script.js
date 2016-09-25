var emailPatternSelect = document.getElementById('email-pattern-select');
var emailTextArea = document.getElementById('email-text-area');
emailPatternSelect.onchange = function() {
    emailTextArea.value = emailPatternSelect.options[emailPatternSelect.selectedIndex].value;
};