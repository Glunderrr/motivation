package com.example.myapplication.usecases.phrase

import com.example.myapplication.data.model.Phrase
import com.example.myapplication.repository.interfaces.PhraseRepository
import javax.inject.Inject

class GetAllPhrases @Inject constructor(
    private val phrasesRepository: PhraseRepository
) {
    suspend operator fun invoke() = phrasesRepository.getAll()
}