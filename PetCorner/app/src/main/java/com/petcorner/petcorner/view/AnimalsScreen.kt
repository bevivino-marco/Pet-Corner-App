package com.petcorner.petcorner.view

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.petcorner.petcorner.model.Animal
import com.petcorner.petcorner.viewmodel.AnimalViewModel
import java.util.*

//@Composable
//fun AnimalsScreen() {
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Text(text = "Animals screen")
//    }
//}
@SuppressLint("SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AnimalsScreen(animalViewModel: AnimalViewModel) {
    var filterState = remember { mutableStateOf("ALL") }
    val viewModel = viewModel<AnimalViewModel>()
    val searchText by viewModel.searchText.collectAsState()
    val animals by viewModel.animals.collectAsState().value.observeAsState(emptyList())
    val isSearching by viewModel.isSearching.collectAsState()
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

//    Animal_CascadeDropdownMenu(provenances,animalViewModel,filterState)


        TextField(
            value = searchText,
            onValueChange = viewModel::onSearchTextChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Search") }
        )
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 80.dp)){
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


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AnimalCard(animal: Animal){
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

//@Composable
//fun Animal_CascadeDropdownMenu(
//    provenances: List<String>,
//    animalViewModel: AnimalViewModel,
//    filterState: MutableState<String>
//) {
//
//    var expanded by remember { mutableStateOf(false) }
//    var selectedText by remember { mutableStateOf("") }
//    var provenances = provenances.plus("ALL")
//    var textfieldSize by remember { mutableStateOf(Size.Zero)}
//
//    val icon = if (expanded)
//        Icons.Filled.KeyboardArrowUp
//    else
//        Icons.Filled.KeyboardArrowDown
//
//
//    Column(Modifier.padding(10.dp)) {
//        OutlinedTextField(
//            value = selectedText,
//            onValueChange = { selectedText = it },
//            modifier = Modifier
//                .fillMaxWidth()
//                .onGloballyPositioned { coordinates ->
//                    //This value is used to assign to the DropDown the same width
//                    textfieldSize = coordinates.size.toSize()
//                },
//            label = {Text("Provincia")},
//            trailingIcon = {
//                Icon(icon,"contentDescription",
//                    Modifier.clickable { expanded = !expanded })
//            }
//        )
//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false },
//            modifier = Modifier
//                .width(with(LocalDensity.current){textfieldSize.width.toDp()})
//        ) {
//            provenances.forEach { provenance ->
//                DropdownMenuItem(onClick = {
//                    selectedText = provenance
//                    filterState.
////                    animalViewModel.filterByProvenance(provenance)
//                    expanded = false
//                }) {
//                    Text(text = provenance)
//                }
//            }
//        }
//    }
//
//}