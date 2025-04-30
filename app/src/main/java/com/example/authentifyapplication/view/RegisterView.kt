package com.example.authentifyapplication.view

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.authentifyapplication.R
import com.example.authentifyapplication.datastore.UserPreferences
import com.example.authentifyapplication.model.User
import com.example.authentifyapplication.navigation.NavigationRoute
import com.example.authentifyapplication.ui.theme.Purple40
import com.example.authentifyapplication.ui.theme.Purple50
import com.example.authentifyapplication.ui.theme.Purple60
import com.example.authentifyapplication.ui.theme.Purple80
import com.example.authentifyapplication.utils.CustomTextField
import com.example.authentifyapplication.utils.isAddressValid
import com.example.authentifyapplication.utils.isBirthDateValid
import com.example.authentifyapplication.utils.isEmailValid
import com.example.authentifyapplication.utils.isPasswordValid
import com.example.authentifyapplication.utils.isUseNameValid
import com.example.authentifyapplication.viewmodel.RegisterViewModel
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun RegisterView(navController: NavHostController, preferences: UserPreferences) {
    val context = LocalContext.current
    val registerVM = remember { RegisterViewModel() }
    val email = registerVM.email.collectAsState()
    val password = registerVM.password.collectAsState()
    val address = registerVM.address.collectAsState()
    val userName = registerVM.userName.collectAsState()
    val birthDate = registerVM.birthDate.collectAsState()
    val emailError = registerVM.isEmailErrorState.collectAsState()
    val gender = registerVM.genderIndex.collectAsState()
    val passwordError = registerVM.isPasswordErrorState.collectAsState()
    val userNameError = registerVM.isUserNameErrorState.collectAsState()
    val addressError = registerVM.isAddressErrorState.collectAsState()
    val birthDateError = registerVM.isBirthDateErrorState.collectAsState()
    val genderError = registerVM.isGenderErrorState.collectAsState()
    var passVisibility by remember { mutableStateOf(false) }
    val genders = remember { listOf(R.string.male, R.string.female) }
    val keyboardController = LocalSoftwareKeyboardController.current
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
            Card(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 60.dp),
                shape = RoundedCornerShape(13.dp),
                colors = CardDefaults.elevatedCardColors(Purple80),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 32.dp)
                        .verticalScroll(
                            rememberScrollState()
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = stringResource(id = R.string.register),
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        fontFamily = FontFamily.Monospace
                    )
                    CustomTextField(
                        value = userName.value,
                        onValueChange = { name ->
                            registerVM.setUserName(name)
                        },
                        onDone = null,
                        label = stringResource(id = R.string.name),
                        trailingIcon = null,
                        isError = userNameError.value,
                        keyboardType = KeyboardType.Text,
                        visualTransformation = VisualTransformation.None
                    )
                    CustomTextField(
                        value =
                        email.value,
                        onValueChange = { email ->
                            registerVM.setEmail(email)
                        },
                        onDone = null,
                        label = stringResource(id = R.string.email),
                        trailingIcon = null,
                        isError = emailError.value,
                        keyboardType = KeyboardType.Email,
                        visualTransformation = VisualTransformation.None
                    )
                    CustomTextField(
                        value = password.value,
                        onValueChange = { pass ->
                            registerVM.setPassword(pass)
                        },
                        onDone = null,
                        label = stringResource(id = R.string.password),
                        isError = passwordError.value,
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
                        visualTransformation = if (passVisibility) VisualTransformation.None else PasswordVisualTransformation()
                    )
                    CustomTextField(
                        value = birthDate.value,
                        onValueChange = { date ->
                            registerVM.setBirth(date)
                        },
                        onDone = null,
                        readOnly = true,
                        label = stringResource(id = R.string.birth_date),
                        isError = birthDateError.value,
                        keyboardType = KeyboardType.Number,
                        visualTransformation = VisualTransformation.None,
                        trailingIcon = {
                            IconButton(onClick = {
                                datePicker(context, registerVM)
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_calender),
                                    contentDescription = null
                                )
                            }
                        },
                    )
                    CustomTextField(
                        value = address.value,
                        onValueChange = { address ->
                            registerVM.setAddress(address)
                        },
                        label = stringResource(id = R.string.address),
                        keyboardType = KeyboardType.Text,
                        isError = addressError.value,
                        trailingIcon = null,
                        singleLine = false,
                        maxLines = 3,
                        height = 130.dp,
                        visualTransformation = VisualTransformation.None,
                        onDone = { keyboardController?.hide() }
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.gender),
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                        genders.forEachIndexed { index, gender ->

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = index == gender,
                                    onClick = {
                                        registerVM.setGender(index)
                                        buttonEnable = true
                                    })
                                Text(
                                    text = stringResource(id = gender),
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }
                    }
                    if (genderError.value) {
                        Text(text = stringResource(id = R.string.required_error), color = Color.Red)
                    }
                    ElevatedButton(
                        onClick = {
                            when {
                                isUseNameValid(userName.value) -> registerVM.setIsUserNameError(true)
                                isEmailValid(email.value) -> registerVM.setIsEmailError(true)
                                isPasswordValid(password.value) -> registerVM.setIsPasswordError(
                                    true
                                )

                                isBirthDateValid(birthDate.value) -> registerVM.setIsBirthError(true)
                                isAddressValid(address.value) -> registerVM.setIsAddressError(true)
                                gender.value == -1 -> registerVM.setIsGenderError(true)
                                else -> {
                                    registerVM.setIsUserNameError(false)
                                    registerVM.setIsEmailError(false)
                                    registerVM.setIsPasswordError(false)
                                    registerVM.setIsBirthError(false)
                                    registerVM.setIsAddressError(false)
                                    registerVM.setIsGenderError(false)
                                    val user = User(
                                        name = userName.value.trim(),
                                        email = email.value.trim(),
                                        password = password.value.trim(),
                                        birth = birthDate.value.trim(),
                                        address = address.value.trim(),
                                        gender = registerVM.genders(context)
                                    )
                                    registerVM.register(navController, user, preferences)
                                    registerVM.setAddress("")
                                    registerVM.setBirth("")
                                    registerVM.setGender(-1)
                                    registerVM.setEmail("")
                                    registerVM.setPassword("")
                                    registerVM.setUserName("")
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
                    ) {
                        Text(text = stringResource(id = R.string.register_now), color = Color.White)
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Text(text = stringResource(id = R.string.goto_login))
                        Text(
                            text = stringResource(id = R.string.login),
                            color = Purple60,
                            modifier = Modifier.clickable {
                                navController.navigate(NavigationRoute.LoginPage.route) {
                                    popUpTo(NavigationRoute.RegisterPage.route) {
                                        inclusive = true
                                    }
                                }
                            })
                    }
                }
            }
        }
    }
}

fun datePicker(context: Context, registerVM: RegisterViewModel) {
    val calender = Calendar.getInstance()
    val year = calender.get(Calendar.YEAR)
    val month = calender.get(Calendar.MONTH)
    val day = calender.get(Calendar.DAY_OF_MONTH)
    DatePickerDialog(
        context,
        { _: DatePicker, d: Int, m: Int, y: Int ->
            registerVM.setBirth("$y/${m + 1}/$d")
        }, year, month, day
    ).also {
        it.show()
    }
}