package com.petcorner.petcorner.service

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.StrictMode
import com.petcorner.petcorner.model.Animal
import com.petcorner.petcorner.model.Sitter
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.text.get

class SitterServiceImpl(private val httpClient: HttpClient): SitterService {
    override suspend fun getSitters(): List<Sitter> {
        return try {
            httpClient.get {
                url(HttpRoute.SITTER_BASE_URL)
            }
        } catch (e: ResponseException){
            println("Error in the sitter API call: ${e.message}")
            emptyList<Sitter>()
        }
    }

    override suspend fun addSitter(sitter: Sitter, token : String): Boolean {
        val gfgPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(gfgPolicy)
        val client = OkHttpClient()
        val body = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("name", sitter.name)
            .addFormDataPart("surname", sitter.surname)
            .addFormDataPart("age", sitter.age.toString())
            .addFormDataPart("owner", "0")
            .addFormDataPart("locality", sitter.locality)
            .addFormDataPart("personalDescription", sitter.personalDescription)
            .addFormDataPart("animalsAllowed", sitter.animalsAllowed)
            .addFormDataPart("sizeAllowed", sitter.sizeAllowed.toString())
            .addFormDataPart("serviceOffered", sitter.serviceOffered)
            .addFormDataPart("email", sitter.email)
            .build()
        val request = Request.Builder()
            .url(HttpRoute.PROFILE_BASE_URL+"/animalSitter/add-animalSitter-queue")
            .post(body)
            .addHeader("Authorization", "Bearer $token")
            .build()
        val response = client.newCall(request).execute()
        println(response.message)
        return true;
    }
}