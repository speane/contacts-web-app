function isNumber(value) {
    var digits = "1234567890";
    for (var i = 0; i < value.length; i++) {
        if (digits.indexOf(value.charAt(i)) == -1) {
            return false;
        }
    }
    return true;
}

function containsOnlyLetters(value) {
    var enAlphabet = "abcdefghijklmnopqrstuvwxyz";
    var ruAlphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    var lowerCaseValue = value.toLowerCase();
    for (var i = 0; i < value.length; i++) {
        if ((enAlphabet.indexOf(lowerCaseValue.charAt(i)) == -1)
            && (ruAlphabet.indexOf(lowerCaseValue.charAt(i)) == -1)) {
            return false;
        }
    }
    return true;
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
