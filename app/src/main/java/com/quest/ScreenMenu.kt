package com.quest

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.quest.main.hero
import com.quest.model.Action
import com.quest.model.Cell
import com.quest.model.CellDialog
import com.quest.model.OnValueChange
import com.thekingames.screen.Screen
import kotlinx.android.synthetic.main.menu.view.*
import kotlinx.android.synthetic.main.root.view.*


class ScreenMenu(parent: ViewGroup) : Screen(parent, R.layout.menu) {
    val a = activity as MainActivity
    lateinit var animationDown: Animation
    lateinit var animationTop: Animation

    init {
        animationDown = AnimationUtils.loadAnimation(a, R.anim.flip_down)
        animationTop = AnimationUtils.loadAnimation(a, R.anim.flip_top)
        fun isGameEnd(): Boolean = hero.episodeIndex ==  hero.chapter.episodes.size
        view.play.setOnClickListener {
            if (isGameEnd()) {
                a.src = a.episodeSourceTiters
                a.h.titersIndex = 0
                a.nextCell()
                statsAnimDown()
                return@setOnClickListener
            }
            a.src = a.episodeSourceMain
            if (a.h.cellIndex == 0) {
                if (a.h.gems == 0) {
                    if (!a.accessProvider) {
                        a.dialogWarning.show()
                    } else {
                        a.dialogTimer.show()
                    }
                } else if (a.h.gems >= 1) {
                    a.h.gems -= 1
                    a.nextCell()
                    statsAnimDown()
                }
            } else if (a.h.idFon != 0) {
                val resultCellIndex = a.h.cellIndex - a.h.margin
                if (a.h.episode.cells[resultCellIndex - 1]::class.java == CellDialog::class.java && a.h.margin > 0) {
                    Log.i("Loading", "Загрузка с шагом вперед")
                    a.h.cellIndex = resultCellIndex
                } else {
                    Log.i("Loading", "Обычная загрузка")
                    a.h.cellIndex = resultCellIndex - 1
                }
                a.h.margin = 0
                a.nextCell()
                statsAnimDown()
                a.setFon(a.h.idFon)
            }
        }
        view.sound.setOnClickListener{
            if (a.soundEnable) {
                view.sound.setImageResource(R.drawable.icon_music_off)
            } else {
                view.sound.setImageResource(R.drawable.icon_music_on)
            }
            a.soundEnable = !a.soundEnable
        }

        val i = Action {
            view.series.text = "${a.h.episodeIndex + 1}"
        }
        i.invoke()
        a.h.onEpisodeChange.add(OnValueChange
        {
            i.invoke()
        })

        view.authors.setOnClickListener{
            a.src = a.episodeSourceTiters
            a.h.titersIndex = 0
            a.nextCell()
            statsAnimDown()
        }

        view.s_bar.setOnClickListener{
            a.h.episodeIndex++
        }

        val colors = intArrayOf(
            Color.parseColor("#00FFFFFF"),
            Color.parseColor("#BF805638"),
            Color.parseColor("#BF805638"),
            Color.parseColor("#BF805638"),
            Color.parseColor("#00FFFFFF")
        )
        val gd = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors)

        gd.cornerRadius = 0f
        view.series_container.background = gd
    }

    override fun releaseDate() {
        super.releaseDate()
        val fons = arrayOf(R.drawable.fon_menu, R.drawable.fon_menu_2s)
        view.fon_menu.setBackgroundResource(fons[(hero.episodeIndex / 6) % 2])
    }

    private fun clearAnim() {
        //region clear anim
        a.viewStats.stat1.clearAnimation()
        a.viewStats.stat2.clearAnimation()
        a.viewStats.stat3.clearAnimation()
        a.viewStats.icon_stat1.clearAnimation()
        a.viewStats.icon_stat2.clearAnimation()
        a.viewStats.icon_stat3.clearAnimation()
        //endregion
    }

    fun statsAnimTop() {
        clearAnim()
        a.viewStats.startAnimation(animationTop)
    }

    fun statsAnimDown() {
        clearAnim()
        a.viewStats.startAnimation(animationDown)
    }

}