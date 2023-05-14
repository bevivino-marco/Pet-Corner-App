package com.petcorner.petcorner.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.petcorner.petcorner.R

@Composable
fun ProfileScreen(navController: NavHostController) {
    val nome = "Marco"
    val cognome= "Bevivino"
    val username = "marco.bevivino@gmail.com"
    val citta = "Torino"
    val ruoli = listOf<String>("Trainer", "Sitter")
    val animali = listOf<String>("Baloo", "Yvi", "Blue", "Yvi", "Blue", "Yvi", "Blue", "Yvi", "Blue", "Yvi", "Blue", "Yvi", "Blue", "Yvi", "Blue")

    Column(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = { navController.navigate("login") },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .width(100.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black,
                    contentColor = Color.White
                )
            ) {

                Text(text = "LOG OUT")
            }
        }
        Spacer(modifier = Modifier.padding(5.dp))
//        Surface(
//        modifier = Modifier
//            .size(154.dp)
//            .padding(5.dp),
//        shape = CircleShape,
//        border = BorderStroke(0.5.dp, Color.LightGray),
//        elevation = 4.dp,
//        color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
//    ) {
//
//        Image(
//            painter = painterResource(id = R.drawable.ic_launcher_background),
//            contentDescription = "profile image",
//            modifier = Modifier.size(135.dp),
//            contentScale = ContentScale.Crop)
//
//
//        }

        ProfileImage()



        Text(
            color = Color.Blue,
            fontSize = 24.sp,
            style = MaterialTheme.typography.h4,
            text = nome + " " + cognome
        )

        Text(
            text = username,
            modifier = Modifier.padding(3.dp),
            style = MaterialTheme.typography.subtitle1
        )

        Text(
            text = citta,
            modifier = Modifier.padding(3.dp)
        )
        Spacer(modifier = Modifier.padding(3.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        )
        {
            Text(text = "Ruoli", fontWeight = FontWeight.Bold)
            Row() {
                var trainerCheck = "No"
                var sitterCheck = "No"
                if (ruoli.contains("Trainer")) trainerCheck = "SI"
                if (ruoli.contains("Sitter")) sitterCheck = "SI"
                Text(text = "Trainer: " + trainerCheck)

                Spacer(modifier = Modifier.padding(3.dp))

                Text(text = "Sitter: " + sitterCheck)
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        )


        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 60.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (animale in animali) {

                Spacer(modifier = Modifier.padding(5.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "profile image",
                        modifier = Modifier.size(50.dp),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.padding(5.dp))

                    Text(text = animale)

                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                horizontalArrangement = Arrangement.End
            ) {
                val contextForToast = LocalContext.current.applicationContext
                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent,
                    ),
                    onClick = {
                        Toast.makeText(
                            contextForToast,
                            "Aggiungi Animale",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "add animal")


                }
            }


        }


    }

}