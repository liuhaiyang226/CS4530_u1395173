package com.example.assignment4.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FunFactDao {
    @Query("SELECT * FROM fun_facts ORDER BY savedAtMillis DESC")
    fun observeAll(): Flow<List<FunFact>>

    @Insert
    suspend fun insert(fact: FunFact)

    @Query("DELETE FROM fun_facts")
    suspend fun clearAll()

}