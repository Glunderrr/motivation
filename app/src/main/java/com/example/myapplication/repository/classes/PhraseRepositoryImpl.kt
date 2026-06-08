package com.example.myapplication.repository.classes

import com.example.myapplication.data.model.Phrase
import com.example.myapplication.data.room.daos.PhraseDao
import com.example.myapplication.repository.interfaces.PhraseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PhraseRepositoryImpl @Inject constructor(
    private val dao: PhraseDao
) : PhraseRepository {

    // Повертає реактивний потік усіх фраз із бази даних
    override fun getAll(): Flow<List<Phrase>> = dao.getAll()

    // Повертає реактивний потік лише улюблених фраз
    override fun getAllLiked(): Flow<List<Phrase>> = dao.getAllLiked()

    // Повертає реактивний потік лише власних фраз користувача
    override fun getAllOwn(): Flow<List<Phrase>> = dao.getAllOwn()

    // Оновлює один або кілька записів фраз у базі даних
    override suspend fun update(vararg phrase: Phrase) = dao.update(*phrase)

    // Вставляє нову або оновлює існуючу фразу в базі даних
    override suspend fun upsert(phrase: Phrase) = dao.upsert(phrase)

    // Видаляє список фраз із бази даних
    override suspend fun deleteAll(phrase: List<Phrase>) = dao.deleteAll(phrase)
}
