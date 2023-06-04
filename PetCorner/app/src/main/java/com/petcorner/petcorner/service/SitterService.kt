package com.petcorner.petcorner.service

import com.petcorner.petcorner.model.Sitter
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*


interface SitterService {
    suspend fun getSitters(): List<Sitter>

    suspend fun addSitter(sitter: Sitter, token : String): Boolean

    companion object{
        fun create():SitterService{
            return SitterServiceImpl(
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