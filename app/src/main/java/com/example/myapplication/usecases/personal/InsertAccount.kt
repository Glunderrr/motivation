package com.example.myapplication.usecases.personal

import com.example.myapplication.repository.interfaces.PersonalRepository
import javax.inject.Inject

class InsertAccount @Inject constructor(
    private val personalRepository: PersonalRepository
) {
    suspend fun invoke(account: com.example.myapplication.data.model.Personal) =
        personalRepository.insertAccount(account)
}