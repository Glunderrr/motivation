package com.example.myapplication.repository.classes

import com.example.myapplication.data.model.Personal
import com.example.myapplication.data.room.daos.PersonalDao
import com.example.myapplication.repository.interfaces.PersonalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PersonalRepositoryImpl @Inject constructor(
    private val dao: PersonalDao
) : PersonalRepository {
    override suspend fun upsertPersonal(personal: Personal) = dao.upsertPersonal(personal)

    override suspend fun deletePersonal(personal: Personal) = dao.deletePersonal(personal)


    override fun getPersonal(): Flow<Personal?> = dao.getPersonal()
}