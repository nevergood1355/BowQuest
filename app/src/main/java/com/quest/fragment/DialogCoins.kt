package com.quest.fragment

import android.view.ViewGroup
import android.view.ViewParent
import com.quest.MainActivity
import com.quest.R
import com.thekingames.screen.Fragment
import kotlinx.android.synthetic.main.dialog_coins.view.*


class DialogCoins(parent: ViewGroup) : Fragment(parent, R.layout.dialog_coins) {
    val a = activity as MainActivity
    init {
        view.close.setOnClickListener { parent.removeView(view) }
        view.buy.setOnClickListener {
            a.h.coins += 2
        }
    }
}