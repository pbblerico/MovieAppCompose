package com.example.movieappcompose.shared.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movieappcompose.ui.theme.Teal200
import com.example.movieappcompose.ui.theme.base

@Composable
fun AuthForm() {
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .imePadding()
    ) {
        var email by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }

        CustomTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email",
            leadingIconImageVector = Icons.Rounded.Email,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            )
        )
        CustomTextField(
            value = password,
            onValueChange = { password = it},
            label = "Password",
            leadingIconImageVector = Icons.Rounded.Lock,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            )
        )
    }
}

@Composable
fun AuthPage(isLogin: Boolean, login: () -> Unit, signUp: () -> Unit) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 25.dp)
            .padding(vertical = 180.dp)
            .background(
                brush = Brush.horizontalGradient(listOf(Teal200, base)),
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                BorderStroke(
                    width = 2.dp,
                    color = Color.Black
                ),
                shape = RoundedCornerShape(16.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
            ) {
            Text(text = if (isLogin) "Log In" else "Sign Up", style = MaterialTheme.typography.h3, modifier = Modifier.padding(bottom = 30.dp))
            AuthForm()
            CustomAuthButton(
                text = if (isLogin) "Log In" else "Sign Up",
                action = if(isLogin) login else signUp
            )
            CustomAuthButton(
                text = if (!isLogin) "Log In" else "Sign Up",
                action = if(!isLogin) login else signUp
            )
    }
}

@Composable
fun CustomAuthButton(text: String, action: () -> Unit) {
    Button(
        onClick = { action.invoke() },
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(vertical = 8.dp)
    ){
        Text(text)
    }
}

@Composable
fun LoginPage(login: () -> Unit, signUp: () -> Unit) {
    AuthPage(isLogin = true, login, signUp)
}

@Composable
fun SignUpPage(login: () -> Unit, signUp: () -> Unit) {
    AuthPage(isLogin = false, login, signUp)
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    LoginPage({}, {})
}