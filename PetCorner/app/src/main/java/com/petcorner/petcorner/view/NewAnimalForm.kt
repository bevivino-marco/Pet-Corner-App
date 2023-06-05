package com.petcorner.petcorner.view

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.petcorner.petcorner.model.Animal
import com.petcorner.petcorner.viewmodel.AnimalViewModel
import com.petcorner.petcorner.viewmodel.ProfileViewModel
import java.io.ByteArrayOutputStream
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewAnimalForm(navController: NavHostController, animalViewModel: AnimalViewModel, profileViewModel: ProfileViewModel){

    val viewModel = viewModel<ProfileViewModel>()
    val name = remember { mutableStateOf("") }
    val sex = remember { mutableStateOf("") }
    val microchip = remember { mutableStateOf("") }
    val provenance = remember { mutableStateOf("") }
    val owner = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val type = remember { mutableStateOf("") }
    val size = remember { mutableStateOf("") }
    val age = remember { mutableStateOf("") }
    val user = profileViewModel.userInfo.collectAsState().value.observeAsState()
    val token = user.value?.token
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val baos = ByteArrayOutputStream()
    bitmap.value?.compress(Bitmap.CompressFormat.JPEG, 100, baos) // bm is the bitmap object
    val b: ByteArray = baos.toByteArray()
    val encodedImage: String = Base64.getEncoder().encodeToString(b)
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }

    Column(
        modifier = Modifier
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {



        Spacer(modifier = Modifier.height(20.dp))

//        TextField(
//            label = { Text(text = "Proprietario/curante") },
//            value = owner.value,
//            visualTransformation = PasswordVisualTransformation(),
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//            onValueChange = { owner.value = it })

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Cittá") },
            value = provenance.value,
            onValueChange = { provenance.value = it }
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Razza") },
            value = type.value,
            onValueChange = { type.value = it })
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Etá") },
            value = age.value,
            onValueChange = { age.value = it })
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Descrizione") },
            value = description.value,
            onValueChange = { description.value = it })
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Microchip") },
            value = microchip.value,
            onValueChange = { microchip.value = it })
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Sesso") },
            value = sex.value,
            onValueChange = { sex.value = it })
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Nome") },
            value = name.value,
            onValueChange = { name.value = it })
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Taglia") },
            value = size.value,
            onValueChange = {size.value=it


            })
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Surface(
                modifier = Modifier
                    .size(154.dp)
                    .padding(5.dp),
                shape = CircleShape,
                border = BorderStroke(0.5.dp, Color.LightGray),
                elevation = 4.dp,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
            ) {
                imageUri?.let {
                    if (Build.VERSION.SDK_INT < 28) {
                        bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                    } else {
                        val source = ImageDecoder.createSource(context.contentResolver, it)
                        bitmap.value = ImageDecoder.decodeBitmap(source)
                    }

                    bitmap.value?.let { btm ->
                        Image(
                            bitmap = btm.asImageBitmap(),
                            contentDescription = "animal image",
                            modifier = Modifier.size(135.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
            Button(onClick = { launcher.launch("image/*") }) {
                Text(text = "Carica immagine")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround) {

            Button(onClick = {
                if(size.value.toLowerCase()=="grande"){
                    size.value = "3"
                }else if(size.value.toLowerCase()=="media"){
                    size.value = "2"
                }else if(size.value.toLowerCase()=="piccola"){
                    size.value = "1"
                }else {
                    size.value="2"
                }

                viewModel.addNewAnimal(token = token, animal = Animal(
                        id =(Math.random()*1000).toInt(),
                        name = name.value,
                        owner = user.value!!.username,
                        age = age.value.toInt(),
                        size =size.value.toInt(),
                        provenance = provenance.value,
                        microchip = microchip.value ,
                        description = description.value,
                        sex = sex.value,
                        type = type.value,
                        image =encodedImage
                        )
                )
                navController.navigate("profile")
            }
            ) {
                Text(text = "Aggiungi")
            }
            Spacer(modifier = Modifier.width(30.dp))

            Button(onClick = {
                navController.navigate("profile")
            }) {
                Text(text = "Cancella")
            }


        }
        Spacer(modifier = Modifier.height(60.dp))


    }
}