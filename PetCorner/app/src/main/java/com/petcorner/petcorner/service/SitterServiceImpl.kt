package com.petcorner.petcorner.service

import com.petcorner.petcorner.model.Animal
import com.petcorner.petcorner.model.Sitter
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
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
}