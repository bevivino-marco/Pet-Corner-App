package com.petcorner.petcorner.service

import com.petcorner.petcorner.model.Profile
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*

interface RegistrationService {

    suspend fun getInfo(email:String, token:String): Profile?
    suspend fun addUser(profile: Profile, path: String?)

    companion object{
        fun create():RegistrationService{
            return RegistrationServiceImpl(
                httpClient = HttpClient(Android){

                    install(Logging){
                        level= LogLevel.ALL
                    }


                    install(JsonFeature){
                        serializer = KotlinxSerializer()
                    }
                }

            )
        }
    }

}