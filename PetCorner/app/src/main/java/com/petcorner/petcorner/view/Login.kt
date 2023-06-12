package com.petcorner.petcorner.view

import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.petcorner.petcorner.view.functions.onRegisterClick
import com.petcorner.petcorner.R
import com.petcorner.petcorner.model.Profile
import com.petcorner.petcorner.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch

@Composable
fun Login(navController: NavController, profileViewModel: ProfileViewModel){
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val username = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }

        var visible by remember { mutableStateOf(false) }

        Image(
            painter = painterResource(id = R.drawable.ic_action_name_foreground),
            contentDescription = "logo",
            modifier = Modifier
                .size(150.dp)
        )
        MyText(visible)
        TextField(
            label = { Text(text = "Username") },
            value = username.value,
            onValueChange = { username.value = it })
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Password") },
            value = password.value,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { password.value = it })
        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
            Button(
                enabled = username.value.text != "" && password.value.text != "",
                onClick = {
                    coroutineScope.launch {
                        if(login(profileViewModel, username.value.text, password.value.text)) {
                            navController.navigate("profile")
                        } else {
                            visible = true
                        }
                    }},
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Login")
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        ClickableText(
            text = AnnotatedString("Registrati qui"),
            onClick = { onRegisterClick(navController) },
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Default
            )
        )
    }
}
@Composable
fun MyText(isVisible: Boolean){
    if(isVisible){
        Text(
            "Ops, credenziali errate",
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Default
            )
        )
    }
}

suspend fun login(viewModel: ProfileViewModel, username: String, psw: String) : Boolean{
    return viewModel.login(username, psw)
}