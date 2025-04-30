package com.example.authentifyapplication.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.authentifyapplication.ui.theme.Pink80
import com.example.authentifyapplication.ui.theme.PurpleGrey80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType,
    isError: Boolean,
    top: Dp = 16.dp,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    height: Dp = 80.dp,
    readOnly: Boolean = false,
    trailingIcon: @Composable (() -> Unit)?,
    visualTransformation: VisualTransformation,
    onDone: (KeyboardActionScope.() -> Unit)?
) {
    OutlinedTextField(
        value = value,
        singleLine = singleLine,
        isError = isError,
        maxLines = maxLines,
        readOnly = readOnly,
        trailingIcon = trailingIcon,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(10.dp),
        visualTransformation = visualTransformation,
        keyboardActions = KeyboardActions(onDone = onDone),
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .padding(top = top),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        label = { Text(text = label, fontFamily = FontFamily.Monospace) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color.White,
            focusedBorderColor = Pink80,
            unfocusedBorderColor = PurpleGrey80,
            errorBorderColor = Color.Red,
        )
    )
}