package com.example.myapplication.usecases.phrase

import com.example.myapplication.data.model.Phrase
import com.example.myapplication.repository.interfaces.PhraseRepository
import javax.inject.Inject

class UpsertPhrase @Inject constructor(
    private val repository: PhraseRepository
) {
    // Зберігає фразу в локальній базі даних, оновлюючи існуючий або створюючи новий запис
    suspend operator fun invoke(phrase: Phrase) = repository.upsert(phrase)
}
