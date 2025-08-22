package com.example.myapplication.usecases.phrase

import com.example.myapplication.data.global_states.UserParametersState
import com.example.myapplication.data.model.Phrase
import com.example.myapplication.repository.interfaces.ApiPhraseRepository
import javax.inject.Inject

class GeneratePhrase @Inject constructor(
    private val apiPhraseRepository: ApiPhraseRepository
) {
    suspend operator fun invoke(theme: String, userParam: UserParametersState): Phrase {
        return apiPhraseRepository.getMotivationalPhrase(theme = theme, userParam = userParam)
    }
}