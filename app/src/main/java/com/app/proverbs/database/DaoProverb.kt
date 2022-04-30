package com.app.proverbs.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.app.proverbs.model.Proverb

@Dao
interface DaoProverb {

    @Insert
    fun insertNewProverb(proverb: Proverb)

    @Delete
    fun removeProverb(vararg proverb: Proverb)

    @Update
    fun updateProverb(proverb: Proverb)

    @Query("SELECT * FROM Proverb ORDER BY text")
    fun getAll(): LiveData<List<Proverb>>

    @Query("SELECT * FROM Proverb WHERE favorite = 1 ORDER BY text")
    fun getFavorites(): LiveData<List<Proverb>>

    @Query("SELECT * FROM Proverb WHERE text LIKE :filter ORDER BY text")
    fun filterProverbs(filter: String): LiveData<List<Proverb>>

    @Query("UPDATE Proverb SET favorite = 1 WHERE id = :id")
    fun addToFavorites(id: Int)

    @Query("UPDATE Proverb SET favorite = 0 WHERE id = :id")
    fun removeFromFavorites(id: Int)

    @Query("UPDATE Proverb SET text = :text where id = :id")
    fun updateProverbText(id: Int, text: String)
}