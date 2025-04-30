package com.example.authentifyapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.authentifyapplication.datastore.UserPreferences
import com.example.authentifyapplication.model.User
import com.example.authentifyapplication.navigation.NavGraph
import com.example.authentifyapplication.ui.theme.AuthenticityApplicationTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


class MainActivity : ComponentActivity() {
    private var isLogin: Boolean by mutableStateOf(false)
    private var user: User by mutableMapOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val preferences = UserPreferences(this)
            runBlocking {
                isLogin = preferences.isLogin.first()
                user = preferences.userInfo.first()
            }
            AuthenticityApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph(navController = navController, isLogin, user, preferences)
                }
            }
        }
    }
}



