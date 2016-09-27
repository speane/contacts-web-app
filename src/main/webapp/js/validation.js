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
