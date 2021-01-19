package com.quest.model

class Episode {
    var cells: ArrayList<BaseCell> = arrayListOf()

    fun addCell(cell: BaseCell): BaseCell {
        cells.add(cell)
        return cell
    }

    fun addCellAt(index: Int, cell: BaseCell): BaseCell {
        cells.add(index, cell)
        return cell
    }
}