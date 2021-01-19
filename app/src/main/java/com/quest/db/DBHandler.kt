package com.quest.db

import com.quest.model.Hero

interface DBHandler {
    fun saveHero(hero: Hero)
    fun loadHero(id: Int): Hero?
    fun updateHero(hero: Hero): Int
    fun deleteHero(hero: Hero)
    fun deleteAll()

    val heroesCount: Int
}