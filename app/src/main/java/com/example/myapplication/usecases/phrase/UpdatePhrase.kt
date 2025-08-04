package com.example.myapplication.usecases.phrase

import com.example.myapplication.data.model.Phrase
import com.example.myapplication.repository.interfaces.PhraseRepository
import javax.inject.Inject

class UpdatePhrase @Inject constructor(
    private val repository: PhraseRepository
) {
    suspend operator fun invoke(vararg phrase: Phrase) = repository.update(*phrase)
}