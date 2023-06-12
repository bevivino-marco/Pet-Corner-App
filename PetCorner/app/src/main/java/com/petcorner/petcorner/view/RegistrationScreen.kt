package com.petcorner.petcorner.view

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.DatePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.petcorner.petcorner.model.Profile
import com.petcorner.petcorner.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegistrationScreen(navController: NavHostController) {
    val viewModel = viewModel<ProfileViewModel>()
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val city = remember { mutableStateOf("") }
    val age = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }
    val providerId = "local"
    val cod_fisc = remember { mutableStateOf("") }
    val piva = remember { mutableStateOf("") }
    val country = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    val image = remember { mutableStateOf("") }
    var isSitter = false
    var isTrainer = false
    val coroutineScope = rememberCoroutineScope()
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val baos = ByteArrayOutputStream()
    bitmap.value?.compress(Bitmap.CompressFormat.JPEG, 100, baos) // bm is the bitmap object
    val b: ByteArray = baos.toByteArray()
    val encodedImage: String = Base64.getEncoder().encodeToString(b)
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }

    var visible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Nome") },
            value = name.value,
            onValueChange = { name.value = it })
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
            value = city.value,
            onValueChange = { city.value = it })
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Paese") },
            value = country.value,
            onValueChange = { country.value = it })
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Indirizzo") },
            value = address.value,
            onValueChange = { address.value = it })
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Codice Fiscale") },
            value = cod_fisc.value,
            onValueChange = { cod_fisc.value = it })
        /*Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Partita Iva") },
            value = piva.value,
            onValueChange = { piva.value = it })
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Etá") },
            value = age.value,
            onValueChange = { age.value = it })
        */
        Spacer(modifier = Modifier.height(20.dp))
//        val date = ShowDatePicker(LocalContext.current)
        Spacer(modifier = Modifier.height(20.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            isTrainer = TrainerCheckBox()
            isSitter = SitterCheckBox()
        }
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
                            contentDescription = "profile image",
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
        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
            Button(
                onClick = {

                    try{
                        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                        val p = Profile(
                            id=0,
                            name = name.value,
                            username = username.value,
//                            age = Period.between(LocalDate.parse(date, formatter), LocalDate.now()).years,
                            age = 18,
                            password = password.value,
                            cod_fisc = cod_fisc.value,
                            piva = "",
                            country = country.value,
                            city = city.value,
                            address = address.value,
                            image = encodedImage ,
                            providerId= "local",
                            token = "",
                            roles = emptyList()
                        )

                        if(isTrainer){
                            p.roles += "Trainer"
                        }
                        if(isSitter){
                            p.roles += "Sitter"
                        }
                        coroutineScope.launch { sendUser(p,viewModel) }
                        navController.navigate("login")
                    } catch (e: Exception){
                        visible = true
                    }
    },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Registrati")
            }
        }
        Spacer(modifier = Modifier.height(60.dp))
        MyText2(visible)
    }
}

@Composable
fun MyText2(isVisible: Boolean){
    if(isVisible){
        Text(
            "Ops, registrazione fallita: ccntrolla di aver inserito correttamente tutti i dati richiesti",
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Default
            )
        )
    }
}

//@Composable
//fun ShowDatePicker(context: Context): String {
//    val year: Int
//    val month: Int
//    val day: Int
//
//    val calendar = Calendar.getInstance()
//    year = calendar.get(Calendar.YEAR)
//    month = calendar.get(Calendar.MONTH)
//    day = calendar.get(Calendar.DAY_OF_MONTH)
//    calendar.time = Date()
//    var monthCorrect = month.toString()
//    var dayOfMonthCorrect = day.toString()
//    val date = remember { mutableStateOf("") }
//    val datePickerDialog = DatePickerDialog(
//        context,
//        {_: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
//
//            if(month.toString().length<2){
//                monthCorrect= "0"+monthCorrect
//            }
//
//            if(dayOfMonth.toString().length<2){
//                dayOfMonthCorrect= "0"+dayOfMonthCorrect
//            }
//
//            date.value = "$dayOfMonth/$month/$year"
//        }, year, month, day
//    )
//
//    Row(verticalAlignment = Alignment.CenterVertically) {
//        if (date.value == "") {
//            Text(text = "Data di nascita")
//            Spacer(modifier = Modifier.size(16.dp))
//        }
//        Button(
//            onClick = { datePickerDialog.show() },
//        ) {
//            Icon(
//                Icons.Filled.DateRange,
//                contentDescription = "Favorite",
//                modifier = Modifier.size(ButtonDefaults.IconSize)
//            )
//        }
//        if (date.value != "") {
//            Spacer(modifier = Modifier.size(16.dp))
//            Text(text = "${date.value}")
//        }
//    }
//    return date.value
//}




suspend fun sendUser(profile: Profile, viewModel: ProfileViewModel){
    viewModel.addUser(profile = profile )


}