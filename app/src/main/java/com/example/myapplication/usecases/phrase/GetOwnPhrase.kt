package com.example.myapplication.usecases.phrase

import com.example.myapplication.repository.interfaces.PhraseRepository
import javax.inject.Inject

class GetOwnPhrase @Inject constructor(
    private val repository: PhraseRepository
) {
    suspend fun invoke() = repository.getAllOwn()
}

