package com.petcorner.petcorner.view

import android.graphics.BitmapFactory
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.petcorner.petcorner.model.Animal
import com.petcorner.petcorner.viewmodel.AnimalViewModel
import com.petcorner.petcorner.viewmodel.ProfileViewModel
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(navController: NavHostController, animalViewModel: AnimalViewModel, profileViewModel: ProfileViewModel) {
    val ruoli = listOf<String>("Trainer", "Sitter")
    val animali = profileViewModel.animals.collectAsState().value.observeAsState(emptyList())
    val user = profileViewModel.userInfo.collectAsState().value.observeAsState()
    val nome = user.value?.name
    val username = user.value?.username
    val citta = user.value?.city
    val token = user.value?.token

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
                onClick = {
                    if (username != null) {
                        profileViewModel.logout()
                    }
                    navController.navigate("login")
                          },
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

        user.value?.let { ProfileImage(profile = it) }

        if (nome != null) {
            Text(
                color = Color.Blue,
                fontSize = 24.sp,
                style = MaterialTheme.typography.h4,
                text = nome
            )
        }

        if (username != null) {
            Text(
                text = username,
                modifier = Modifier.padding(3.dp),
                style = MaterialTheme.typography.subtitle1
            )
        }

        if (citta != null) {
            Text(
                text = citta,
                modifier = Modifier.padding(3.dp)
            )
        }
        Spacer(modifier = Modifier.padding(3.dp))
        OutlinedButton(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue, contentColor = Color.White),
            onClick = { navController.navigate("add-animal")},
            modifier= Modifier.width(200.dp),
            elevation = ButtonDefaults.elevation(10.dp),
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "add animal")
            Text(text = "Aggiungi animale")
        }
        Spacer(modifier = Modifier.padding(3.dp))
//        Column(
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.Start
//        )
//        {
//            Text(text = "Ruoli", fontWeight = FontWeight.Bold)
//            Row() {
//                var trainerCheck = "No"
//                var sitterCheck = "No"
//                if (ruoli.contains("Trainer")) trainerCheck = "SI"
//                if (ruoli.contains("Sitter")) sitterCheck = "SI"
//                Text(text = "Trainer: " + trainerCheck)
//
//                Spacer(modifier = Modifier.padding(3.dp))
//
//                Text(text = "Sitter: " + sitterCheck)
//            }
//        }
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
            for (animale in animali.value) {
                Spacer(modifier = Modifier.padding(5.dp))
                if (token != null) {
                    AnimalCardProfile(animal = animale, profileViewModel, token)
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AnimalCardProfile(animal: Animal, profileViewModel: ProfileViewModel, token: String){
    val imageBytes = Base64.getDecoder().decode(animal.image)
    val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .background(Color.White)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = animal.name, fontWeight = FontWeight.Bold)
        }
        Image(
            painter =  BitmapPainter( decodedImage.asImageBitmap()),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.FillWidth
        )

        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Etá: " + animal.age, modifier = Modifier.padding(8.dp))
            Spacer(modifier = Modifier.padding(10.dp))
            Text(text = "Cittá: ${animal.provenance}", modifier = Modifier.padding(start = 8.dp))
        }
        val contextForToast = LocalContext.current.applicationContext
        val context = LocalContext.current

        Button(
            onClick = {

                profileViewModel.deleteAnimalForUser(animal,token )
                Toast.makeText(
                contextForToast,
                "Animale eliminato",
                Toast.LENGTH_SHORT
            ).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .width(100.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.LightGray,
                contentColor = Color.Black
            )
        ) {
            Text(text = "Elimina")
        }
        Spacer(modifier = Modifier
            .padding(25.dp)
            .background(color = Color.DarkGray)
        )
    }
}
