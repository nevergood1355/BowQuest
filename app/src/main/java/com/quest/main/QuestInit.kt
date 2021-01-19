package com.quest.main


import android.util.Log
import com.quest.R
import com.quest.model.*

lateinit var hero: Hero

/*Индекс кнопки, количество слайдов до диалога*/
fun getCycleAction(index: Int, l: Int): ActivityAction {
    return ActivityAction { a ->
        val cell = (a.h.episode.cells[a.h.cellIndex - l] as CellDialog)
        if (cell.idFonButtons.count { it == 0 } <= 1) {
            val j = (a.h.episode.cells.indexOf(cell))
            val startIndex = (j + 1)
            val endIndex = (j + l)
            for (i in startIndex..endIndex) {
                a.h.episode.cells.removeAt(startIndex)
                Log.i("M0", "El has been deleted i = $i")
            }
            a.h.cellIndex -= (l + 1);
            cell.idFonButtons[index] = 0;
        }
    }
}

/*Для 1 кнопки*/
fun cycleAction0(l: Int): ActivityAction {
    return getCycleAction(0, l)
}

/*Для 2 кнопки*/
fun cycleAction1(l: Int): ActivityAction {
    return getCycleAction(1, l)
}

/*Для 3 кнопки*/
fun cycleAction2(l: Int): ActivityAction {
    return getCycleAction(2, l)
}

val idFonButtonsTemplate = arrayOf(R.drawable.fon_button, R.drawable.fon_button, R.drawable.fon_button)
val emptyCosts = arrayOf(0, 0, 0)

class QuestInit(val holder: Hero) {
    val chapters = arrayListOf<Chapter>()
    lateinit var episodeTiters: Episode
    var idFon: Int = 0

    private val ci
        get() = holder.cellIndex;

    private fun initTiters() {
        val episodeT = Episode()
        episodeT.addCell(R.string.ch1_e_c0, ActivityAction {
            it.setFon(R.drawable.fon_town, false)
        })
            .addCell(R.string.ch1_e_c1_m)
            .addHeroCellRight(R.drawable.hero_takko, R.string.ch1_e_c2_a)
            .addHeroCellLeft(R.drawable.hero_veren, R.string.ch1_e_c3_t)
            .addHeroCellRight(R.drawable.hero_magraf, R.string.ch1_e_c4_v)
            .addHeroCellLeft(R.drawable.hero_agnet, R.string.ch1_e_c5_k)
            .addHeroCellRight(R.drawable.hero_kaysa, R.string.ch1_e_c6_j)
            .addCell(R.string.ch1_e_c8)
            .addCell(R.string.ch1_e_c7)
            .addCell(R.string.ch1_e_c9)

        episodeTiters = episodeT
    }

