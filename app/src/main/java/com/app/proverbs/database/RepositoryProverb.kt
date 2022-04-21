package com.app.proverbs.database

import androidx.lifecycle.LiveData
import com.app.proverbs.model.Proverb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepositoryProverb(private val daoProverb: DaoProverb) {
    val allProverbs: LiveData<List<Proverb>> = daoProverb.getAll()

    fun insertProverbs(proverbs: List<Proverb>) {
        CoroutineScope(Dispatchers.IO).launch {
            daoProverb.insertAll(proverbs)
        }
    }
}