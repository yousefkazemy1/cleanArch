package com.example.core

private const val EMAIL_REGEX = "^[a-zA-Z0-9](\\.?[a-zA-Z0-9]){5,29}@\\w+\\.com\$"

private const val USERNAME_REGEX = "^[a-zA-Z0-9]([a-zA-Z0-9_.]){4,19}\$"

private const val PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{6,}\$"

/**
 * The legal characters are: letters(a-z), digits(0-9), dot(.)
 * If these situations aren't considered, validating won't be passed:
 * 1) The string should just contain above legal characters
 * 2) The string shouldn't end with dot(.)
 * 3) The string's count should be between 6 and 30
 * 4) The string should contain @[a-z].com @sample.com
 */
fun String.validateEmail() = EMAIL_REGEX.toRegex().matches(this)

/**
 * The legal characters are: letters(a-z), digits(0-9), dot(.), underscore(_)
 * If these situations aren't considered, validating won't be passed:
 * 1) The string should just contain above legal characters
 * 2) The string's count should be between 5 and 20
 */
fun String.validateUsername() = USERNAME_REGEX.toRegex().matches(this)

/**
 * If these situations aren't considered, validating won't be passed:
 * 1) The password should be a combination of Letters(both lowercase and uppercase) and digits
 * 2) The password's count should be more than 6
 */
fun String.validatePassword() = PASSWORD_REGEX.toRegex().matches(this)