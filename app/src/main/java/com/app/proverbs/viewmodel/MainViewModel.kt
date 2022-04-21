package com.app.proverbs.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import com.app.proverbs.database.DBProverb
import com.app.proverbs.database.RepositoryProverb
import com.app.proverbs.model.Proverb

class MainViewModel (application: Application) {
    val allProverbs: LiveData<List<Proverb>>
    val filteredProverbs: LiveData<List<Proverb>>
    private val rep: RepositoryProverb

    init {
        val db = DBProverb.getInstance(application)
        val dao = db.proverbiDAO()
        rep = RepositoryProverb(dao)

        allProverbs = rep.allProverbs
        filteredProverbs = allProverbs
    }
}