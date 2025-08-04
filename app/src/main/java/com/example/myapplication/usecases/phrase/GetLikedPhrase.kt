package com.example.myapplication.usecases.phrase

import com.example.myapplication.repository.interfaces.PhraseRepository
import javax.inject.Inject

class GetLikedPhrase @Inject constructor(
    private val repository: PhraseRepository
) {
    fun invoke() = repository.getAllLiked()
}

