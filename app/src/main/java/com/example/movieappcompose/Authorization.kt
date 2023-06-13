package com.example.movieappcompose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Password
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movieappcompose.ui.theme.Teal200
import com.example.movieappcompose.ui.theme.base
import com.example.movieappcompose.ui.theme.dark

@Composable
fun InputFields(label: String = "", initialText: String = "", imageVector: ImageVector = Icons.Rounded.Password) {
    var text by remember {
        mutableStateOf(initialText)
    }

    TextField(
        value = text,
        onValueChange = {text = it},
        label = {Text(text = label)},
        singleLine = true,
        leadingIcon = {Icon(imageVector = imageVector, "", tint = base)},
        modifier = Modifier
            .padding(5.dp)
            .border(
                BorderStroke(
                    width = 4.dp,
                    brush = Brush.horizontalGradient(listOf(Teal200, base))
                ), shape = RoundedCornerShape(8.dp)
            )
            .fillMaxWidth(),
        trailingIcon = {
            IconButton(onClick = {text = "" }) {
                Icon(imageVector = Icons.Rounded.Cancel, "delete", tint = dark)
            }
        },
    )
}

@Composable
fun PasswordField() {
    var showPassword by rememberSaveable { mutableStateOf(false) }
    var password by rememberSaveable { mutableStateOf("") }

    TextField(
        value = password,
        onValueChange = {password = it},
        label = {Text(text = "Password")},
        singleLine = true,
        leadingIcon = {Icon(imageVector = Icons.Rounded.Password, "", tint = base)},
        modifier = Modifier
            .padding(5.dp)
            .border(
                BorderStroke(
                    width = 4.dp,
                    brush = Brush.horizontalGradient(listOf(Teal200, base))
                ), shape = RoundedCornerShape(8.dp)
            )
            .fillMaxWidth(),
        trailingIcon = {
            val icon = if (showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            IconButton(onClick = {showPassword != showPassword}) {
                Icon(imageVector = icon, "", tint = dark)
            }
        },
    )
}

@Composable
fun Login() {
    Column(
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        InputFields("Email", "example@mail.ru", Icons.Rounded.Email)
        InputFields("Password", "", Icons.Rounded.Password)
    }
}

@Composable
fun SignUp() {

}

@Preview(showBackground = true)
@Composable
fun Preview() {
    Login()
}