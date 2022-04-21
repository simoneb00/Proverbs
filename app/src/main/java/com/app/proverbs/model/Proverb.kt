package com.app.proverbs.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Proverb {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var text: String = ""
    var language: String = ""
    var category: Int = 0
    var favorite: Int = 0
}