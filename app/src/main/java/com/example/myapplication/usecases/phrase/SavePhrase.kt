package com.example.myapplication.usecases.phrase

import com.example.myapplication.data.model.Phrase
import com.example.myapplication.repository.interfaces.PhraseRepository
import javax.inject.Inject

class SavePhrase @Inject constructor(
    private val repository: PhraseRepository
) {
    suspend fun invoke(phrase: Phrase) = repository.insert(phrase)
}