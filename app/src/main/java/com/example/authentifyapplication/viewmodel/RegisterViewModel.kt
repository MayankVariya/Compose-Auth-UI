package com.example.authentifyapplication.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.authentifyapplication.R
import com.example.authentifyapplication.datastore.UserPreferences
import com.example.authentifyapplication.model.User
import com.example.authentifyapplication.navigation.NavigationRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class RegisterViewModel : ViewModel() {
    var userName = MutableStateFlow("")
    var email = MutableStateFlow("")
    var password = MutableStateFlow("")
    var address = MutableStateFlow("")
    var genderIndex = MutableStateFlow(-1)
    var birthDate = MutableStateFlow("")
    var isUserNameErrorState = MutableStateFlow(false)
    var isEmailErrorState = MutableStateFlow(false)
    var isPasswordErrorState = MutableStateFlow(false)
    var isAddressErrorState = MutableStateFlow(false)
    var isBirthDateErrorState = MutableStateFlow(false)
    var isGenderErrorState = MutableStateFlow(false)

    fun genders(context: Context): String {
        if (genderIndex.value == 0) return context.getString(R.string.male)
        else if (genderIndex.value == 1) return context.getString(R.string.female)
        return ""
    }

    fun setEmail(value: String) = email.tryEmit(value)
    fun setUserName(value: String) = userName.tryEmit(value)
    fun setPassword(value: String) = password.tryEmit(value)
    fun setBirth(value: String) = birthDate.tryEmit(value)
    fun setGender(value: Int) = genderIndex.tryEmit(value)
    fun setAddress(value: String) = address.tryEmit(value)
    fun setIsEmailError(error: Boolean) = isEmailErrorState.tryEmit(error)
    fun setIsUserNameError(error: Boolean) = isUserNameErrorState.tryEmit(error)
    fun setIsPasswordError(error: Boolean) = isPasswordErrorState.tryEmit(error)
    fun setIsAddressError(error: Boolean) = isAddressErrorState.tryEmit(error)
    fun setIsGenderError(error: Boolean) = isGenderErrorState.tryEmit(error)
    fun setIsBirthError(error: Boolean) = isBirthDateErrorState.tryEmit(error)

    fun register(navController: NavHostController, user: User, preferences: UserPreferences) {
        viewModelScope.launch {
            preferences.storeUserInfo(user)
            navController.navigate(NavigationRoute.LoginPage.route) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    }
}