package com.example.myapplication.usecases.personal

import com.example.myapplication.data.model.Personal
import com.example.myapplication.repository.interfaces.PersonalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPersonalData @Inject constructor(
    private val personalRepository: PersonalRepository
) {
    fun invoke(): Flow<Personal?> =
        personalRepository.getPersonal()
}