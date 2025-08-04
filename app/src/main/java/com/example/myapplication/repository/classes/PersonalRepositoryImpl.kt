package com.example.myapplication.repository.classes

import com.example.myapplication.data.model.Personal
import com.example.myapplication.data.room.daos.PersonalDao
import com.example.myapplication.repository.interfaces.PersonalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PersonalRepositoryImpl @Inject constructor(
    private val dao: PersonalDao
) : PersonalRepository {

    override suspend fun insertAccount(account: Personal) = dao.insertAccount(account)


    override suspend fun updateAccount(account: Personal) = dao.updateAccount(account)


    override suspend fun deleteAccount(account: Personal) = dao.deleteAccount(account)


    override fun getAccount(): Flow<Personal> = dao.getAccount()
}