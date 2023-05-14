package com.petcorner.petcorner

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.petcorner.petcorner.ui.theme.Navigation
import com.petcorner.petcorner.ui.theme.PetCornerTheme
import com.petcorner.petcorner.view.BottomNavigationBar

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetCornerTheme(){
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            items = listOf(
                                BottomNavItem(
                                    name = "Animals",
                                    route = "animals",
                                    icon = Icons.Default.Star
                                ),
                                BottomNavItem(
                                    name = "Users",
                                    route = "users",
                                    icon = Icons.Default.Person,
                                ),
                                BottomNavItem(
                                    name = "Profile",
                                    route = "login",
                                    icon = Icons.Default.Home,
                                ),
                            ),
                            navController = navController,
                            onItemClick = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                ) {
                    Navigation(navController = navController)
                }
            }
        }
    }
}


@Composable
fun RegistrationScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val username = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }
        val citta = remember { mutableStateOf(TextFieldValue()) }
        val eta = remember { mutableStateOf(TextFieldValue()) }
        val sitter = ""
        val trainer = ""

        Text(text = "Registration", style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.SansSerif))

        Spacer(modifier = Modifier.height(20.dp))
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
        TextField(
            label = { Text(text = "Cittá") },
            value = username.value,
            onValueChange = { username.value = it })

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Etá") },
            value = username.value,
            onValueChange = { username.value = it })

        Spacer(modifier = Modifier.height(20.dp))
        TrainerCheckBox()
        Spacer(modifier = Modifier.height(20.dp))
        SitterCheckBox()
        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
            Button(
                onClick = { navController.navigate("profile")},
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Green,
                    contentColor = Color.Black
                )
            ) {

                Text(text = "REGISTER")
            }
        }



    }

}

@Composable
private fun TrainerCheckBox() {

    val contextForToast = LocalContext.current.applicationContext

    var checked by remember {
        mutableStateOf(false)
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = checked,
            onCheckedChange = { checked_ ->
                checked = checked_
                if (checked==true)
                    Toast.makeText(contextForToast, "Sarai registrato come Trainer", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(contextForToast, "Non sarai registrato come Trainer", Toast.LENGTH_SHORT).show()
            }
        )

        Text(
            modifier = Modifier.padding(start = 2.dp),
            text = "As Trainer"
        )
    }
}


@Composable
private fun SitterCheckBox() {

    val contextForToast = LocalContext.current.applicationContext

    var checked by remember {
        mutableStateOf(false)
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = checked,
            onCheckedChange = { checked_ ->
                checked = checked_
                if (checked==true)
                Toast.makeText(contextForToast, "Sarai registrato come Sitter", Toast.LENGTH_SHORT).show()
                else {
                    Toast.makeText(contextForToast, "Non sarai registrato come Sitter", Toast.LENGTH_SHORT).show()
                }

            }
        )

        Text(
            modifier = Modifier.padding(start = 2.dp),
            text = "As Sitter"
        )
    }
}


fun onRegisterClick(navController: NavController) {
    navController.navigate("register")
}
