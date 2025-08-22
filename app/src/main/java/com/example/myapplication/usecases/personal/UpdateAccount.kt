package com.example.myapplication.usecases.personal

import com.example.myapplication.data.model.Personal
import com.example.myapplication.repository.interfaces.PersonalRepository
import javax.inject.Inject

class UpdateAccount @Inject constructor(
    private val personalRepository: PersonalRepository
) {
    suspend fun invoke(persona: Personal) =
        personalRepository.upsertPersonal(persona)
}