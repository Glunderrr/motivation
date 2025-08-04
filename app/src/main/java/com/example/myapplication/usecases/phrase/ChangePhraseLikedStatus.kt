package com.example.myapplication.usecases.phrase

import com.example.myapplication.data.model.Phrase
import com.example.myapplication.repository.interfaces.PhraseRepository
import javax.inject.Inject

class ChangePhraseLikedStatus @Inject constructor(
    private val repository: PhraseRepository
) {
    suspend fun invoke(vararg phrase: Phrase, likeStatus: Boolean) {
        phrase.forEach {
            repository.update(it.copy(isLiked = likeStatus))
        }
    }
}