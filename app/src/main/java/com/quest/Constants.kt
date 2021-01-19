package com.quest

import kotlin.math.sign

const val ANIM_TIME_FAST = 0L
const val ANIM_TIME_NORMAL = 600L
var ANIM_TIME = ANIM_TIME_NORMAL


fun getSign(delta: Int): String {
    return if (delta.sign > 0) {
        "+"
    } else {
        ""
    }
}