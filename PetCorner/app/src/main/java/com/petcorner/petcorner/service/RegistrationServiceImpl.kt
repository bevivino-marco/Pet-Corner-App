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
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*


class RegistrationServiceImpl(private val httpClient: HttpClient): RegistrationService {


    override suspend fun getInfo(email:String, token: String): Profile? {
        return try {

            httpClient.get {
                url(HttpRoute.PROFILE_BASE_URL + "/user-info/${email}")
                headers {
                    append(HttpHeaders.Accept, "*/*")
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
            }

        } catch (e: ResponseException) {
            println("Error in the profile info call: ${e.message}")
            null
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun addUser(profile: Profile, path: String?) {
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
            .url(HttpRoute.PROFILE_BASE_URL+"/animal/add-animal-adopt-queue")
            .post(body)
            .build()
        val response = client.newCall(request).execute()
        println(response.message)
    }

//    @OptIn(InternalAPI::class)
//    @RequiresApi(Build.VERSION_CODES.O)
//    override suspend fun addUser(profile:Profile) {
//        val imageBytes = Base64.getDecoder().decode(profile.image)
//        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
//        val baos = ByteArrayOutputStream()
//        decodedImage.compress(Bitmap.CompressFormat.PNG, 100, baos)
//        val b = baos.toByteArray()
//
//
//        try {
//            httpClient.submitFormWithBinaryData<HttpResponse>(
//                url = HttpRoute.PROFILE_BASE_URL + "/user/save/add-user",
//                formData = formData {
//                    append("image", b)
//                    append("username",profile.username)
//                    append("name",profile.name)
//                    append("password",profile.password)
//                    append("providerId",profile.providerId)
//                    append("cod_fisc",profile.cod_fisc)
//                    append("country",profile.country)
//                    append("city",profile.city)
//                    append("address",profile.address)
//                    append("piva",profile.piva)
//                    append("age",profile.age)
//
//                }
//
//
//            )
//            println("CALL DONEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE")
//        }catch (e: Exception){
//            println("Error in the registration call: ${e.message}")
//
//        }
//    }

}