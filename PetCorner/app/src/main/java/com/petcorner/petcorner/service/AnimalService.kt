package com.petcorner.petcorner.service

import com.petcorner.petcorner.model.Animal
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*

interface AnimalService {

    suspend fun getAnimals(): List<Animal>
    suspend fun getAnimalsForUser(email: String): List<Animal>
    suspend fun AddAnimal(animal:Animal, token: String)

    companion object{
        fun create():AnimalService{
            return AnimalServiceImpl(
                httpClient = HttpClient(Android){

                    install(Logging){
                        level=LogLevel.ALL
                    }


                    install(JsonFeature.Feature){
                        serializer = KotlinxSerializer()
                    }
                }
            )
        }
    }

}