package com.quest.model

import com.quest.R

open class BaseCell(){
    val onStartCellListeners = arrayListOf<ActivityAction>()
}

const val LEFT = 123
const val RIGHT = 321


open class Cell(var idFon: Int, val idText: Int) : BaseCell() {

}

class CellHero(idFon: Int, val idHeroFon: Int, idText: Int, var side: Int) :
    Cell(idFon, idText) {

}

class CellDialog(
    idFon: Int, idText: Int,
    val idTextButtons: Array<Int>, val actions: Array<() -> Unit>, val idFonButtons: Array<Int>,val costs: Array<Int>,val isATime: Boolean) : Cell(idFon, idText) {

}

class CellSelectItem(
    idFon: Int, idText: Int,
    val idTextButton: Int, val items: Array<Item>
) : Cell(idFon, idText) {

}