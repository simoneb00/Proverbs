package com.app.proverbs.database

import androidx.lifecycle.LiveData
import com.app.proverbs.model.Proverb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepositoryProverb(private val daoProverb: DaoProverb) {
    val allProverbs: LiveData<List<Proverb>> = daoProverb.getAll()
    val favoriteProverbs: LiveData<List<Proverb>> = daoProverb.getFavorites()

    fun insertProverbs(proverbs: List<Proverb>) {
        CoroutineScope(Dispatchers.IO).launch {
            daoProverb.insertAll(proverbs)
        }
    }

    fun addToFavorites(proverb: Proverb) {
        CoroutineScope(Dispatchers.IO).launch {
            daoProverb.addToFavorites(proverb.id)
        }
    }

    fun removeFromFavorites(proverb: Proverb) {
        CoroutineScope(Dispatchers.IO).launch {
            daoProverb.removeFromFavorites(proverb.id)
        }
    }

    fun updateProverbText(proverb: Proverb, newText: String) {
        CoroutineScope(Dispatchers.IO).launch {
            daoProverb.updateProverbText(proverb.id, newText)
        }
    }
}