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
                            Age: ${userParam.personalState.value.age}, Gender: ${userParam.personalState.value.gender}, Region: ${userParam.personalState.value.region}
                            Personality: ${userParam.personalState.value.personality}, Emotional state: ${userParam.personalState.value.emotionalState}, Values: ${userParam.personalState.value.userValues}
                            Goal: ${userParam.personalState.value.mainGoal}, Field: ${userParam.personalState.value.field}, Challenges: ${userParam.personalState.value.challenges}, Experience in fields: ${userParam.personalState.value.experienceLevel}

                            Tone: ${userParam.personalState.value.tone}, Format: ${userParam.personalState.value.format}, Max length: ${userParam.personalState.value.maxLength} words, Address user: ${userParam.personalState.value.addressUser}

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