package com.example.myapplication.usecases.phrase

import com.example.myapplication.data.model.Phrase
import com.example.myapplication.repository.interfaces.ApiPhraseRepository
import javax.inject.Inject

class GeneratePhraseByTheme @Inject constructor(
    private val apiPhraseRepository: ApiPhraseRepository
) {
    suspend operator fun invoke(theme: String): Phrase {
        return apiPhraseRepository.getMotivationalPhrase(theme = theme)
    }
}