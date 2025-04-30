package com.example.authentifyapplication.navigation


sealed class NavigationRoute(var route: String) {
    object LoginPage : NavigationRoute("login_page")
    object RegisterPage : NavigationRoute("register_page")
    object HomePage : NavigationRoute("home_page")
}
