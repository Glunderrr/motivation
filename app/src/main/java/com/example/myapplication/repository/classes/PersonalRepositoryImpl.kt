package com.example.myapplication.repository.classes

import com.example.myapplication.data.model.Personal
import com.example.myapplication.data.room.daos.PersonalDao
import com.example.myapplication.repository.interfaces.PersonalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PersonalRepositoryImpl @Inject constructor(
    private val dao: PersonalDao
) : PersonalRepository {

    override suspend fun insertPersonal(account: Personal) = dao.insertPersonal(account)


    override suspend fun updatePersonal(account: Personal) = dao.updatePersonal(account)


    override suspend fun deletePersonal(account: Personal) = dao.deletePersonal(account)


    override fun getPersonal(): Flow<Personal?> = dao.getPersonal()
}