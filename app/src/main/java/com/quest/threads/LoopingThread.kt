package com.quest.threads

import android.os.Handler
import android.util.Log
import com.quest.threads.OnTickListener
import java.util.concurrent.CopyOnWriteArrayList


class LoopingThread : Thread() {
    private lateinit var handler: Handler
    private lateinit var handler1ms: Handler
    var isRunning = false
    var onTickEventListeners: CopyOnWriteArrayList<OnTickListener> = CopyOnWriteArrayList()
    private lateinit var runnable100MS: Runnable
    var speed = 2

    override fun run() {
        this.handler = Handler()
        this.handler1ms = Handler()
        runnable100MS = Runnable {
            if (isRunning) {
                val iterator = onTickEventListeners.iterator()
                while (iterator.hasNext()) {
                    iterator.next().onTick()
                }
            }
            handler.postDelayed(runnable100MS, 100L/speed)
        }

        handler.post(runnable100MS)
    }

    fun onStart() {
        isRunning = true
    }

    fun onStop() {
        isRunning = false
    }

    fun finish() {
        this.onStop()
        this.onTickEventListeners.clear()
    }
}