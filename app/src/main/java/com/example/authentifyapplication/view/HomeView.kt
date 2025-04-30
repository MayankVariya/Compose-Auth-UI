package com.example.authentifyapplication.view


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.authentifyapplication.ui.theme.Purple80
import com.example.authentifyapplication.viewmodel.HomeViewModel
import com.example.authentifyapplication.R
import com.example.authentifyapplication.datastore.UserPreferences

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navController: NavHostController, preferences: UserPreferences) {
    val homeVM = remember { HomeViewModel(preferences) }
    val user = homeVM.user.collectAsState()

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = user.value.first().name)
        }, actions = {
            TextButton(onClick = { homeVM.logoutORDelete(navController, false, preferences) }) {
                Text(text = stringResource(id = R.string.logout), color = Color.DarkGray)
            }
        }, colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Purple80))
    }) {
        Column(
            modifier = Modifier.padding(it),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(text = "Name : ${user.value.first().name}", modifier = Modifier.padding(8.dp))
            Text(text = "Email : ${user.value.first().email}", modifier = Modifier.padding(8.dp))
            Text(
                text = "Birth Date : ${user.value.first().birth}",
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = "Address : ${user.value.first().address}",
                modifier = Modifier.padding(8.dp)
            )
            Text(text = "Gender : ${user.value.first().gender}", modifier = Modifier.padding(8.dp))
            Button(
                onClick = { homeVM.logoutORDelete(navController, true, preferences) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                shape = RoundedCornerShape(10.dp)
            ) { Text(text = stringResource(id = R.string.delete)) }
        }
    }
}