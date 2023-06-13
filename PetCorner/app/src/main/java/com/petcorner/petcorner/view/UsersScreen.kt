package com.petcorner.petcorner.view

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
import com.petcorner.petcorner.model.Sitter
import com.petcorner.petcorner.viewmodel.SitterViewModel
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UsersScreen() {
    var filterState = remember { mutableStateOf("ALL") }
    val viewModel = viewModel<SitterViewModel>()
    val searchText by viewModel.searchText.collectAsState()
    val sitters by viewModel.sitters.collectAsState().value.observeAsState(emptyList())
    val isSearching by viewModel.isSearching.collectAsState()


    TextField(
        value = searchText,
        onValueChange = viewModel::onSearchTextChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Cerca..") }
    )
    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 80.dp)){
        items(items= sitters, itemContent = {
            SitterCard(sitter = it)



        })


    }





}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SitterCard(sitter: Sitter){
    val imageBytes = Base64.getDecoder().decode(sitter.image)
    val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .background(Color.White)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            Text(text = sitter.name, fontWeight = FontWeight.Bold)
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
            Text(text = "Etá: " + sitter.age, modifier = Modifier.padding(8.dp))
            Spacer(modifier = Modifier.padding(10.dp))
            Text(text = "Cittá: ${sitter.locality}", modifier = Modifier.padding(start = 8.dp))
        }
        val contextForToast = LocalContext.current.applicationContext
        val context = LocalContext.current

        Button(
            onClick = {
                Toast.makeText(
                contextForToast,
                "Contatta Proprietario",
                Toast.LENGTH_SHORT
            ).show()
                context.sendMailToSitter(to = sitter.email, subject = "Richiesta Disponibilitá per dogsitting")
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


    }
}








fun Context.sendMailToSitter(to: String, subject: String) {
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

