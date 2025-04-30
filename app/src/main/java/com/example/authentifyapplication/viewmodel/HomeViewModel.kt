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

class HomeViewModel(preferences: UserPreferences) : ViewModel() {
    var user = MutableStateFlow(mutableSetOf<User>())

    init {
        getUser(preferences)
    }

    private fun getUser(preferences: UserPreferences) =
        viewModelScope.launch { user.value.add(preferences.userInfo.first()) }

    fun logoutORDelete(
        navController: NavHostController,
        isDelete: Boolean,
        preferences: UserPreferences
    ) {
        viewModelScope.launch {
            if (isDelete) preferences.delete()
            else preferences.logout()
            navController.navigate(NavigationRoute.LoginPage.route) {
                popUpTo(NavigationRoute.HomePage.route) {
                    inclusive = true
                }
            }
        }
    }
}