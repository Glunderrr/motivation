package com.example.myapplication.repository.classes

import com.example.myapplication.data.model.Phrase
import com.example.myapplication.data.room.daos.PhraseDao
import com.example.myapplication.repository.interfaces.PhraseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PhraseRepositoryImpl @Inject constructor(
    private val dao: PhraseDao
) : PhraseRepository {

    override fun getAll(): Flow<List<Phrase>> = dao.getAll()

    override fun getAllLiked(): Flow<List<Phrase>> = dao.getAllLiked()

    override fun getAllOwn(): Flow<List<Phrase>> = dao.getAllOwn()

    override suspend fun update(vararg phrase: Phrase) = dao.update(*phrase)

    override suspend fun upsert(phrase: Phrase) = dao.upsert(phrase)

    override suspend fun deleteAll(phrase: List<Phrase>) = dao.deleteAll(phrase)
}