package com.quest.model

import java.util.ArrayList

class Chapter {
    var episodes: ArrayList<Episode> = arrayListOf()

    fun addEpisode(episode: Episode) {
        episodes.add(episode)
    }

    fun addAll(es: Array<Episode>){
        episodes.addAll(es)
    }
}