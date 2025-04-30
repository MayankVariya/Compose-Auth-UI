package com.example.authentifyapplication.utils

import android.util.Patterns

fun isEmailValid(email: String): Boolean {
    if (email.isEmpty()) {
        return true
    } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        return true
    }
    return false
}

fun isPasswordValid(pass: String): Boolean {
    if (pass.isEmpty()) {
        return true
    } else if (pass.length <= 6) {
        return true
    }
    return false
}

fun isUseNameValid(name: String): Boolean = name.isEmpty()
fun isAddressValid(address: String): Boolean = address.isEmpty()
fun isBirthDateValid(date: String): Boolean = date.isEmpty()

