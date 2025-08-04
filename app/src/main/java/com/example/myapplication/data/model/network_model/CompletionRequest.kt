package com.example.myapplication.data.model.network_model


data class CompletionRequest(
    val model: String,
    val messages: List<ChatMessage>,
    val temperature: Double = 0.7
)