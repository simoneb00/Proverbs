package com.app.proverbs.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.app.proverbs.model.Proverb

@Dao
interface DaoProverb {
    @Insert
    fun insertAll(proverbs: List<Proverb>)

    @Delete
    fun removeProverb(proverb: Proverb)

    @Delete
    fun removeProverbs(proverbs: List<Proverb>)

    @Update
    fun updateProverb(proverb: Proverb)

    @Query("SELECT * FROM Proverb ORDER BY text")
    fun getAll(): LiveData<List<Proverb>>

    @Query("SELECT * FROM Proverb WHERE favorite = 1 ORDER BY text")
    fun getFavorites(): LiveData<List<Proverb>>

    @Query("SELECT * FROM Proverb WHERE text LIKE :filter ORDER BY text")
    fun filterProverbs(filter: String): LiveData<List<Proverb>>
}