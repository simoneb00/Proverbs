package com.app.proverbs.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import com.app.proverbs.database.DBProverb
import com.app.proverbs.database.DaoProverb
import com.app.proverbs.database.RepositoryProverb
import com.app.proverbs.model.Proverb

class MainViewModel (application: Application) {
    val allProverbs: LiveData<List<Proverb>>
    val favoriteProverbs: LiveData<List<Proverb>>
    val dao: DaoProverb
    private val rep: RepositoryProverb

    init {
        val db = DBProverb.getInstance(application)
        dao = db.proverbiDAO()
        rep = RepositoryProverb(dao)

        allProverbs = rep.allProverbs
        favoriteProverbs = rep.favoriteProverbs
    }
}