    init {
        hero = holder
        val chapter1 = Chapter()
        initTiters()

        val episode0 = Episode()
        episode0.addCell(R.string.ch1_e0_c0, ActivityAction {
            it.setFon(R.drawable.fon_town)
            it.mp = it.trackFon
        })
            .addCell(R.string.ch1_e0_c1)
            .addCell(R.string.ch1_e0_c2)
            .addCell(R.string.ch1_e0_c3)
            .addCell(R.string.ch1_e0_c4)
            .addCell(R.string.ch1_e0_c4_1)
            .addHeroCellRight(R.drawable.hero_veren, R.string.ch1_e0_c5_v)
            .addHeroCellRight(R.drawable.hero_veren, R.string.ch1_e0_c6_v)
            .addHeroCellRight(R.drawable.hero_veren, R.string.ch1_e0_c6_v1)
            .addDialogCell(
                R.string.ch1_e0_c7_t, arrayOf(R.string.ch1_e0_c7_v1, R.string.ch1_e0_c7_v2, R.string.ch1_e0_c7_v3), arrayOf(
                    {
                        hero.defence++
                        episode0.addHeroCellLeftHere(ci, R.drawable.hero_takko, R.string.ch1_e0_c8_v1_t)
                    }, {
                        hero.strength++
                        episode0.addHeroCellLeftHere(ci, R.drawable.hero_takko, R.string.ch1_e0_c8_v2_t)
                    }, {

                    }
                ))
            .addCell(R.string.ch1_e0_c9)
            .addHeroCellRight(R.drawable.hero_veren, R.string.ch1_e0_c10_v)
            .addHeroCellRight(R.drawable.hero_veren, R.string.ch1_e0_c11_v)
            .addDialogCell(
                R.string.ch1_e0_c12_t, arrayOf(R.string.ch1_e0_c13_v1, R.string.ch1_e0_c13_v2, R.string.ch1_e0_c13_v3), arrayOf(
                    {
                        hero.fame++
                    }, {
                        hero.fame--
                    }, {

                    }
                ))
            .addCell(R.string.ch1_e0_c14)
            .addCell(R.string.ch1_e0_c14_v1)
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e0_c17_t)
            .addHeroCellRight(R.drawable.hero_veren, R.string.ch1_e0_c18_v)
            .addCell(R.string.ch1_e0_c19, ActivityAction {
                it.setFon(R.drawable.fon_yard)
            })
            .addHeroCellLeft(R.drawable.hero_takko_wonder, R.string.ch1_e0_c20_t)
            .addHeroCellRight(R.drawable.hero_veren, R.string.ch1_e0_c21_v)
            .addCell(R.string.ch1_e0_c22)
            .addHeroCellRight(R.drawable.hero_veren, R.string.ch1_e0_c23_v)
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e0_c23_t)
            .addHeroCellRight(R.drawable.hero_veren, R.string.ch1_e0_c23_v1_t)
            .addCell(R.string.ch1_e0_c24, ActivityAction {
                it.setFon(R.drawable.fon_town)
            })
            .addHeroCellRight(R.drawable.hero_veren, R.string.ch1_e0_c25_v)
            .addHeroCellRight(R.drawable.hero_veren, R.string.ch1_e0_c26_v)
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e0_c27_t)
            .addDialogCell(
                R.string.ch1_e0_c29, arrayOf(R.string.ch1_e0_c29_v1, R.string.ch1_e0_c29_v2), arrayOf(
                    {
                        episode0.addCellHere(ci, R.string.ch1_e0_c30_v1)
                            .addHeroCellLeftHere(ci + 1, R.drawable.hero_takko, R.string.ch1_e0_c31_v1_t)
                            .addHeroCellRightHere(ci + 2, R.drawable.hero_ditmar, R.string.ch1_e0_c32_v1_d, ActivityAction {
                                it.setPath(R.string.path_fame_l)
                            })
                            .addDialogCellHere(ci + 3, R.string.ch1_e0_c33, arrayOf(R.string.ch1_e0_c34_v1, R.string.ch1_e0_c34_v2), arrayOf(
                                {
                                    episode0.addHeroCellLeftHere(ci, R.drawable.hero_takko, R.string.ch1_e0_c35_v1_d)
                                        .addHeroCellRightHere(ci + 1, R.drawable.hero_ditmar, R.string.ch1_e0_c36_v1_d)
                                        .addHeroCellLeftHere(ci + 2, R.drawable.hero_takko, R.string.ch1_e0_c37_v1_t,
                                            ActivityAction {
                                                it.setFon(R.drawable.fon_town)
                                                cycleAction0(7).invoke(it)
                                            })
                                }, {
                                    episode0.addHeroCellLeftHere(ci, R.drawable.hero_takko, R.string.ch1_e0_c37_v1_t,
                                        ActivityAction {
                                            it.setFon(R.drawable.fon_town)
                                            cycleAction0(5).invoke(it)
                                        })
                                }
                            ))

                    }, {
                        episode0.addCellHere(ci, R.string.ch1_e0_c38_v2)
                            .addHeroCellLeftHere(ci + 1, R.drawable.hero_takko, R.string.ch1_e0_c39_v2_t, ActivityAction {
                                it.setFon(R.drawable.fon_home_veren)
                            })
                            .addHeroCellRightHere(ci + 2, R.drawable.hero_widow, R.string.ch1_e0_c40_v2_h, ActivityAction {
                                it.setPath(R.string.path_fame_l)
                            })
                            .addDialogCellHere(ci + 3, R.string.ch1_e0_c41_v2_t, arrayOf(R.string.ch1_e0_c41_v2_v1, R.string.ch1_e0_c41_v2_v2), arrayOf(
                                {
                                    episode0.addHeroCellLeftHere(ci, R.drawable.hero_takko, R.string.ch1_e0_c41_v1, ActivityAction {
                                        it.setFon(R.drawable.fon_town)
                                        cycleAction1(5).invoke(it)
                                    })
                                }, {
                                    episode0.addHeroCellLeftHere(ci, R.drawable.hero_takko, R.string.ch1_e0_c42_v2_t)
                                        .addHeroCellLeftHere(ci + 1, R.drawable.hero_takko, R.string.ch1_e0_c43_v2_t)
                                        .addHeroCellRightHere(ci + 2, R.drawable.hero_widow, R.string.ch1_e0_c44_v2_w)
                                        .addHeroCellLeftHere(ci + 3, R.drawable.hero_takko, R.string.ch1_e0_c41_v1, ActivityAction {
                                            it.setFon(R.drawable.fon_town)
                                            cycleAction1(8).invoke(it)
                                        })
                                }
                            ))
                    }
                ))
            .addCell(R.string.ch1_e0_c43_1, ActivityAction {
                it.setFon(R.drawable.fon_bakery)
            })
            .addCell(R.string.ch1_e0_c43)
            .addCell(R.string.ch1_e0_c44)
            .addCell(R.string.ch1_e0_c45)
            .addCell(R.string.ch1_e0_c46)
            .addHeroCellRight(R.drawable.hero_kaysa, R.string.ch1_e0_c47_k)
            .addCell(R.string.ch1_e0_c48)
            .addCell(R.string.ch1_e0_c49)
            .addHeroCellRight(R.drawable.hero_gg, R.string.ch1_e0_c50_gg)
            .addHeroCellLeft(R.drawable.hero_takko_smile, R.string.ch1_e0_c51_t)
            .addCell(R.string.ch1_e0_c52)
            .addCell(R.string.ch1_e0_c53)
            .addCell(R.string.ch1_e0_c54, ActivityAction { it.setFon(R.drawable.fon_bakery) })
            .addCell(R.string.ch1_e0_c56)
            .addDialogCell(
                R.string.ch1_e0_c57, arrayOf(R.string.ch1_e0_c57_v1, R.string.ch1_e0_c57_v2, R.string.ch1_e0_c57_v3), arrayOf(
                    {

                    }, {
                        episode0.addHeroCellLeftHere(ci, R.drawable.hero_takko, R.string.ch1_e0_c58_t)
                            .addHeroCellRightHere(ci + 1, R.drawable.hero_kaysa, R.string.ch1_e0_c59_k)
                            .addHeroCellLeftHere(ci + 2, R.drawable.hero_takko, R.string.ch1_e0_c60_t)
                            .addHeroCellLeftHere(ci + 3, R.drawable.hero_takko, R.string.ch1_e0_c61_t)
                            .addCellHere(ci + 4, R.string.ch1_e0_c62)
                            .addHeroCellLeftHere(ci + 5, R.drawable.hero_takko, R.string.ch1_e0_c63_t)
                            .addHeroCellRightHere(ci + 6, R.drawable.hero_kaysa, R.string.ch1_e0_c64_k)
                            .addCellHere(ci + 7, R.string.ch1_e0_c65)
                            .addDialogCellHere(ci + 4,
                                R.string.ch1_e0_c66_t, arrayOf(R.string.ch1_e0_c66_v1, R.string.ch1_e0_c66_v2), arrayOf(
                                    {
                                        episode0.addHeroCellRightHere(ci, R.drawable.hero_kaysa, R.string.ch1_e0_c67_k)
                                            .addHeroCellLeftHere(ci + 1, R.drawable.hero_takko, R.string.ch1_e0_c68_t)
                                    }, {
                                        episode0.addHeroCellLeftHere(ci, R.drawable.hero_takko, R.string.ch1_e0_c68_t)
                                    }
                                ))
                            .addHeroCellRightHere(ci + 5, R.drawable.hero_kaysa, R.string.ch1_e0_c69_k)
                            .addHeroCellRightHere(ci + 6, R.drawable.hero_kaysa, R.string.ch1_e0_c70_k, ActivityAction {
                                hero.fame++
                            })
                    }, {
                        episode0.addCellHere(ci, R.string.ch1_e0_c62)
                            .addHeroCellLeftHere(ci + 1, R.drawable.hero_takko, R.string.ch1_e0_c63_t)
                            .addHeroCellRightHere(ci + 2, R.drawable.hero_kaysa, R.string.ch1_e0_c64_k)
                            .addCellHere(ci + 3, R.string.ch1_e0_c65)
                            .addDialogCellHere(ci + 4,
                                R.string.ch1_e0_c66_t, arrayOf(R.string.ch1_e0_c66_v1, R.string.ch1_e0_c66_v2), arrayOf(
                                    {
                                        episode0.addHeroCellRightHere(ci, R.drawable.hero_kaysa, R.string.ch1_e0_c67_k)
                                            .addHeroCellLeftHere(ci + 1, R.drawable.hero_takko, R.string.ch1_e0_c68_t)
                                    }, {
                                        episode0.addHeroCellLeftHere(ci, R.drawable.hero_takko, R.string.ch1_e0_c68_t)
                                    }
                                ))
                            .addHeroCellRightHere(ci + 5, R.drawable.hero_kaysa, R.string.ch1_e0_c69_k)
                            .addHeroCellRightHere(ci + 6, R.drawable.hero_kaysa, R.string.ch1_e0_c70_k, ActivityAction {
                                hero.fame++
                            })
                    }
                ))

            .addDialogCell(
                R.string.ch1_e0_c71, arrayOf(R.string.ch1_e0_c71_v1, R.string.ch1_e0_c71_v2, R.string.ch1_e0_c71_v3), arrayOf(
                    {
                        episode0.addCellHere(ci, R.string.ch1_e0_c72)
                            .addHeroCellLeftHere(ci + 1, R.drawable.hero_takko, R.string.ch1_e0_c73_t)
                            .addHeroCellRightHere(ci + 2, R.drawable.hero_ditmar, R.string.ch1_e0_c74_d, ActivityAction {
                                it.setPath(R.string.path_fame_h)
                            })
                            .addDialogCellHere(ci + 3,
                                R.string.ch1_e0_c75_t, arrayOf(R.string.ch1_e0_c75_v1, R.string.ch1_e0_c75_v2, R.string.ch1_e0_c75_v3), arrayOf(
                                    {
                                        hero.defence++
                                        episode0.addHeroCellRightHere(ci, R.drawable.hero_ditmar, R.string.ch1_e0_c76_d, cycleAction0(5))
                                    }, {
                                        episode0.addHeroCellLeftHere(ci, R.drawable.hero_takko, R.string.ch1_e0_c77_t, cycleAction0(5))
                                    }, {
                                        hero.strength++
                                        episode0.addHeroCellLeftHere(ci, R.drawable.hero_takko_rage, R.string.ch1_e0_c78_t, cycleAction0(5))
                                    }
                                ))
                    }, {
                        episode0.addCellHere(ci, R.string.ch1_e0_c79, ActivityAction {
                            it.setFon(R.drawable.fon_yard)
                        })
                            .addHeroCellLeftHere(ci + 1, R.drawable.hero_takko, R.string.ch1_e0_c80_t)
                            .addHeroCellRightHere(ci + 2, R.drawable.hero_magraf, R.string.ch1_e0_c81_m, ActivityAction {
                                it.setPath(R.string.path_fame_h)
                            })
                            .addDialogCellHere(ci + 3,
                                R.string.ch1_e0_c81_t, arrayOf(R.string.ch1_e0_c81_v1, R.string.ch1_e0_c81_v2), arrayOf(
                                    {
                                        hero.fame++
                                        episode0.addHeroCellLeftHere(ci, R.drawable.hero_takko, R.string.ch1_e0_c82_t, cycleAction1(5))
                                    }, {
                                        hero.fame--
                                        episode0.addHeroCellLeftHere(ci, R.drawable.hero_takko, R.string.ch1_e0_c83_t, cycleAction1(5))
                                    })
                            )
                    }, {
                        episode0.addCellHere(ci, R.string.ch1_e0_c84, ActivityAction { it.setFon(R.drawable.fon_home_veren) })
                            .addHeroCellLeftHere(ci + 1, R.drawable.hero_takko, R.string.ch1_e0_c85_t)
                            .addHeroCellRightHere(ci + 2, R.drawable.hero_widow, R.string.ch1_e0_c86, ActivityAction {
                                it.setPath(R.string.path_fame_h)
                                cycleAction2(3).invoke(it)
                            })
                    }
                ), ActivityAction { it.setFon(R.drawable.fon_town) })
            .addCell(R.string.ch1_e0_c87)
            .addCell(BaseCell())

        val episode1 = Episode()
        episode1
            .addCell(R.string.ch1_e1_c0, ActivityAction { it.setFon(R.drawable.fon_home_veren) })
            .addCell(R.string.ch1_e1_c1)
            .addCell(R.string.ch1_e1_c2)
            .addCell(R.string.ch1_e1_c3)
            .addCell(R.string.ch1_e1_c4)
            .addDialogCell(
                R.string.ch1_e1_c5, arrayOf(R.string.ch1_e1_c5_v1, R.string.ch1_e1_c5_v2), arrayOf(
                    {
                        hero.strength++
                        episode1.addCellHere(ci, R.string.ch1_e1_c6_v1)
                    }, {
                        hero.defence++
                        episode1.addCellHere(ci, R.string.ch1_e1_c6_v2)
                    })
            )
            .addCell(
                R.string.ch1_e1_c7
            )
            .addDialogCell(
                R.string.ch1_e1_c8, arrayOf(R.string.ch1_e1_c8_v1, R.string.ch1_e1_c8_v2), arrayOf({}, {})
            )
            .addCell(
                R.string.ch1_e1_c10
            )
            .addSelectItemCell(
                R.string.ch1_e1_c11, R.string.ch1_e1_c11_b, arrayOf(
                    Item(
                        R.drawable.item_bow1, R.string.ch1_e1_c11_v1
                    ), Item(
                        R.drawable.item_bow2, R.string.ch1_e1_c11_v2
                    ), Item(
                        R.drawable.item_bow3, R.string.ch1_e1_c11_v3, {
                            hero.coins -= 5
                            hero.fame++
                        }, 5, R.drawable.fon_button_gold
                    )
                )
            )
            .addCell(R.string.ch1_e1_c12)
            .addCell(R.string.ch1_e1_c13)
            .addDialogCell(
                R.string.ch1_e1_c14, arrayOf(
                    R.string.ch1_e1_c14_v1, R.string.ch1_e1_c14_v2
                ), arrayOf({
                    hero.defence++
                    episode1.addCellHere(ci, R.string.ch1_e1_c15_v1)
                }, {
                    hero.strength++
                    episode1.addCellHere(ci, R.string.ch1_e1_c15_v2)
                })
            )
            .addHeroCellRight(R.drawable.hero_veren, R.string.ch1_e1_c16)
            .addHeroCellLeft(R.drawable.hero_takko_smile, R.string.ch1_e1_c17)
            .addHeroCellRight(R.drawable.hero_veren, R.string.ch1_e1_c18)
            .addDialogCell(
                R.string.ch1_e1_c19, arrayOf(
                    R.string.ch1_e1_c19_v1, R.string.ch1_e1_c19_v2, R.string.ch1_e1_c19_v3
                ), arrayOf({
                    hero.fame++
                    episode1.addHeroCellLeftHere(
                        ci, R.drawable.hero_takko, R.string.ch1_e1_c20_v1
                    )
                }, {
                    hero.fame--
                    episode1.addHeroCellLeftHere(
                        ci, R.drawable.hero_takko_wonder, R.string.ch1_e1_c20_v2
                    )
                }, { })
            )
            .addCell(R.string.ch1_e1_c21)
            .addHeroCellRight(R.drawable.hero_veren, R.string.ch1_e1_c22)
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e1_c23)
            .addHeroCellRight(R.drawable.hero_veren, R.string.ch1_e1_c24)
            .addDialogCell(
                R.string.ch1_e1_c25, arrayOf(R.string.ch1_e1_c25_v1, R.string.ch1_e1_c25_v2, R.string.ch1_e1_c25_v3), arrayOf({
                    hero.strength++
                    episode1.addHeroCellLeftHere(
                        ci, R.drawable.hero_takko_rage, R.string.ch1_e1_c26_v1
                    )
                }, {
                    hero.defence++
                    episode1.addHeroCellLeftHere(
                        ci, R.drawable.hero_takko_smile, R.string.ch1_e1_c26_v2
                    )
                }, {})
            )
            .addCell(R.string.ch1_e1_c27)
            .addCell(R.string.ch1_e1_c28)
            .addCell(R.string.ch1_e1_c29)
            .addHeroCellRight(R.drawable.hero_kaysa, R.string.ch1_e1_c30)
            .addDialogCell(
                R.string.ch1_e1_c32, arrayOf(
                    R.string.ch1_e1_c32_v1, R.string.ch1_e1_c32_v2, R.string.ch1_e1_c32_v3
                ), arrayOf({
                    episode1.addHeroCellLeftHere(
                        ci, R.drawable.hero_takko_smile, R.string.ch1_e1_c33_v1
                    )
                }, {
                    episode1.addHeroCellLeftHere(
                        ci, R.drawable.hero_takko_smile, R.string.ch1_e1_c33_v2
                    )
                }, {
                    episode1.addCellHere(ci, R.string.ch1_e1_c33_v3)
                })
            )
            .addHeroCellRight(R.drawable.hero_veren, R.string.ch1_e1_c35)
            .addCell(R.string.ch1_e1_c36)
            .addCell(R.string.ch1_e1_c37)
            .addHeroCellRight(R.drawable.hero_veren, R.string.ch1_e1_c38)
            .addCell(R.string.ch1_e1_c40)
            .addCell(R.string.ch1_e1_c41)
            .addCell(R.string.ch1_e1_c42)
            .addHeroCellRight(R.drawable.hero_kaysa, R.string.ch1_e1_c45)
            .addHeroCellRight(R.drawable.hero_veren, R.string.ch1_e1_c46)
            .addHeroCellRight(R.drawable.hero_veren, R.string.ch1_e1_c47)
            .addCell(R.string.ch1_e1_c49)
            .addHeroCellLeft(R.drawable.hero_takko_wonder, R.string.ch1_e1_c50)
            .addHeroCellRight(R.drawable.hero_veren, R.string.ch1_e1_c51)
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e1_c52)
            .addHeroCellRight(R.drawable.hero_veren, R.string.ch1_e1_c53)
            .addCell(R.string.ch1_e1_c54)
            .addCell(R.string.ch1_e1_c55)
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e1_c56_t)
            .addCell(R.string.ch1_e1_c57)
            .addHeroCellRight(R.drawable.hero_veren, R.string.ch1_e1_c58_v)
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e1_c59_t)
            .addHeroCellRight(R.drawable.hero_veren, R.string.ch1_e1_c60_v)
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e1_c61_t)
            .addCell(R.string.ch1_e1_c62)
            .addHeroCellRight(R.drawable.hero_veren, R.string.ch1_e1_c63_v)
            .addDialogCell(R.string.ch1_e1_c64_t, arrayOf(R.string.ch1_e1_c64_v1, R.string.ch1_e1_c64_v2, R.string.ch1_e1_c64_v3), arrayOf({
                hero.defence++
                episode1.addHeroCellLeftHere(ci, R.drawable.hero_takko, R.string.ch1_e1_c65_v1)
            }, {
                hero.strength++
                episode1.addHeroCellLeftHere(ci, R.drawable.hero_takko_rage, R.string.ch1_e1_c65_v2)
            }, {}))
            .addCell(R.string.ch1_e1_c66)
            .addCell(R.string.ch1_e1_c67, ActivityAction {
                it.mp = it.trackLove
            })
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e1_c68_t)
            .addDialogCell(R.string.ch1_e1_c69, arrayOf(R.string.ch1_e1_c70_v1, R.string.ch1_e1_c70_v2, R.string.ch1_e1_c70_v3), arrayOf({
                hero.fame--
                episode1.addHeroCellLeftHere(ci, R.drawable.hero_takko, R.string.ch1_e1_c71_v1)
            }, {
                episode1.addHeroCellLeftHere(ci, R.drawable.hero_takko_smile, R.string.ch1_e1_c71_v2)
                episode1.addHeroCellRightHere(ci + 1, R.drawable.hero_kaysa, R.string.ch1_e1_c71_v2_v1)
                episode1.addDialogCellHere(ci + 2, R.string.ch1_e1_c69, arrayOf(R.string.ch1_e1_c72_v1, R.string.ch1_e1_c72_v2, R.string.ch1_e1_c72_v3), arrayOf({
                    hero.fame++
                    hero.coins -= 10
                    episode1.addCellHere(ci, R.string.ch1_e1_c73_v1)
                    episode1.addCellHere(ci + 1, R.string.ch1_e1_c74)
                    episode1.addCellHere(ci + 2, R.string.ch1_e1_c75)
                    episode1.addCellHere(ci + 3, R.string.ch1_e1_c76)
                    episode1.addCellHere(ci + 4, R.string.ch1_e1_c77)
                }, {
                    episode1.addCellHere(ci, R.string.ch1_e1_c73_v2)
                }, {
                    episode1.addCellHere(ci, R.string.ch1_e1_c73_v3)
                }), arrayOf(R.drawable.fon_button_gold, R.drawable.fon_button, R.drawable.fon_button), arrayOf(10, 0, 0))
            }))
            .addCell(BaseCell())

        val episode2 = Episode()
        episode2
            .addCell(R.string.ch1_e2_c0, ActivityAction {
                it.setFon(R.drawable.fon_home_veren)
            })
            .addCell(R.string.ch1_e2_c34, ActivityAction {
                it.mp = it.trackTown
                it.setFon(R.drawable.fon_town)
            })
            .addCell(R.string.ch1_e2_c36)
            .addCell(R.string.ch1_e2_c37, ActivityAction {
                it.mp = it.trackRoom
                it.setFon(R.drawable.fon_room)
            })
            .addCell(R.string.ch1_e2_c38)
            .addHeroCellRight(R.drawable.hero_magraf, R.string.ch1_e2_c43_m)
            .addDialogCell(
                R.string.ch1_e2_c44, arrayOf(R.string.ch1_e2_c44_v1, R.string.ch1_e2_c44_v2, R.string.ch1_e2_c44_v3), arrayOf({
                    hero.strength++
                    episode2.addHeroCellLeftHere(
                        ci, R.drawable.hero_takko, R.string.ch1_e2_c45_v1_t
                    )
                }, {
                    hero.fame--
                    episode2.addHeroCellLeftHere(
                        ci, R.drawable.hero_takko, R.string.ch1_e2_c45_v2_t
                    )
                }, {
                    hero.defence++
                })
            )
            .addCell(R.string.ch1_e2_c46)
            .addCell(R.string.ch1_e2_c47)
            .addSelectItemCell(R.string.ch1_e2_c48, R.string.ch1_e2_c48_b, arrayOf(Item(R.drawable.item_picture, R.string.ch1_e2_c48_v1, {})))
            .addHeroCellRight(R.drawable.hero_magraf, R.string.ch1_e2_c52_m)
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e2_c53_t)
            .addHeroCellRight(R.drawable.hero_magraf, R.string.ch1_e2_c54_m)
            .addCell(R.string.ch1_e2_c55)
            .addCell(R.string.ch1_e2_c56)
            .addHeroCellRight(R.drawable.hero_agnet, R.string.ch1_e2_c57_a)
            .addHeroCellRight(R.drawable.hero_magraf, R.string.ch1_e2_c61_m)
            .addHeroCellRight(R.drawable.hero_agnet_sadness, R.string.ch1_e2_c61_a)
            .addCell(R.string.ch1_e2_c62)
            .addDialogCell(
                R.string.ch1_e2_c63_t, arrayOf(R.string.ch1_e2_c63_v1, R.string.ch1_e2_c63_v2, R.string.ch1_e2_c63_v3), arrayOf({
                    hero.fame++
                    Unit
                }, {
                    hero.fame--
                    episode2.addHeroCellLeftHere(
                        ci, R.drawable.hero_takko_rage, R.string.ch1_e2_c64_v2
                    )
                }, {})
            )
            .addCell(R.string.ch1_e2_c66)
            .addHeroCellRight(R.drawable.hero_magraf, R.string.ch1_e2_c67_m)
            .addHeroCellRight(R.drawable.hero_magraf, R.string.ch1_e2_c68_m)
            .addDialogCell(
                R.string.ch1_e2_c69_t, arrayOf(R.string.ch1_e2_c69_v1, R.string.ch1_e2_c69_v2, R.string.ch1_e2_c69_v3), arrayOf({
                    hero.fame++
                    episode2.addHeroCellLeftHere(
                        ci, R.drawable.hero_takko, R.string.ch1_e2_c70_v1_t
                    )
                }, {
                    hero.fame--
                    episode2.addHeroCellLeftHere(
                        ci, R.drawable.hero_takko, R.string.ch1_e2_c70_v2_t
                    )
                }, {})
            )
            .addHeroCellRight(R.drawable.hero_magraf, R.string.ch1_e2_c71_m)
            .addHeroCellRight(R.drawable.hero_magraf_sadness, R.string.ch1_e2_c72_m)
            .addHeroCellRight(R.drawable.hero_magraf, R.string.ch1_e2_c73_m)
            .addCell(R.string.ch1_e2_c74, ActivityAction {
                if (hero.defence >= hero.strength) {
                    it.setPath(R.string.path_stat2)
                    episode2.addHeroCellLeftHere(ci + 2, R.drawable.hero_takko, R.string.ch1_e2_c75_o2_t)
                    episode2.addHeroCellRightHere(ci + 3, R.drawable.hero_magraf, R.string.ch1_e2_c75_o3_m)
                } else {
                    it.setPath(R.string.path_stat1)
                    episode2.addHeroCellLeftHere(ci + 1, R.drawable.hero_takko, R.string.ch1_e2_c75_s2_t)
                }
            })
            .addCell(R.string.ch1_e2_c76)
            .addCell(R.string.ch1_e2_c77)
            .addCell(R.string.ch1_e2_c78)
            .addCell(R.string.ch1_e2_c79)
            .addCell(R.string.ch1_e2_c80, ActivityAction {
                it.mp = it.trackTown
                it.setFon(R.drawable.fon_town)
            })
            .addCell(R.string.ch1_e2_c81)
            .addCell(R.string.ch1_e2_c82)
            .addCell(R.string.ch1_e2_c83, ActivityAction {
                it.mp = it.trackStress
                it.blood()
            })
            .addCell(R.string.ch1_e2_c86)
            .addDialogCell(
                R.string.ch1_e2_c87_t, arrayOf(R.string.ch1_e2_c87_v1, R.string.ch1_e2_c87_v2, R.string.ch1_e2_c87_v3), arrayOf({
                    hero.strength++
                    episode2.addHeroCellLeftHere(
                        ci, R.drawable.hero_takko_rage, R.string.ch1_e2_c88_v1
                    )
                }, {
                    episode2.addHeroCellLeftHere(
                        ci, R.drawable.hero_takko_smile, R.string.ch1_e2_c88_v2
                    )
                }, { hero.defence++ })
            )
            .addHeroCellRight(R.drawable.hero_worker, R.string.ch1_e2_c89_w)
            .addCell(R.string.ch1_e2_c90)
            .addCell(R.string.ch1_e2_c91, ActivityAction {
                it.blood()
            })
            .addHeroCellRight(R.drawable.hero_worker, R.string.ch1_e2_c92_w)
            .addCell(R.string.ch1_e2_c93)
            .addCell(R.string.ch1_e2_c94)
            .addHeroCellRight(R.drawable.hero_worker, R.string.ch1_e2_c95_w)
            .addCell(R.string.ch1_e2_c96)
            .addDialogCell(
                R.string.ch1_e2_c97_t, arrayOf(R.string.ch1_e2_c97_v1, R.string.ch1_e2_c97_v2, R.string.ch1_e2_c97_v3), arrayOf({
                    hero.strength++

                }, {
                    hero.defence++

                }, {}), idFonButtonsTemplate, emptyCosts, true, ActivityAction {
                    if (hero.fame > 0) {
                        it.setPath(R.string.path_fame_h)
                        episode2.addCellHere(ci + 1, R.string.ch1_e2_c98_f_h)
                    } else {
                        it.setPath(R.string.path_fame_l)
                        episode2.addCellHere(ci + 1, R.string.ch1_e2_c98_f_l, ActivityAction {
                            it.blood()
                        })
                    }
                }
            )
            .addCell(R.string.ch1_e2_c99, ActivityAction {
                it.mp = it.trackTown
            })
            .addCell(R.string.ch1_e2_c100)
            .addCell(R.string.ch1_e2_c101)
            .addCell(R.string.ch1_e2_c102)
            .addCell(BaseCell())
        val episode3 = Episode()
        episode3
            .addCell(R.string.ch1_e3_c0, ActivityAction {
                it.setFon(R.drawable.fon_bakery)
                it.mp = it.trackFon
            })
            .addCell(R.string.ch1_e3_c25, ActivityAction {
                it.setFon(R.drawable.fon_home_veren)
                it.mp = it.trackFon
            })
            .addHeroCellRight(R.drawable.hero_kaysa, R.string.ch1_e3_c27_k)
            .addHeroCellRight(R.drawable.hero_veren, R.string.ch1_e3_c28_v)
            .addDialogCell(
                R.string.ch1_e3_c29_t, arrayOf(R.string.ch1_e3_c29_v1, R.string.ch1_e3_c29_v2, R.string.ch1_e3_c29_v3), arrayOf({
                    hero.strength++
                    episode3.addHeroCellLeftHere(ci, R.drawable.hero_takko_smile, R.string.ch1_e3_c30_v1_t)
                }, {
                    hero.fame++
                    episode3.addHeroCellLeftHere(ci, R.drawable.hero_takko_smile, R.string.ch1_e3_c30_v2_t)
                }, {
                    hero.defence++
                })
            )
            .addHeroCellRight(R.drawable.hero_veren, R.string.ch1_e3_c31_v)
            .addHeroCellLeft(R.drawable.hero_takko_wonder, R.string.ch1_e3_c32_t)
            .addHeroCellRight(R.drawable.hero_veren, R.string.ch1_e3_c33_v)
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e3_c34_t)
            .addHeroCellRight(R.drawable.hero_veren, R.string.ch1_e3_c35_v)
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e3_c36_t)
            .addHeroCellRight(R.drawable.hero_veren, R.string.ch1_e3_c37_v)
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e3_c38_t)
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e3_c39_t)
            .addHeroCellRight(R.drawable.hero_veren, R.string.ch1_e3_c40_v)
            .addDialogCell(
                R.string.ch1_e3_c41_t, arrayOf(R.string.ch1_e3_c41_v1, R.string.ch1_e3_c41_v2), arrayOf({
                    episode3.addHeroCellLeftHere(ci, R.drawable.hero_takko, R.string.ch1_e3_c42_v1)

                }, {
                    episode3.addHeroCellLeftHere(ci, R.drawable.hero_takko, R.string.ch1_e3_c42_v2)

                })
            )
            .addCell(R.string.ch1_e3_c47)
            .addCell(R.string.ch1_e3_c48, ActivityAction { it.mp = it.trackLove })
            .addDialogCell(
                R.string.ch1_e3_c49_t, arrayOf(R.string.ch1_e3_c49_v1, R.string.ch1_e3_c49_v2, R.string.ch1_e3_c49_v3), arrayOf({
                    episode3.addHeroCellLeftHere(ci, R.drawable.hero_takko, R.string.ch1_e3_c50_v1_t)
                }, {
                    episode3.addHeroCellLeftHere(ci, R.drawable.hero_takko, R.string.ch1_e3_c50_v2_t)
                }, {

                })
            )
            .addCell(R.string.ch1_e3_c54)
            .addCell(R.string.ch1_e3_c55)
            .addHeroCellRight(R.drawable.hero_kaysa, R.string.ch1_e3_c56_k)
            .addDialogCell(
                R.string.ch1_e3_c60_t, arrayOf(R.string.ch1_e3_c60_v1, R.string.ch1_e3_c60_v2, R.string.ch1_e3_c60_v3), arrayOf({
                    episode3.addHeroCellLeftHere(ci, R.drawable.hero_takko, R.string.ch1_e3_c61_v1_t)
                }, {
                    episode3.addHeroCellLeftHere(ci, R.drawable.hero_takko, R.string.ch1_e3_c61_v2_t)
                }, {
                    episode3.addHeroCellLeftHere(ci, R.drawable.hero_takko, R.string.ch1_e3_c61_v3_t)
                })
            )
            .addHeroCellRight(R.drawable.hero_kaysa, R.string.ch1_e3_c62_k)
            .addDialogCell(
                R.string.ch1_e3_c63_t, arrayOf(R.string.ch1_e3_c63_v1, R.string.ch1_e3_c63_v2, R.string.ch1_e3_c63_v3), arrayOf({
                    episode3.addHeroCellLeftHere(ci, R.drawable.hero_takko_smile, R.string.ch1_e3_c64_v1)

                }, {
                    episode3.addHeroCellLeftHere(ci, R.drawable.hero_takko_smile, R.string.ch1_e3_c64_v2)

                }, {
                    episode3.addHeroCellLeftHere(ci, R.drawable.hero_takko_smile, R.string.ch1_e3_c64_v3)

                }), idFonButtonsTemplate, emptyCosts, false, ActivityAction {
                    if (hero.fame <= 0) {
                        episode3.addCellHere(ci + 1, R.string.ch1_e3_c65_f_l1, ActivityAction {
                            it.setPath(R.string.path_fame_l)
                        })
                        episode3.addHeroCellRightHere(
                            ci + 2, R.drawable.hero_kaysa, R.string.ch1_e3_c65_f_l2_k
                        )
                    } else {
                        episode3.addCellHere(ci + 1, R.string.ch1_e3_c65_f_h1, ActivityAction {
                            it.setPath(R.string.path_fame_h)
                        })
                    }
                }
            )
            .addCell(R.string.ch1_e3_c66, ActivityAction { it.mp = it.trackFon })
            .addCell(R.string.ch1_e3_c66_2)
            .addHeroCellRight(R.drawable.hero_veren, R.string.ch1_e3_c67_v)
            .addDialogCell(R.string.ch1_e3_c68_t, arrayOf(R.string.ch1_e3_c68_v1, R.string.ch1_e3_c68_v2, R.string.ch1_e3_c68_v3), arrayOf(
                {
                    episode3.addHeroCellLeftHere(ci, R.drawable.hero_takko, R.string.ch1_e3_c69_v1)
                }, {
                    episode3.addHeroCellLeftHere(ci, R.drawable.hero_takko, R.string.ch1_e3_c69_v2)
                }, {

                })
            )
            .addCell(R.string.ch1_e3_c71)
            .addCell(R.string.ch1_e3_c72)
            .addCell(R.string.ch1_e3_c73)
            .addHeroCellRight(R.drawable.hero_veren, R.string.ch1_e3_c76_v)
            .addHeroCellRight(R.drawable.hero_veren, R.string.ch1_e3_c77_v)
            .addCell(R.string.ch1_e3_c78)
            .addCell(R.string.ch1_e3_c79)
            .addSelectItemCell(
                R.string.ch1_e3_c80, R.string.ch1_e3_c80_b, arrayOf(
                    Item(R.drawable.item_bowstring1, R.string.ch1_e3_c80_v1), Item(R.drawable.item_bowstring2, R.string.ch1_e3_c80_v2), Item(
                        R.drawable.item_bowstring3, R.string.ch1_e3_c80_v3, { hero.fame++;hero.coins -= 5 }, 5, R.drawable.fon_button_gold
                    )
                )
            )
            .addHeroCellRight(R.drawable.hero_veren, R.string.ch1_e3_c81_v)
            .addDialogCell(R.string.ch1_e3_c82_t, arrayOf(R.string.ch1_e3_c82_v1, R.string.ch1_e3_c82_v2, R.string.ch1_e3_c82_v3), arrayOf(
                {
                    episode3.addHeroCellLeftHere(ci, R.drawable.hero_takko, R.string.ch1_e3_c83_v1)
                }, {
                    episode3.addHeroCellLeftHere(ci, R.drawable.hero_takko, R.string.ch1_e3_c83_v2)
                }, {
                    episode3.addHeroCellLeftHere(ci, R.drawable.hero_takko, R.string.ch1_e3_c83_v3)
                })
            )
            .addCell(R.string.ch1_e3_c87)
            .addHeroCellLeft(R.drawable.hero_takko_smile, R.string.ch1_e3_c88_t)
            .addCell(R.string.ch1_e3_c89)
            .addSelectItemCell(
                R.string.ch1_e3_c91, R.string.ch1_e3_c91_b, arrayOf(
                    Item(R.drawable.item_quiver1, R.string.ch1_e3_c91_v1), Item(R.drawable.item_quiver2, R.string.ch1_e3_c91_v2), Item(
                        R.drawable.item_quiver3, R.string.ch1_e3_c91_v3, { hero.fame++;hero.coins -= 5 }, 5, R.drawable.fon_button_gold
                    )
                )
            )
            .addCell(R.string.ch1_e3_c92)
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e3_c97_t)
            .addCell(BaseCell())

        //episode4
        val episode4 = Episode()
        episode4
            .addCell(R.string.ch1_e4_c0, ActivityAction {
                it.setFon(R.drawable.fon_town)
                it.mp = it.trackTown
            })
            .addCell(R.string.ch1_e4_c1)
            .addCell(R.string.ch1_e4_c2)
            .addHeroCellLeft(R.drawable.hero_takko_smile, R.string.ch1_e4_c3_t)
            .addCell(R.string.ch1_e4_c4, ActivityAction {
                it.setFon(R.drawable.fon_room)
                it.mp = it.trackRoom
            })
            .addDialogCell(
                R.string.ch1_e4_c5_t, arrayOf(R.string.ch1_e4_c5_v1, R.string.ch1_e4_c5_v2, R.string.ch1_e4_c5_v3), arrayOf({
                    episode4.addHeroCellLeftHere(ci, R.drawable.hero_takko, R.string.ch1_e4_c6_v1_t)
                    hero.fame++

                }, {
                    episode4.addHeroCellLeftHere(ci, R.drawable.hero_takko_rage, R.string.ch1_e4_c6_v2_t)
                    hero.fame--

                }, {})
            )
            .addCell(R.string.ch1_e4_c7)
            .addHeroCellRight(R.drawable.hero_ditmar, R.string.ch1_e4_c8_d)
            .addDialogCell(
                R.string.ch1_e4_c9_t, arrayOf(R.string.ch1_e4_c9_v1, R.string.ch1_e4_c9_v2, R.string.ch1_e4_c9_v3), arrayOf({
                    episode4.addHeroCellLeftHere(ci, R.drawable.hero_takko, R.string.ch1_e4_c10_v1_t)
                    hero.strength++

                }, {
                    episode4.addHeroCellLeftHere(ci, R.drawable.hero_takko, R.string.ch1_e4_c10_v2_t)
                    hero.defence++

                }, {})
            )
            .addHeroCellRight(R.drawable.hero_ditmar, R.string.ch1_e4_c11_d)
            .addHeroCellRight(R.drawable.hero_ditmar, R.string.ch1_e4_c12_d)
            .addHeroCellRight(R.drawable.hero_ditmar, R.string.ch1_e4_c13_d, ActivityAction {
                if (hero.strength >= hero.defence) {
                    episode4.addHeroCellLeftHere(
                        ci + 1, R.drawable.hero_takko, R.string.ch1_e4_c14_s_t, ActivityAction {
                            it.setPath(R.string.path_stat1)
                        })
                } else {
                    episode4.addHeroCellLeftHere(
                        ci + 1, R.drawable.hero_takko, R.string.ch1_e4_c14_o_t, ActivityAction {
                            it.setPath(R.string.path_stat2)
                        })
                }
            })
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e4_c16_t)
            .addHeroCellRight(R.drawable.hero_ditmar, R.string.ch1_e4_c17_d)
            .addDialogCell(
                R.string.ch1_e4_c18, arrayOf(R.string.ch1_e4_c18_v1, R.string.ch1_e4_c18_v2, R.string.ch1_e4_c18_v3), arrayOf({
                    episode4.addHeroCellLeftHere(ci, R.drawable.hero_takko_rage, R.string.ch1_e4_c19_v1)
                    hero.strength++

                }, {
                    episode4.addHeroCellLeftHere(ci, R.drawable.hero_takko_smile, R.string.ch1_e4_c19_v2)
                    hero.fame--

                }, {
                    hero.defence++

                })
            )
            .addCell(R.string.ch1_e4_c20)
            .addCell(R.string.ch1_e4_c21)
            .addHeroCellRight(R.drawable.hero_ditmar, R.string.ch1_e4_c22_d)
            .addCell(R.string.ch1_e4_c23, ActivityAction {
                it.setFon(R.drawable.fon_room)
            })
            .addHeroCellRight(R.drawable.hero_magraf, R.string.ch1_e4_c24, ActivityAction {
                if (hero.defence > hero.strength) {
                    episode4.addHeroCellRightHere(
                        ci + 1, R.drawable.hero_takko, R.string.ch1_e4_c25_o_t, ActivityAction { it.setPath(R.string.path_stat2) })
                } else {
                    episode4.addHeroCellLeftHere(
                        ci + 1, R.drawable.hero_takko, R.string.ch1_e4_c25_s1_t, ActivityAction { it.setPath(R.string.path_stat1) })
                    episode4.addHeroCellRightHere(ci + 2, R.drawable.hero_ditmar, R.string.ch1_e4_c25_s2_d)
                }
            })
            .addCell(R.string.ch1_e4_c26)
            .addCell(R.string.ch1_e4_c27)
            .addCell(R.string.ch1_e4_c28)
            .addHeroCellRight(R.drawable.hero_magraf, R.string.ch1_e4_c29_m)
            .addDialogCell(
                R.string.ch1_e4_c30_t, arrayOf(R.string.ch1_e4_c30_v1, R.string.ch1_e4_c30_v2, R.string.ch1_e4_c30_v3), arrayOf({
                    episode4.addHeroCellLeftHere(
                        ci, R.drawable.hero_takko_sadness, R.string.ch1_e4_c31_v1
                    )
                    hero.fame--

                }, {
                    episode4.addHeroCellLeftHere(ci, R.drawable.hero_takko, R.string.ch1_e4_c31_v2)
                    hero.strength++

                }, {
                    hero.defence++

                })
            )
            .addCell(R.string.ch1_e4_c32)
            .addHeroCellRight(R.drawable.hero_magraf, R.string.ch1_e4_c33_m)
            .addCell(R.string.ch1_e4_c34)
            .addHeroCellRight(R.drawable.hero_magraf, R.string.ch1_e4_c35_m)
            .addCell(R.string.ch1_e4_c36, ActivityAction {
                it.setFon(R.drawable.fon_yard)
                it.mp = it.trackTown
            })
            .addDialogCell(
                R.string.ch1_e4_c37_t, arrayOf(R.string.ch1_e4_c37_v1, R.string.ch1_e4_c37_v2, R.string.ch1_e4_c37_v3), arrayOf({
                    episode4.addHeroCellLeftHere(ci, R.drawable.hero_takko, R.string.ch1_e4_c38_v1_v2_1)
                    hero.fame++

                }, {
                    episode4.addHeroCellLeftHere(ci, R.drawable.hero_takko, R.string.ch1_e4_c38_v1_v2_1)

                }, {

                })
            )
            .addCell(R.string.ch1_e4_c40)
            .addHeroCellRight(R.drawable.hero_agnet_smile, R.string.ch1_e4_c41_a)
            .addCell(R.string.ch1_e4_c42)
            .addHeroCellRight(R.drawable.hero_magraf_smile, R.string.ch1_e4_c43_m)
            .addCell(R.string.ch1_e4_c44)
            .addCell(R.string.ch1_e4_c45)
            .addCell(R.string.ch1_e4_c46)
            .addCell(R.string.ch1_e4_c47)
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e4_c48_t)
            .addCell(R.string.ch1_e4_c49)
            .addDialogCell(
                R.string.ch1_e4_c50_t, arrayOf(R.string.ch1_e4_c50_v1, R.string.ch1_e4_c50_v2, R.string.ch1_e4_c50_v3), arrayOf({
                    episode4.addCellHere(ci, R.string.ch1_e4_c51_v1)
                }, {
                    episode4.addCellHere(ci, R.string.ch1_e4_c51_v2)
                    hero.fame--
                }, {
                    episode4.addCellHere(ci, R.string.ch1_e4_c51_v3)
                    hero.fame--
                }), idFonButtonsTemplate, emptyCosts, true
            )
            .addHeroCellRight(R.drawable.hero_magraf_smile, R.string.ch1_e4_c52_m)
            .addCell(R.string.ch1_e4_c53)
            .addHeroCellRight(R.drawable.hero_magraf, R.string.ch1_e4_c54_m)
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e4_c55_t)
            .addHeroCellRight(R.drawable.hero_magraf, R.string.ch1_e4_c56_m)
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e4_c57_t)
            .addHeroCellRight(R.drawable.hero_magraf, R.string.ch1_e4_c58_m)
            .addDialogCell(
                R.string.ch1_e4_c59_t, arrayOf(R.string.ch1_e4_c59_v1, R.string.ch1_e4_c59_v2, R.string.ch1_e4_c59_v3), arrayOf({
                    hero.fame++
                }, {}, { hero.fame-- }
                )
            )
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e4_c60_t)
            .addHeroCellRight(R.drawable.hero_magraf, R.string.ch1_e4_c61_m)
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e4_c62_t)
            .addHeroCellRight(R.drawable.hero_magraf, R.string.ch1_e4_c63_m)
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e4_c64_t)
            .addHeroCellRight(R.drawable.hero_magraf_wonder, R.string.ch1_e4_c65_m)
            .addCell(R.string.ch1_e4_c66)
            .addHeroCellRight(R.drawable.hero_magraf, R.string.ch1_e4_c67_m)
            .addCell(R.string.ch1_e4_c68, ActivityAction {
                it.setFon(R.drawable.fon_room)
                it.mp = it.trackRoom
            })
            .addHeroCellRight(R.drawable.hero_magraf, R.string.ch1_e4_c69_m)
            .addHeroCellLeft(R.drawable.hero_takko_sadness, R.string.ch1_e4_c70_t)
            .addDialogCell(
                R.string.ch1_e4_c71_t, arrayOf(R.string.ch1_e4_c71_v1, R.string.ch1_e4_c71_v2, R.string.ch1_e4_c71_v3), arrayOf({
                    hero.fame++
                    hero.coins -= 5
                    episode4.addCellHere(ci, R.string.ch1_e4_c72_v1_1)
                    episode4.addCellHere(ci + 1, R.string.ch1_e4_c73_v1_2)
                    episode4.addCellHere(ci + 2, R.string.ch1_e4_c74_v1_3)
                    episode4.addCellHere(ci + 3, R.string.ch1_e4_c75_v1_v2_1)
                    episode4.addCellHere(ci + 4, R.string.ch1_e4_c75_v1_v2_2)
                }, {
                    episode4.addCellHere(ci, R.string.ch1_e4_c75_v1_v2_1)
                    episode4.addCellHere(ci + 1, R.string.ch1_e4_c75_v1_v2_2)
                }, {
                    hero.fame--
                }), arrayOf(R.drawable.fon_button_gold, R.drawable.fon_button, R.drawable.fon_button), arrayOf(5, 0, 0)
            )
            .addHeroCellRight(R.drawable.hero_magraf, R.string.ch1_e4_c76_m)
            .addHeroCellRight(R.drawable.hero_magraf, R.string.ch1_e4_c77_m)
            .addHeroCellRight(R.drawable.hero_magraf, R.string.ch1_e4_c78_m)
            .addHeroCellRight(R.drawable.hero_magraf, R.string.ch1_e4_c79_m)
            .addCell(R.string.ch1_e4_c80)
            .addCell(R.string.ch1_e4_c81)
            .addCell(R.string.ch1_e4_c82)
            .addCell(R.string.ch1_e4_c83)
            .addCell(R.string.ch1_e4_c84)
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e4_c85_t)
            .addCell(R.string.ch1_e4_c86)
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e4_c87_t)
            .addHeroCellRight(R.drawable.hero_magraf, R.string.ch1_e4_c88_m)
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e4_c89_t)
            .addCell(R.string.ch1_e4_c90)
            .addHeroCellRight(R.drawable.hero_magraf, R.string.ch1_e4_c91_m, ActivityAction {
                if (holder.strength > holder.defence) {
                    episode4.addHeroCellLeftHere(
                        ci + 1, R.drawable.hero_takko, R.string.ch1_e4_c92_s_t, ActivityAction { it.setPath(R.string.path_stat1) })
                } else {
                    episode4.addCellHere(
                        ci + 1, R.string.ch1_e4_c92_o, ActivityAction { it.setPath(R.string.path_stat2) })

                }
            })
            .addCell(R.string.ch1_e4_c93)
            .addHeroCellRight(R.drawable.hero_magraf, R.string.ch1_e4_c94_m)
            .addCell(R.string.ch1_e4_c95, ActivityAction {
                it.setFon(R.drawable.fon_town)
                it.mp = it.trackTown
            })
            .addCell(R.string.ch1_e4_c96)
            .addCell(R.string.ch1_e4_c97)
            .addCell(R.string.ch1_e4_c98, ActivityAction {
                it.mp = it.trackStress
            })
            .addCell(R.string.ch1_e4_c99)
            .addDialogCell(
                R.string.ch1_e4_c100_t, arrayOf(R.string.ch1_e4_c100_v1, R.string.ch1_e4_c100_v2, R.string.ch1_e4_c100_v3), arrayOf({
                    hero.strength++
                    episode4.addCellHere(ci, R.string.ch1_e4_c101_v1)
                }, {
                    hero.defence++
                    episode4.addCellHere(ci, R.string.ch1_e4_c101_v2)
                }, {
                    episode4.addCellHere(ci, R.string.ch1_e4_c101_v3)
                })
            )
            .addCell(R.string.ch1_e4_c102)
            .addCell(R.string.ch1_e4_c103, ActivityAction {
                it.blood()
                it.mp = it.trackTown
            })
            .addHeroCellLeft(R.drawable.hero_takko_sadness, R.string.ch1_e4_c104_t)
            .addCell(BaseCell())

        //episode5
        val episode5 = Episode()
        episode5
            .addCell(R.string.ch1_e5_c0, ActivityAction {
                it.setFon(R.drawable.fon_court)
                it.mp = it.trackTragedic
            })
            .addCell(R.string.ch1_e5_c1)
            .addCell(R.string.ch1_e5_c2)
            .addCell(R.string.ch1_e5_c3)
            .addCell(R.string.ch1_e5_c4)
            .addCell(R.string.ch1_e5_c5)
            .addHeroCellRight(R.drawable.hero_judge, R.string.ch1_e5_c6_j)
            .addHeroCellRight(R.drawable.hero_judge, R.string.ch1_e5_c7_j)
            .addDialogCell(
                R.string.ch1_e5_c8_t, arrayOf(R.string.ch1_e5_c8_v1, R.string.ch1_e5_c8_v2, R.string.ch1_e5_c8_v3), arrayOf({
                    episode5.addHeroCellLeftHere(ci, R.drawable.hero_takko, R.string.ch1_e5_c9_v1_t)
                }, {
                    episode5.addHeroCellLeftHere(ci, R.drawable.hero_takko_sadness, R.string.ch1_e5_c9_v2_t)
                    hero.fame--
                }, {
                    episode5.addHeroCellLeftHere(ci, R.drawable.hero_takko_sadness, R.string.ch1_e5_c9_v3_t)
                    hero.fame--
                }), idFonButtonsTemplate, emptyCosts, true
            )
            .addHeroCellRight(R.drawable.hero_judge, R.string.ch1_e5_c10_j)
            .addHeroCellLeft(R.drawable.hero_takko_sadness, R.string.ch1_e5_c11_t)
            .addHeroCellRight(R.drawable.hero_judge, R.string.ch1_e5_c12_j)
            .addDialogCell(
                R.string.ch1_e5_c13_t, arrayOf(R.string.ch1_e5_c13_v1, R.string.ch1_e5_c13_v2, R.string.ch1_e5_c13_v3), arrayOf({
                    episode5.addHeroCellLeftHere(ci, R.drawable.hero_takko_sadness, R.string.ch1_e5_c14_v1_t)
                    hero.fame--
                }, {
                    episode5.addHeroCellLeftHere(ci, R.drawable.hero_takko_sadness, R.string.ch1_e5_c14_v2_t)
                    hero.strength++
                }, {
                    episode5.addHeroCellLeftHere(ci, R.drawable.hero_takko_sadness, R.string.ch1_e5_c14_v3_t)
                    hero.defence++
                })
            )
            .addHeroCellRight(R.drawable.hero_judge, R.string.ch1_e5_c15_j)
            .addHeroCellLeft(R.drawable.hero_takko_sadness, R.string.ch1_e5_c16_t)
            .addHeroCellLeft(R.drawable.hero_takko_rage, R.string.ch1_e5_c17_t)
            .addCell(R.string.ch1_e5_c18, ActivityAction {
                it.setFon(R.drawable.fon_prison)
                it.mp = it.trackHorror
            })
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e5_c19_t)
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e5_c20_t)
            .addCell(R.string.ch1_e5_c21, ActivityAction {
                it.blood()
            })
            .addHeroCellLeft(R.drawable.hero_takko_rage, R.string.ch1_e5_c22_t)
            .addCell(R.string.ch1_e5_c23)
            .addCell(R.string.ch1_e5_c24)
            .addHeroCellLeft(R.drawable.hero_takko_rage, R.string.ch1_e5_c25_t)
            .addCell(R.string.ch1_e5_c26)
            .addCell(R.string.ch1_e5_c27)
            .addHeroCellLeft(R.drawable.hero_takko_sadness, R.string.ch1_e5_c28_t)
            .addCell(R.string.ch1_e5_c29)
            .addHeroCellLeft(R.drawable.hero_takko_rage, R.string.ch1_e5_c30_t)
            .addHeroCellLeft(R.drawable.hero_takko_rage, R.string.ch1_e5_c31_t)
            .addHeroCellLeft(R.drawable.hero_takko_sadness, R.string.ch1_e5_c32_t)
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e5_c33_t)
            .addCell(R.string.ch1_e5_c34)
            .addCell(R.string.ch1_e5_c35, ActivityAction {
                it.setFon(R.drawable.fon_court)
                it.mp = it.trackTragedic
            })
            .addCell(R.string.ch1_e5_c36)
            .addHeroCellRight(R.drawable.hero_magraf_wonder, R.string.ch1_e5_c37_m)
            .addCell(R.string.ch1_e5_c38)
            .addCell(R.string.ch1_e5_c39)
            .addHeroCellRight(R.drawable.hero_magraf, R.string.ch1_e5_c40_m)
            .addDialogCell(
                R.string.ch1_e5_c41_t, arrayOf(R.string.ch1_e5_c41_v1, R.string.ch1_e5_c41_v2, R.string.ch1_e5_c41_v3), arrayOf({
                    episode5.addHeroCellLeftHere(ci, R.drawable.hero_takko, R.string.ch1_e5_c42_v1_1_t)
                    episode5.addCellHere(ci + 1, R.string.ch1_e5_c42_v1_2)
                    hero.strength++
                }, {
                    episode5.addHeroCellLeftHere(ci, R.drawable.hero_takko, R.string.ch1_e5_c42_v2)
                    hero.fame--
                }, {
                    hero.defence++
                })
            )
            .addHeroCellRight(R.drawable.hero_magraf_smile, R.string.ch1_e5_c43_m)
            .addHeroCellRight(R.drawable.hero_magraf_smile, R.string.ch1_e5_c44_m)
            .addHeroCellRight(R.drawable.hero_magraf, R.string.ch1_e5_c45_m)
            .addHeroCellRight(R.drawable.hero_magraf, R.string.ch1_e5_c46_m)
            .addHeroCellRight(R.drawable.hero_magraf, R.string.ch1_e5_c47_m)
            .addCell(R.string.ch1_e5_c48, ActivityAction {
                if (hero.fame >= 4) {
                    episode5.addCellHere(ci + 1, R.string.ch1_e5_c49_f_h)
                } else {
                    episode5.addCellHere(ci + 1, R.string.ch1_e5_c49_f_l)
                }
            })
            .addCell(R.string.ch1_e5_c50)
            .addHeroCellRight(R.drawable.hero_magraf_smile, R.string.ch1_e5_c51_m)
            .addHeroCellRight(R.drawable.hero_magraf, R.string.ch1_e5_c52_m)
            .addHeroCellRight(R.drawable.hero_magraf, R.string.ch1_e5_c53_m)
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e5_c54_t)
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e5_c55_t)
            .addCell(R.string.ch1_e5_c56)
            .addCell(R.string.ch1_e5_c57, ActivityAction {
                it.setFon(R.drawable.fon_town)
                it.mp = it.trackTown
            })
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e5_c58_t)
            .addCell(R.string.ch1_e5_c59)
            .addCell(R.string.ch1_e5_c60, ActivityAction {
                it.setFon(R.drawable.fon_home_veren)
                it.mp = it.trackHorror
            })
            .addCell(R.string.ch1_e5_c61)
            .addCell(R.string.ch1_e5_c62)
            .addCell(R.string.ch1_e5_c63)
            .addCell(BaseCell())

        val episode6 = Episode()
        episode6.addCell(R.string.ch1_e6_c0, ActivityAction {
            it.setFon(R.drawable.fon_river)
        })
            .addCell(R.string.ch1_e6_c1, ActivityAction {
                it.mp = it.trackTown
            })
            .addCell(R.string.ch1_e6_c2)
            .addCell(R.string.ch1_e6_c3)
            .addCell(R.string.ch1_e6_c4)
            .addCell(R.string.ch1_e6_c5)
            .addCell(R.string.ch1_e6_c6)
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e6_c7_t)
            .addCell(R.string.ch1_e6_c8)
            .addCell(R.string.ch1_e6_c9)
            .addCell(R.string.ch1_e6_c10)
            .addCell(R.string.ch1_e6_c11)
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e6_c12_t)
            .addHeroCellRight(R.drawable.hero_old_lady, R.string.ch1_e6_c13_w)
            .addCell(R.string.ch1_e6_c14)
            .addCell(R.string.ch1_e6_c15)
            .addHeroCellRight(R.drawable.hero_old_lady, R.string.ch1_e6_c16_w)
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e6_c17_t)
            .addHeroCellRight(R.drawable.hero_old_lady, R.string.ch1_e6_c18_w)
            .addCell(R.string.ch1_e6_c19)
            .addHeroCellRight(R.drawable.hero_old_lady, R.string.ch1_e6_c20_w)
            .addDialogCell(
                R.string.ch1_e6_c21_t, arrayOf(R.string.ch1_e6_c22_v1, R.string.ch1_e6_c23_v2), arrayOf({
                    hero.fame++
                }, {
                    episode6.addHeroCellRightHere(ci, R.drawable.hero_old_lady, R.string.ch1_e6_c24_v2_w)
                    hero.fame--
                }
                ))
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e6_c25_t)
            .addCell(R.string.ch1_e6_c26)
            .addCell(R.string.ch1_e6_c27)
            .addCell(R.string.ch1_e6_c28)
            .addCell(R.string.ch1_e6_c29)
            .addCell(R.string.ch1_e6_c30)
            .addCell(R.string.ch1_e6_c31)
            .addHeroCellRight(R.drawable.hero_old_lady, R.string.ch1_e6_c32_w)
            .addCell(R.string.ch1_e6_c33)
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e6_c34_t)
            .addHeroCellRight(R.drawable.hero_old_lady, R.string.ch1_e6_c35_w)
            .addCell(R.string.ch1_e6_c36)
            .addCell(R.string.ch1_e6_c37)
            .addCell(R.string.ch1_e6_c38)
            .addCell(R.string.ch1_e6_c39)
            .addCell(R.string.ch1_e6_c40)
            .addCell(R.string.ch1_e6_c41, ActivityAction {
                it.setFon(R.drawable.fon_house)
                it.mp = it.trackFon
            })
            .addHeroCellRight(R.drawable.hero_old_lady, R.string.ch1_e6_c42_w)
            .addCell(R.string.ch1_e6_c43)
            .addHeroCellRight(R.drawable.hero_old_lady, R.string.ch1_e6_c44_w)
            .addCell(R.string.ch1_e6_c45)
            .addHeroCellRight(R.drawable.hero_old_lady, R.string.ch1_e6_c46_w)
            .addHeroCellLeft(R.drawable.hero_takko_wonder, R.string.ch1_e6_c47_t)
            .addHeroCellRight(R.drawable.hero_old_lady, R.string.ch1_e6_c48_w)
            .addHeroCellRight(R.drawable.hero_old_lady, R.string.ch1_e6_c49_w)
            .addCell(R.string.ch1_e6_c50)
            .addHeroCellRight(R.drawable.hero_old_lady, R.string.ch1_e6_c51_w)
            .addHeroCellRight(R.drawable.hero_old_lady, R.string.ch1_e6_c51_fix_w)
            .addCell(R.string.ch1_e6_c52)
            .addSelectItemCell(
                R.string.ch1_e6_c53, R.string.ch1_e6_c53_b, arrayOf(Item(R.drawable.item_frog, R.string.ch1_e6_c53_v1))
            )
            .addCell(R.string.ch1_e6_c54)
            .addHeroCellRight(R.drawable.hero_old_lady, R.string.ch1_e6_c55_w)
            .addCell(R.string.ch1_e6_c56)
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e6_c57_t)
            .addCell(R.string.ch1_e6_c58)
            .addHeroCellLeft(R.drawable.hero_takko_wonder, R.string.ch1_e6_c59_t)
            .addHeroCellRight(R.drawable.hero_old_lady, R.string.ch1_e6_c60_w)
            .addHeroCellRight(R.drawable.hero_old_lady, R.string.ch1_e6_c61_w)
            .addCell(R.string.ch1_e6_c62, ActivityAction {
                it.setFon(R.drawable.fon_house)
            })
            .addCell(R.string.ch1_e6_c63)
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e6_c64_t)
            .addCell(R.string.ch1_e6_c65)
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e6_c66_t)
            .addHeroCellRight(R.drawable.hero_old_lady, R.string.ch1_e6_c67_w)
            .addHeroCellRight(R.drawable.hero_old_lady, R.string.ch1_e6_c68_w)
            .addDialogCell(
                R.string.ch1_e6_c69, arrayOf(R.string.ch1_e6_c70_v1, R.string.ch1_e6_c70_v2, R.string.ch1_e6_c70_v3), arrayOf({
                    hero.coins -= 5
                    hero.fame++
                    episode6.addHeroCellLeftHere(ci, R.drawable.hero_takko_wonder, R.string.ch1_e6_c72_v1_v2_t)
                    episode6.addHeroCellRightHere(ci + 1, R.drawable.hero_old_lady, R.string.ch1_e6_c73_v1_v2_w)
                    episode6.addHeroCellRightHere(ci + 2, R.drawable.hero_old_lady, R.string.ch1_e6_c74_v1_v2_w)
                    episode6.addHeroCellLeftHere(ci + 3, R.drawable.hero_takko, R.string.ch1_e6_c75_v1_t)
                    episode6.addHeroCellRightHere(ci + 4, R.drawable.hero_old_lady, R.string.ch1_e6_c76_v1_w)
                    episode6.addHeroCellRightHere(ci + 5, R.drawable.hero_old_lady, R.string.ch1_e6_c77_v1_w)
                    episode6.addHeroCellLeftHere(ci + 6, R.drawable.hero_takko, R.string.ch1_e6_c78_v1_t)
                    episode6.addHeroCellRightHere(ci + 7, R.drawable.hero_old_lady, R.string.ch1_e6_c79_v1_w)
                }, {
                    episode6.addHeroCellLeftHere(ci, R.drawable.hero_takko_wonder, R.string.ch1_e6_c72_v1_v2_t)
                    episode6.addHeroCellRightHere(ci + 1, R.drawable.hero_old_lady, R.string.ch1_e6_c73_v1_v2_w)
                    episode6.addHeroCellRightHere(ci + 2, R.drawable.hero_old_lady, R.string.ch1_e6_c74_v1_v2_w)
                    hero.fame--
                }, {
                    hero.defence++
                }),
                arrayOf(R.drawable.fon_button_gold, R.drawable.fon_button, R.drawable.fon_button),
                arrayOf(5, 0, 0)
            )
            .addHeroCellLeft(R.drawable.hero_takko_wonder, R.string.ch1_e6_c80_t)
            .addCell(R.string.ch1_e6_c81)
            .addCell(R.string.ch1_e6_c82)
            .addHeroCellLeft(R.drawable.hero_takko, R.string.ch1_e6_c83_t)
            .addCell(R.string.ch1_e6_c84)
            .addCell(R.string.ch1_e6_c85)
            .addCell(R.string.ch1_e6_c86)
            .addCell(R.string.ch1_e6_c87)
            .addHeroCellLeft(R.drawable.hero_takko_sadness, R.string.ch1_e6_c88_t)
            .addCell(R.string.ch1_e6_c89)
            .addCell(R.string.ch1_e6_c90, ActivityAction {
                it.setFon(R.drawable.fon_house)
            })
            .addCell(R.string.ch1_e6_c91)
            .addCell(R.string.ch1_e6_c92)
            .addCell(R.string.ch1_e6_c93)
            .addCell(R.string.ch1_e6_c94)
            .addCell(R.string.ch1_e6_c95)
            .addCell(R.string.ch1_e6_c96, ActivityAction {
                it.setFon(R.drawable.fon_town)
            })
            .addCell(BaseCell())

        chapter1.addAll(arrayOf(episode0, episode1, episode2, episode3, episode4, episode5, episode6))
        chapters.add(chapter1)
    }

    private fun Episode.addHeroCellLeft(
        idHeroFon: Int, idText: Int, vararg action: ActivityAction
    ): Episode {
        addCell(CellHero(idFon, idHeroFon, idText, LEFT)).onStartCellListeners.addAll(action)
        return this
    }

    private fun Episode.addHeroCellLeftHere(index: Int, idHeroFon: Int, idText: Int, vararg action: ActivityAction): Episode {
        val cell = (CellHero(idFon, idHeroFon, idText, LEFT))
        cell.onStartCellListeners.addAll(action)
        addCellAt(index, cell)
        hero.margin++
        Log.i("M_CELL", "margin=${hero.margin}")
        return this
    }

    private fun Episode.addHeroCellRight(idHeroFon: Int, idText: Int, vararg action: ActivityAction): Episode {
        addCell(CellHero(idFon, idHeroFon, idText, RIGHT)).onStartCellListeners.addAll(action)
        return this
    }

    private fun Episode.addHeroCellRightHere(index: Int, idHeroFon: Int, idText: Int, vararg action: ActivityAction): Episode {
        val cell = CellHero(idFon, idHeroFon, idText, RIGHT)
        cell.onStartCellListeners.addAll(action)
        addCellAt(index, cell)
        hero.margin++
        Log.i("M_CELL", "margin=${hero.margin}")
        return this
    }

    private fun Episode.addCell(idText: Int, vararg action: ActivityAction): Episode {
        addCell(Cell(idFon, idText)).onStartCellListeners.addAll(action)
        return this
    }

    private fun Episode.addCellHere(index: Int, idText: Int, vararg action: ActivityAction): Episode {
        val cell = Cell(idFon, idText)
        addCellAt(index, cell).onStartCellListeners.addAll(action)
        return this
    }

    private fun createIdButtonsFromTemplate(): Array<Int> {
        return arrayOf(R.drawable.fon_button, R.drawable.fon_button, R.drawable.fon_button)
    }

    private fun Episode.addDialogCell(
        idText: Int,
        idTextButtons: Array<Int>,
        actions: Array<() -> Unit>,
        idFonButtons: Array<Int> = createIdButtonsFromTemplate(),
        costs: Array<Int> = emptyCosts,
        isATime: Boolean = false,
        vararg action: ActivityAction
    ): Episode {
        addCell(CellDialog(idFon, idText, idTextButtons, actions, idFonButtons, costs, isATime)).onStartCellListeners.addAll(action)
        return this
    }

    private fun Episode.addDialogCell(
        idText: Int,
        idTextButtons: Array<Int>,
        actions: Array<() -> Unit>,
        vararg action: ActivityAction
    ): Episode {
        addCell(CellDialog(idFon, idText, idTextButtons, actions, createIdButtonsFromTemplate(), emptyCosts, false)).onStartCellListeners.addAll(action)
        return this
    }

    private fun Episode.addDialogCellHere(
        index: Int,
        idText: Int,
        idTextButtons: Array<Int>,
        actions: Array<() -> Unit>,
        idFonButtons: Array<Int> = createIdButtonsFromTemplate(),
        costs: Array<Int> = emptyCosts,
        isATime: Boolean = false,
        vararg action: ActivityAction
    ): Episode {
        val cell = CellDialog(idFon, idText, idTextButtons, actions, idFonButtons, costs, isATime)
        addCellAt(index, cell).onStartCellListeners.addAll(action)
        return this
    }

    private fun Episode.addSelectItemCell(
        idText: Int, idTextButton: Int, items: Array<Item>, vararg action: ActivityAction
    ): Episode {
        addCell(CellSelectItem(idFon, idText, idTextButton, items)).onStartCellListeners.addAll(action)
        return this
    }
}