package com.example.huckathon.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
@Serializable
data class ChatMessage(val role: String, val content: String)

@Serializable
data class ChatRequest(
    val model: String = "gpt-4o-mini",
    val messages: List<ChatMessage>
)

@Serializable
data class Choice(val index: Int, val message: ChatMessage)

@Serializable
data class ChatResponse(
    val id: String,
    val choices: List<Choice>
)

object OpenAIClient {
    private val client = HttpClient(CIO)
    private val json = Json { ignoreUnknownKeys = true }

    private const val ENDPOINT =
        "https://localeyes11.openai.azure.com/openai/deployments/gpt-4o-mini/chat/completions?api-version=2024-08-01-preview"

    private const val apiKey =
        "FiSavYGhiY7zWpyrJlrfOpMKxyAJ0fyjJGA9sTSwSRtb1WaObBWRJQQJ99BAAC5RqLJXJ3w3AAABACOGcxx0"

    suspend fun getSuggestion(prompt: String): String {
        val request = ChatRequest(messages = listOf(ChatMessage("user", prompt)))
        val response: HttpResponse = client.post(ENDPOINT) {
            header("api-key", apiKey)
            contentType(ContentType.Application.Json)
            setBody(json.encodeToString(request))
        }

        val body = response.bodyAsText()
        val chat = json.decodeFromString<ChatResponse>(body)
        return chat.choices.first().message.content.trim()
    }
}