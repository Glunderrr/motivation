package com.example.myapplication.usecases.personal

import com.example.myapplication.data.model.Personal
import com.example.myapplication.repository.interfaces.PersonalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAccount @Inject constructor(
    private val personalRepository: PersonalRepository
) {
    suspend fun invoke(): Flow<Personal> =
        personalRepository.getAccount()
}