package com.quest.model

import com.quest.R

data class Item(var idIcon: Int, var idText: Int, var action: () -> Unit = {}, val cost: Int = 0, val idFonButton: Int = R.drawable.fon_button)