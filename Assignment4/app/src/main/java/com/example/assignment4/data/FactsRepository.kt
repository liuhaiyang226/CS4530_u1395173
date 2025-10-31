package com.example.assignment4.data

import com.example.assignment4.network.UselessFactsApi
import kotlinx.coroutines.flow.Flow

/**
 * Single place the ViewModel talks to.
 * Very small on purpose for this assignment.
 */
class FactsRepository(
    private val dao: FunFactDao,
    private val api: UselessFactsApi
) {
    fun facts(): Flow<List<FunFact>> = dao.observeAll()

    suspend fun fetchAndSave() {
        val resp = api.fetchRandomFact(language = "en")
        val entity = FunFact(text = resp.factText)
        dao.insert(entity)
    }
    suspend fun clearAll() = dao.clearAll()

}