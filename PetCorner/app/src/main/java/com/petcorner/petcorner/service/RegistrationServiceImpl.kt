package com.petcorner.petcorner.service

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Environment
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import androidx.annotation.RequiresApi
import com.petcorner.petcorner.model.Profile
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*


class RegistrationServiceImpl(private val httpClient: HttpClient): RegistrationService {

    override suspend fun getInfo(email:String, token: String): Profile? {
        return try {
            var profile = httpClient.get<Profile> {
                url(HttpRoute.PROFILE_BASE_URL + "/user-info/${email}")
                headers {
                    append(HttpHeaders.Accept, "*/*")
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
            }
            profile.token = token
            profile
        } catch (e: ResponseException) {
            println("Error in the profile info call: ${e.message}")
            null
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun addUser(profile: Profile, path: String?) {
        try{
            val gfgPolicy = ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(gfgPolicy)
            val imageBytes = Base64.getDecoder().decode(profile.image)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            val baos = ByteArrayOutputStream()
            decodedImage.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val b = baos.toByteArray()
            val client = OkHttpClient()
            val mediaType = "text/plain".toMediaType()

            val body = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("image", profile.image)
                .addFormDataPart("address",profile.address)
                .addFormDataPart("city", profile.city )
                .addFormDataPart("country", profile.country )
                .addFormDataPart("cod_fisc", profile.cod_fisc )
                .addFormDataPart("providerId", profile.providerId )
                .addFormDataPart("password", profile.password )
                .addFormDataPart("provider", profile.providerId )
                .addFormDataPart("username", profile.username )
                .addFormDataPart("name", profile.name )
                .addFormDataPart("piva", profile.piva )
                .build()
            val request = Request.Builder()
                .url(HttpRoute.PROFILE_BASE_URL+"/user/save/add-user")
                .post(body)
                .build()
            val response = client.newCall(request).execute()
            println(response.message)
        } catch (e: Exception){
            println("Error while registration!")
        }
    }

    override suspend fun loginUser(username: String, psw: String): String? {
        return try {
            val gfgPolicy = ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(gfgPolicy)
            val client = OkHttpClient()
            val body = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("username", username)
                .addFormDataPart("password", psw)
                .build()
            val request = Request.Builder()
                .url(HttpRoute.PROFILE_BASE_URL + "/login")
                .post(body)
                .build()
            val response = client.newCall(request).execute()
            var json = JSONObject(response.body?.string())
            var token = json.getString("access_token")
            token
        } catch (e: Exception) {
            println("Error in the login call: ${e.message}")
            null
        }
    }
}