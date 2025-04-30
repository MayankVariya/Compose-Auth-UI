package com.example.authentifyapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.authentifyapplication.datastore.UserPreferences
import com.example.authentifyapplication.model.User
import com.example.authentifyapplication.navigation.NavigationRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class LoginViewModel(preferences: UserPreferences) : ViewModel() {
    var emailValue = MutableStateFlow("")
    var passwordValue = MutableStateFlow("")
    var emailError = MutableStateFlow(false)
    var passError = MutableStateFlow(false)
    var errorDialog = MutableStateFlow(false)
    var user = MutableStateFlow(mutableSetOf<User>())

    init {
        getUser(preferences)
    }

    private fun getUser(preferences: UserPreferences) =
        viewModelScope.launch { user.value.add(preferences.userInfo.first()) }

    fun setEmail(email: String) = emailValue.tryEmit(email)
    fun setPassword(password: String) = passwordValue.tryEmit(password)
    fun setIsEmailError(error: Boolean) = emailError.tryEmit(error)
    fun setIsPasswordError(error: Boolean) = passError.tryEmit(error)

    fun setIsErrorDialog(error: Boolean) = errorDialog.tryEmit(error)
    fun login(navController: NavHostController, preferences: UserPreferences) {
        viewModelScope.launch {
            preferences.login()
            navController.navigate(NavigationRoute.HomePage.route) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    }
}
