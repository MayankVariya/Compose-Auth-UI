package com.example.authentifyapplication.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.authentifyapplication.R
import com.example.authentifyapplication.datastore.UserPreferences
import com.example.authentifyapplication.navigation.NavigationRoute
import com.example.authentifyapplication.ui.theme.Purple40
import com.example.authentifyapplication.ui.theme.Purple50
import com.example.authentifyapplication.ui.theme.Purple60
import com.example.authentifyapplication.ui.theme.Purple80
import com.example.authentifyapplication.utils.CustomTextField
import com.example.authentifyapplication.utils.isEmailValid
import com.example.authentifyapplication.utils.isPasswordValid
import com.example.authentifyapplication.viewmodel.LoginViewModel


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun LoginView(navController: NavHostController, preferences: UserPreferences) {
    val loginVM = remember { LoginViewModel(preferences) }
    val emailValue = loginVM.emailValue.collectAsState()
    val passwordValue = loginVM.passwordValue.collectAsState()
    val emailError = loginVM.emailError.collectAsState()
    val passwordError = loginVM.passError.collectAsState()
    val errorDialog = loginVM.errorDialog.collectAsState()
    val user = loginVM.user.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    var passVisibility by remember { mutableStateOf(false) }
    var buttonEnable by remember { mutableStateOf(false) }

    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.TopCenter
        ) {
            Box(
                modifier = Modifier
                    .background(Purple80)
                    .width(7.dp)
                    .fillMaxHeight(0.3f)
                    .clip(CircleShape)
            )
            Box(
                modifier = Modifier
                    .aspectRatio(0.35f),
                contentAlignment = Alignment.BottomCenter
            ) {
                AsyncImage(
                    model = stringResource(id = R.string.login_image),
                    contentDescription = null,
                    modifier = Modifier.aspectRatio(0.9f)
                )
            }
            Card(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 60.dp),

                shape = RoundedCornerShape(13.dp),
                colors = CardDefaults.elevatedCardColors(Purple80),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 32.dp)
                        .verticalScroll(
                            rememberScrollState()
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = stringResource(id = R.string.login),
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        fontFamily = FontFamily.Monospace
                    )
                    CustomTextField(
                        value = emailValue.value,
                        onValueChange = { email ->
                            if (emailError.value) loginVM.setIsEmailError(false)
                            loginVM.setEmail(email)
                        },
                        top = 24.dp,
                        height = 87.dp,
                        label = stringResource(id = R.string.email),
                        keyboardType = KeyboardType.Email,
                        trailingIcon = null,
                        isError = emailError.value,
                        visualTransformation = VisualTransformation.None,
                        onDone = null
                    )
                    CustomTextField(
                        value = passwordValue.value,
                        onValueChange = { pass ->
                            if (passwordError.value) loginVM.setIsPasswordError(false)
                            loginVM.setPassword(pass)
                            buttonEnable = true
                        },
                        label = stringResource(id = R.string.password),
                        keyboardType = KeyboardType.Password,
                        trailingIcon = {
                            IconButton(onClick = { passVisibility = !passVisibility }) {
                                Icon(
                                    painter = painterResource(
                                        id = if (passVisibility)
                                            R.drawable.ic_visible else R.drawable.ic_visible_off
                                    ),
                                    contentDescription = null
                                )
                            }
                        },
                        isError = passwordError.value,
                        visualTransformation = if (passVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                        onDone = {
                            keyboardController?.hide()
                        }
                    )
                    Text(
                        text = stringResource(id = R.string.forgot_password),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        textAlign = TextAlign.End
                    )
                    if (errorDialog.value) {
                        AlertDialog(title = { Text(text = stringResource(id = R.string.result)) },
                            text = { Text(text = stringResource(id = R.string.not_found)) },
                            onDismissRequest = { loginVM.setIsErrorDialog(false) },
                            confirmButton = {
                                TextButton(onClick = { loginVM.setIsErrorDialog(false) }) {
                                    Text(text = stringResource(id = R.string.ok))
                                }
                            })
                    }
                    ElevatedButton(
                        onClick = {
                            when {
                                isEmailValid(emailValue.value) -> loginVM.setIsEmailError(true)
                                isPasswordValid(passwordValue.value) -> loginVM.setIsPasswordError(
                                    true
                                )

                                else -> {
                                    loginVM.setIsEmailError(false)
                                    loginVM.setIsPasswordError(false)
                                    loginVM.setIsErrorDialog(false)
                                    when {
                                        (user.value.first().email != emailValue.value &&
                                                user.value.first().password != passwordValue.value) ->
                                            loginVM.setIsErrorDialog(true)

                                        else -> {
                                            loginVM.setIsEmailError(false)
                                            loginVM.setIsPasswordError(false)
                                            loginVM.setIsErrorDialog(false)
                                            loginVM.setEmail("")
                                            loginVM.setPassword("")
                                            loginVM.login(navController, preferences)
                                        }
                                    }
                                }
                            }
                        },
                        enabled = buttonEnable,
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 32.dp, vertical = 12.dp),
                        modifier = Modifier.padding(top = 16.dp),
                        colors = ButtonDefaults.elevatedButtonColors(
                            disabledContainerColor = Purple40,
                            containerColor = Purple50
                        )
                    ) { Text(text = stringResource(id = R.string.login_now), color = Color.White) }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Text(text = stringResource(id = R.string.create_account))
                        Text(
                            text = stringResource(id = R.string.register),
                            color = Purple60,
                            modifier = Modifier.clickable {
                                navController.navigate(NavigationRoute.RegisterPage.route) {
                                    popUpTo(NavigationRoute.LoginPage.route) {
                                        inclusive = true
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}


