package com.app.proverbs.database

import androidx.lifecycle.LiveData
import com.app.proverbs.model.Proverb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepositoryProverb(private val daoProverb: DaoProverb) {
    val allProverbs: LiveData<List<Proverb>> = daoProverb.getAll()
    val favoriteProverbs: LiveData<List<Proverb>> = daoProverb.getFavorites()

    fun insertProverb(proverb: Proverb) {
        CoroutineScope(Dispatchers.IO).launch {
            daoProverb.insertNewProverb(proverb)
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

    fun removeProverb(proverb: Proverb) {
        CoroutineScope(Dispatchers.IO).launch {
            daoProverb.removeProverb(proverb)
        }
    }
}