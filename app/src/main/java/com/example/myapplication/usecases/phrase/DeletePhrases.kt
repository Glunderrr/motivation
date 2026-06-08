package com.example.myapplication.usecases.phrase

import com.example.myapplication.data.model.Phrase
import com.example.myapplication.repository.interfaces.PhraseRepository
import javax.inject.Inject

class DeletePhrases @Inject constructor(
    private val phraseRepository: PhraseRepository
) {
    // Видаляє список фраз з локальної бази даних
    suspend fun invoke(phrase: List<Phrase>) {
        phraseRepository.deleteAll(phrase)
    }
}
