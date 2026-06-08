package com.example.myapplication.data.network

import com.example.myapplication.data.model.network_model.CompletionRequest
import com.example.myapplication.data.model.network_model.CompletionResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    // Надсилає POST-запит до API Mistral AI для генерації мотиваційної фрази на основі вхідного промпту
    @POST("v1/chat/completions")
    suspend fun getMotivationalPhrases(
        @Body request: CompletionRequest
    ): CompletionResponse
}
