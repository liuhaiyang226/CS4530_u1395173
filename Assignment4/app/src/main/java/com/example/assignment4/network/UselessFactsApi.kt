package com.example.assignment4.network

import android.system.Os.accept
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class UselessFactsApi {
    // keep a single client (cheap here, but good habit)
    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true  // API adds fields sometimes
            })
        }
        install(Logging) {
            level = LogLevel.INFO
        }
        defaultRequest {
            accept(ContentType.Application.Json)
        }
    }

    suspend fun fetchRandomFact(language: String = "en"): RandomFactResponse {
        val url = "https://uselessfacts.jsph.pl/api/v2/facts/random?language=$language"
        return client.get(url).body()
    }
}

/**
 * API sometimes used "text", older versions had "fact".
 * Map both, pick whichever is present. Keep fields nullable just in case.
 */
@Serializable
data class RandomFactResponse(
    val id: String? = null,
    val language: String? = null,
    val source: String? = null,
    @SerialName("text") val text: String? = null,
    @SerialName("fact") val fact: String? = null
) {
    val factText: String
        get() = text ?: fact ?: "(no text)"
}