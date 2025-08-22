package com.example.myapplication.repository.interfaces

import com.example.myapplication.data.global_states.UserParametersState
import com.example.myapplication.data.model.Phrase

interface ApiPhraseRepository {
    suspend fun getMotivationalPhrase(theme: String, userParam: UserParametersState): Phrase
}