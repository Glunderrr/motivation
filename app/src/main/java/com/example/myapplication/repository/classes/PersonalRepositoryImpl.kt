package com.example.myapplication.repository.classes

import com.example.myapplication.data.model.Personal
import com.example.myapplication.data.room.daos.PersonalDao
import com.example.myapplication.repository.interfaces.PersonalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PersonalRepositoryImpl @Inject constructor(
    private val dao: PersonalDao
) : PersonalRepository {

    // Вставляє або оновлює запис особистих даних користувача в базі даних
    override suspend fun upsertPersonal(personal: Personal) = dao.upsertPersonal(personal)

    // Видаляє запис особистих даних користувача з бази даних
    override suspend fun deletePersonal(personal: Personal) = dao.deletePersonal(personal)

    // Повертає реактивний потік особистих даних поточного користувача
    override fun getPersonal(): Flow<Personal?> = dao.getPersonal()
}
