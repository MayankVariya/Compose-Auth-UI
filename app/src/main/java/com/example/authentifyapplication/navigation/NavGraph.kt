package com.example.authentifyapplication.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.authentifyapplication.datastore.UserPreferences
import com.example.authentifyapplication.model.User
import com.example.authentifyapplication.view.HomeView
import com.example.authentifyapplication.view.LoginView
import com.example.authentifyapplication.view.RegisterView


@Composable
fun NavGraph(navController: NavHostController, isLogin:Boolean,user: User,preferences: UserPreferences) {
    NavHost(
        navController = navController,
        startDestination = if (user.email != "") {
            if (isLogin) {
                NavigationRoute.HomePage.route
            } else {
                NavigationRoute.LoginPage.route
            }
        } else NavigationRoute.LoginPage.route
    ) {
        composable(route = NavigationRoute.LoginPage.route) { LoginView(navController,preferences) }
        composable(route = NavigationRoute.RegisterPage.route) { RegisterView(navController,preferences) }
        composable(route = NavigationRoute.HomePage.route) { HomeView(navController,preferences) }
    }
}