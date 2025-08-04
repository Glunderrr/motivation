package com.example.myapplication.data.model.network_model

data class CompletionResponse(
    val id: String,
    val model: String,
    val choices: List<Choice>
)

