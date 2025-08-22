package com.example.myapplication.repository.classes

import com.example.myapplication.data.global_states.UserParametersState
import com.example.myapplication.data.model.Phrase
import com.example.myapplication.data.model.network_model.ChatMessage
import com.example.myapplication.data.model.network_model.CompletionRequest
import com.example.myapplication.data.network.ApiService
import com.example.myapplication.repository.interfaces.ApiPhraseRepository
import javax.inject.Inject

class ApiPhraseRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ApiPhraseRepository {
    override suspend fun getMotivationalPhrase(
        theme: String,
        userParam: UserParametersState
    ): Phrase {
        val response = apiService.getMotivationalPhrases(
            CompletionRequest(
                model = "mistral-small-latest",
                messages = listOf(
                    ChatMessage(
                        role = "user",
                        content =
                            """
                            Generate exactly one short motivational phrase in Ukrainian.

                            Theme: ${theme}
                            
                            User profile:
                            Age: ${userParam.state.value.age}, Gender: ${userParam.state.value.gender}, Region: ${userParam.state.value.region}
                            Personality: ${userParam.state.value.personality}, Emotional state: ${userParam.state.value.emotionalState}, Values: ${userParam.state.value.userValues}
                            Goal: ${userParam.state.value.mainGoal}, Field: ${userParam.state.value.field}, Challenges: ${userParam.state.value.challenges}, Experience: ${userParam.state.value.experienceLevel}

                            Tone: ${userParam.state.value.tone}, Format: ${userParam.state.value.format}, Max length: ${userParam.state.value.maxLength} words, Address user: ${userParam.state.value.addressUser}

                            Rules:
                            - Only natural modern Ukrainian, no archaic words.
                            - No explanations, introductions, or extra text — only the phrase itself.
                            """.trimIndent()
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