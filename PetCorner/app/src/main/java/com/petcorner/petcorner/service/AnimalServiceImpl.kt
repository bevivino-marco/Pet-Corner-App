package com.petcorner.petcorner.service

import com.petcorner.petcorner.model.Animal
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*

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


}