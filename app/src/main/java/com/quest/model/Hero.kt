package com.quest.model

import android.util.Log
import androidx.annotation.DrawableRes
import com.quest.MainActivity
import com.quest.main.QuestInit
import kotlin.math.max
import kotlin.math.min


class Hero(
    st: Int,
    def: Int,
    fame: Int,
    coins: Int,
    gems: Int,
    cellIndex: Int,
    episodeIndex: Int,
    var chapterIndex: Int,
    t1: Int,
    t2: Int,
    t3: Int,
    d1: Int,
    d2: Int,
    d3: Int,
    @DrawableRes var idFon: Int,
    var margin: Int,
    var timeMills: Long
) {
    private lateinit var mainActivity: MainActivity

    constructor(args: java.util.ArrayList<Number>) : this(
        args[0].toInt(),
        args[1].toInt(),
        args[2].toInt(),
        args[3].toInt(),
        args[4].toInt(),
        args[5].toInt(),
        args[6].toInt(),
        args[7].toInt(),
        args[8].toInt(),
        args[9].toInt(),
        args[10].toInt(),
        args[11].toInt(),
        args[12].toInt(),
        args[13].toInt(),
        args[14].toInt(),
        args[15].toInt(),
        args[16].toLong(),
    )

    fun bind(mainActivity: MainActivity) {
        this.mainActivity = mainActivity
    }

    var questInit = QuestInit(this)

    var t = TempStats(t1, t2, t3)
    var d = DStats(d1, d2, d3)

    var onStrengthChange = arrayListOf<OnValueChange>()
    var onDefenceChange = arrayListOf<OnValueChange>()
    var onFameChange = arrayListOf<OnValueChange>()
    var onCoinsChangeListeners = arrayListOf<OnValueChange>()
    var onGemsChange = arrayListOf<OnValueChange>()
    var onEpisodeChange = arrayListOf<OnValueChange>()

    fun getId(): Int {
        return 1
    }

    init {
        questInit = QuestInit(this)
        t = TempStats(t1, t2, t3)
        d = DStats(d1, d2, d3)
        onStrengthChange = arrayListOf()
        onDefenceChange = arrayListOf()
        onFameChange = arrayListOf()
        onCoinsChangeListeners = arrayListOf()
        onGemsChange = arrayListOf()
        onEpisodeChange = arrayListOf()
    }

    var strength = st
        set(value) {
            val d = value - field
            field = value
            onStrengthChange.forEach { it.onChange(d) }
        }
    var defence = def
        set(value) {
            val d = value - field
            field = value
            onDefenceChange.forEach { it.onChange(d) }
        }
    var fame = fame
        set(value) {
            val d = value - field
            field = value
            onFameChange.forEach { it.onChange(d) }
        }

    var coins = coins
        set(value) {
            val d = value - field
            field = value
            onCoinsChangeListeners.forEach { it.onChange(d) }
        }

    var gems = gems
        set(value) {
            var newValue = value
            if (newValue > 3) {
                newValue = 3
            }
            if (newValue < 0) {
                newValue = 0
            }
            val d = newValue - field
            field = newValue
            onGemsChange.forEach { it.onChange(d) }
        }

    var cellIndex: Int = cellIndex
        get() {
            return if (field < 0) {
                0
            } else field
        }

    data class TempStats(var tStat1: Int, var tStat2: Int, var tStat3: Int)
    data class DStats(var dStat1: Int, var dStat2: Int, var dStat3: Int)

    var episodeIndex = episodeIndex
        set(value) {
            val newValue = min(value, chapter.episodes.size)
            val dIndex = newValue - field
            field = newValue
            d = DStats(strength - t.tStat1, defence - t.tStat2, fame - t.tStat3)
            t = TempStats(strength, defence, fame)
            onEpisodeChange.forEach { it.onChange(dIndex) }
            cellIndex = 0
            margin = 0
            Log.i("episodeIndex", "episode: $episodeIndex cell: $cellIndex")
        }


    val episode: Episode
        get() {
            return chapter.episodes[episodeIndex]
        }

    val chapter: Chapter
        get() {
            return questInit.chapters[chapterIndex]
        }

    var titersIndex = 0

    val episodeTiters: Episode
        get() {
            return questInit.episodeTiters
        }
}