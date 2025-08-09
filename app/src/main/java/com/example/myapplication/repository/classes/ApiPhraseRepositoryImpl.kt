package com.example.myapplication.repository.classes

import com.example.myapplication.data.model.Phrase
import com.example.myapplication.data.model.network_model.ChatMessage
import com.example.myapplication.data.model.network_model.CompletionRequest
import com.example.myapplication.data.network.ApiService
import com.example.myapplication.repository.interfaces.ApiPhraseRepository
import javax.inject.Inject

class ApiPhraseRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ApiPhraseRepository {
    override suspend fun getMotivationalPhrase(theme: String): Phrase {
        val response = apiService.getMotivationalPhrases(
            CompletionRequest(
                model = "mistral-small-latest",
                messages = listOf(
                    ChatMessage(
                        role = "user",
                        content = "Generate **one** short motivational phrase in Ukrainian on the theme: $theme.  \n" +
                                "Do not add any explanations or introductions, only the phrase."
                    )
                )
            )
        )
        return response.choices[0].message.content.let { content ->
            Phrase(
                phrase = content,
                theme = theme,
                isOwn = false
            )
        }
    }
}