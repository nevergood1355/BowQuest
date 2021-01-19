package com.quest.fragment

import android.os.CountDownTimer
import android.view.ViewGroup
import com.quest.MainActivity
import com.quest.R
import com.thekingames.screen.Fragment
import kotlinx.android.synthetic.main.dialog_timer.view.*
import java.text.SimpleDateFormat
import java.util.*


class DialogTimer(parent: ViewGroup) : Fragment(parent, R.layout.dialog_timer) {
    private lateinit var countDownTimer: CountDownTimer
    private var rest: Long = 0L
    val a = activity as MainActivity

    init {
        view.close.setOnClickListener { parent.removeView(view) }
        view.buy.setOnClickListener {
            a.h.gems++
        }

        countDownTimer = object : CountDownTimer(Long.MAX_VALUE, 1000){
            override fun onTick(p0: Long) {
                rest = (30 - a.mDif)
                if (rest == 0L) {
                    view.close.callOnClick()
                }
                view.timer.text = "$rest"
            }

            override fun onFinish() {
                countDownTimer.start()
            }
        }
        countDownTimer.start()
    }

    override fun releaseDate() {
        super.releaseDate()
        rest = (30 - a.mDif)
    }
}