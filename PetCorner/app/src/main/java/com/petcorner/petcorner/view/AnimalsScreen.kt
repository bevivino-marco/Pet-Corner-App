package com.petcorner.petcorner.view

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType.Companion.Uri
import androidx.compose.ui.unit.dp
import com.petcorner.petcorner.R
import com.petcorner.petcorner.model.Animal
import com.petcorner.petcorner.viewmodel.AnimalViewModel

//@Composable
//fun AnimalsScreen() {
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Text(text = "Animals screen")
//    }
//}
@Composable
fun AnimalsScreen(animalViewModel: AnimalViewModel) {

//    val animals =  listOf<AnimalPost>(
//        AnimalPost(123, "Lullu",2, "2", 2254, "Torino", true, false),
//        AnimalPost(123, "Lullu",2, "2", 2254, "Torino", true, false),
//        AnimalPost(123, "Lullu",2, "2", 2254, "Torino", true, false),
//        AnimalPost(123, "Lullu",2, "2", 2254, "Torino", true, false),
//        AnimalPost(123, "Lullu",2, "2", 2254, "Torino", true, false),
//        AnimalPost(123, "Lullu",2, "2", 2254, "Torino", true, false),
//        AnimalPost(123, "Lullu",2, "2", 2254, "Torino", true, false),
//        AnimalPost(123, "Lullu",2, "2", 2254, "Torino", true, false),
//        AnimalPost(123, "Lullu",2, "2", 2254, "Torino", true, false),
//        AnimalPost(123, "Lullu",2, "2", 2254, "Torino", true, false),
//        AnimalPost(123, "Lullu",2, "2", 2254, "Torino", true, false),
//
//
//    );
    val animals by animalViewModel.animals.observeAsState(initial = emptyList())
    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 60.dp)){
        items(items= animals, itemContent = {


                AnimalCard(animal = it)


        })


    }

//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Text(text = "Animals screen")
//    }




}

@Composable
fun AnimalCard(animal: Animal){

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
            painter = painterResource(id = R.drawable.ic_launcher_background),
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
            onClick = {Toast.makeText(
                    contextForToast,
                    "Contatta Proprietario",
                    Toast.LENGTH_SHORT
                ).show()
                context.sendMail(to = animal.owner, subject = "Richiesta Disponibilitá adozione per "+animal.name+". microchip: "+animal.microchip)
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
            Text(text = "contatta")
        }

        Spacer(modifier = Modifier
            .padding(25.dp)
            .background(color = Color.DarkGray))

//        Button(
//            onClick = {
//                Toast.makeText(
//                    contextForToast,
//                    "Contatta Proprietario",
//                    Toast.LENGTH_SHORT
//                ).show()
//            },
////                shape = RoundedCornerShape(50.dp),
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(50.dp),
//            colors = ButtonDefaults.buttonColors(
//                backgroundColor = Color.LightGray,
//                contentColor = Color.Black
//            )
//
//        ) {
//            Text(text = "contatta")
//        }
    }
}








fun Context.sendMail(to: String, subject: String) {
    try {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "vnd.android.cursor.item/email" // or "message/rfc822"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        // TODO: Handle case where no email app is available
    } catch (t: Throwable) {
        // TODO: Handle potential other type of exceptions
    }
}

//fun Context.dial(phone: String) {
//    try {
//        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
//        startActivity(intent)
//    } catch (t: Throwable) {
//        // TODO: Handle potential exceptions
//    }
//}




