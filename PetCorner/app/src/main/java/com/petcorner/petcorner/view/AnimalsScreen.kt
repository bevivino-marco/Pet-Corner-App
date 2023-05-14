package com.petcorner.petcorner.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.petcorner.petcorner.R
import com.petcorner.petcorner.viewmodel.AnimalPost

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
fun AnimalsScreen() {

    val animals =  listOf<AnimalPost>(
        AnimalPost(123, "Lullu",2, "2", 2254, "Torino", true, false),
        AnimalPost(123, "Lullu",2, "2", 2254, "Torino", true, false),
        AnimalPost(123, "Lullu",2, "2", 2254, "Torino", true, false),
        AnimalPost(123, "Lullu",2, "2", 2254, "Torino", true, false),
        AnimalPost(123, "Lullu",2, "2", 2254, "Torino", true, false),
        AnimalPost(123, "Lullu",2, "2", 2254, "Torino", true, false),
        AnimalPost(123, "Lullu",2, "2", 2254, "Torino", true, false),
        AnimalPost(123, "Lullu",2, "2", 2254, "Torino", true, false),
        AnimalPost(123, "Lullu",2, "2", 2254, "Torino", true, false),
        AnimalPost(123, "Lullu",2, "2", 2254, "Torino", true, false),
        AnimalPost(123, "Lullu",2, "2", 2254, "Torino", true, false),


    );

    LazyColumn(modifier = Modifier.fillMaxWidth().padding(vertical = 60.dp)){
        items(items= animals, itemContent = {
            animal -> AnimalCard(animal= animal)



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
fun AnimalCard(animal: AnimalPost){

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .background(Color.White)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            Text(text = animal.animalName, fontWeight = FontWeight.Bold)
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
            Text(text = "Etá: " + animal.animalAge, modifier = Modifier.padding(8.dp))
            Spacer(modifier = Modifier.padding(10.dp))
            Text(text = "Cittá: ${animal.location}", modifier = Modifier.padding(start = 8.dp))
        }
        val contextForToast = LocalContext.current.applicationContext

        Button(
            onClick = {Toast.makeText(
                    contextForToast,
                    "Contatta Proprietario",
                    Toast.LENGTH_SHORT
                ).show()  },
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

        Spacer(modifier = Modifier.padding(25.dp).background(color = Color.DarkGray))

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













