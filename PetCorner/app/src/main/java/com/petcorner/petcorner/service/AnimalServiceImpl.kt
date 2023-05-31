package com.petcorner.petcorner.service

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.StrictMode
import androidx.annotation.RequiresApi
import com.petcorner.petcorner.model.Animal
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.ByteArrayOutputStream
import java.util.*

class AnimalServiceImpl(private val httpClient: HttpClient): AnimalService {


    override suspend fun getAnimals(): List<Animal> {
        return try {

        httpClient.get {
            url(HttpRoute.ANIMAL_BASE_URL)
        }

        } catch (e: ResponseException){
            println("Error in the animals adopt API call: ${e.message}")
            emptyList<Animal>()
        }
    }

    override suspend fun getAnimalsForUser(email: String): List<Animal> {
        return try {

            httpClient.get {
                url(HttpRoute.ADOPT_BASE_URL+"/animals/owner/"+email)
            }

        } catch (e: ResponseException){
            println("Error in the animals adopt API call: ${e.message}")
            emptyList<Animal>()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun AddAnimal(animal: Animal, token: String) {

        val gfgPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(gfgPolicy)
        val imageBytes = Base64.getDecoder().decode(animal.image)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        val baos = ByteArrayOutputStream()
        decodedImage.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        val client = OkHttpClient()
        val mediaType = "text/plain".toMediaType()

        val body = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("file", animal.image)
            .addFormDataPart("age",animal.age.toString())
            .addFormDataPart("size", animal.size.toString() )
            .addFormDataPart("type", animal.type )
            .addFormDataPart("description", animal.description )
            .addFormDataPart("owner", animal.owner )
            .addFormDataPart("provenance", animal.provenance )
            .addFormDataPart("microchip", animal.microchip )
            .addFormDataPart("sex", animal.sex )
            .addFormDataPart("name", animal.name )
            .build()
        val request = Request.Builder()
            .url(HttpRoute.PROFILE_BASE_URL+"/animal/add-animal-adopt-queue")
            .post(body)
            .addHeader("Authorization", "Bearer $token")
            .build()
        val response = client.newCall(request).execute()
    }

    override suspend fun deleteAnimal(microchip: String, token: String) {
        val client = OkHttpClient()

        val body = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("param", microchip )

            .build()
        val request = Request.Builder()
            .url(HttpRoute.PROFILE_BASE_URL+"/animal/delete-animal-adopt-queue")
            .post(body)
            .addHeader("Authorization", "Bearer $token")
            .build()
        val response = client.newCall(request).execute()

    }


}


