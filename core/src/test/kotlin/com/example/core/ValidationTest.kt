package com.example.core

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ValidationTest {

    class Email {
        @Test
        fun validateEmail_addIllegalCharToEmail_notValidate() {
            val result = "yousef?kazemy1@gmail.com".validateEmail()
            assertEquals(false, result)
        }

        @Test
        fun validateEmail_addDotToEndOfEmail_notValidate() {
            val result = "yousefkazemy1.@gmail.com".validateEmail()
            assertEquals(false, result)
        }

        @Test
        fun validateEmail_emailIsLessThan6Chars_notValidate() {
            val result = "youse.@gmail.com".validateEmail()
            assertEquals(false, result)
        }

        @Test
        fun validateEmail_emailIsMoreThan30Chars_notValidate() {
            val result = "yousefkazemy1yousefkazemy1yousefkazemy1@gmail.com".validateEmail()
            assertEquals(false, result)
        }

        @Test
        fun validateEmail_notContainEmailAtSignPostfix_notValidate() {
            val result = "yousefkazemy1gmail.com".validateEmail()
            assertEquals(false, result)
        }

        @Test
        fun validateEmail_notContainEmailPostfixFirstPart_notValidate() {
            val result = "yousefkazemy1@.com".validateEmail()
            assertEquals(false, result)
        }

        @Test
        fun validateEmail_notContainEmailPostfixSecondPart_notValidate() {
            val result = "yousefkazemy1@gmail".validateEmail()
            assertEquals(false, result)
        }

        @Test
        fun validateEmail_emailIsCorrect_validatesSuccessfully() {
            val result = "yousefkazemy1@gmail.com".validateEmail()
            assertEquals(true, result)
        }
    }

    class Username {
        @Test
        fun validateUsername_addIllegalCharToUsername_notValidate() {
            val result = "yousef#@kazemy-".validateUsername()
            assertEquals(false, result)
        }

        @Test
        fun validateUsername_usernameIsLessThan5Chars_notValidate() {
            val result = "yosf".validateUsername()
            assertEquals(false, result)
        }

        @Test
        fun validateUsername_usernameIsMoreThan20Chars_notValidate() {
            val result = "yousefkazemy1996_androiddeveloper".validateUsername()
            assertEquals(false, result)
        }

        @Test
        fun validateUsername_addUnderscoreAndDotToUsername_validatesSuccessfully() {
            val result = "yousefKa.android_dev".validateUsername()
            assertEquals(true, result)
        }
    }

    class Password {
        @Test
        fun validatePassword_passwordIsLessThan6Chars_notValidate() {
            val result = "yousf".validatePassword()
            assertEquals(false, result)
        }

        @Test
        fun validatePassword_passwordIsNotCombinationOfDigitsAndUpperAndLowercase_notValidate() {
            val result = "yousefkazemy".validatePassword()
            assertEquals(false, result)
        }

        @Test
        fun validatePassword_passwordIsCombinationOfDigitsAndUpperAndLowercase_validateSuccessfully() {
            val result = "YousefKazemy1996".validatePassword()
            assertEquals(true, result)
        }
    }
}