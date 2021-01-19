package com.quest

import android.animation.AnimatorSet
import android.animation.LayoutTransition
import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.transition.Fade
import android.transition.Slide
import android.transition.Transition
import android.transition.TransitionManager
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import com.quest.model.*
import com.quest.threads.LoopingThread
import com.quest.threads.OnTickListener
import com.thekingames.screen.Fragment
import com.thekingames.screen.Screen
import kotlinx.android.synthetic.main.cell.view.text
import kotlinx.android.synthetic.main.cell_dialog.view.*
import kotlinx.android.synthetic.main.cell_episode_end.view.*
import kotlinx.android.synthetic.main.cell_hero.view.*
import kotlinx.android.synthetic.main.cell_select_item.view.*
import kotlinx.android.synthetic.main.cell_select_item.view.content
import kotlinx.android.synthetic.main.cell_select_item.view.cost
import kotlinx.android.synthetic.main.cell_select_item.view.next
import kotlinx.android.synthetic.main.menu.view.*
import kotlinx.android.synthetic.main.root.*
import kotlinx.android.synthetic.main.root.view.*
import kotlinx.android.synthetic.main.select_item.view.*
import kotlin.math.abs
import kotlin.math.sign


const val FADE_DURATION = 400L
const val FADE_DELAY = 150L

fun View.setSingleClickListener(l: View.OnClickListener) {
    setOnClickListener {
        l.onClick(it)
        it.setOnClickListener(null)
    }
}

fun View.setDelayedClickListener(delay: Long, l: View.OnClickListener) {
    postDelayed({
        setSingleClickListener {
            l.onClick(it)
        }
    }, delay)
}

open class ScreenCell(parent: ViewGroup, cellBase: BaseCell, resId: Int = R.layout.cell) :
    Screen(parent, resId) {

    override fun show() {
        setLayoutTransition()
        TransitionManager.beginDelayedTransition(parent, Fade(Fade.OUT).apply { duration = FADE_DURATION - FADE_DELAY })
        super.show()
    }

    protected open fun setLayoutTransition() {
        parent.layoutTransition = LayoutTransition()
        val layoutTransition = parent.layoutTransition
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING)

        layoutTransition.setAnimator(LayoutTransition.APPEARING,  ObjectAnimator.ofFloat(null, View.ALPHA, 0F, 1F))
        layoutTransition.setDuration(LayoutTransition.APPEARING, FADE_DURATION)
        layoutTransition.setStartDelay(LayoutTransition.APPEARING, FADE_DELAY)

        layoutTransition.setAnimator(LayoutTransition.DISAPPEARING,  ObjectAnimator.ofFloat(null, View.ALPHA, 0F).apply { interpolator = AccelerateInterpolator(2F) })
        layoutTransition.setDuration(LayoutTransition.DISAPPEARING, FADE_DURATION)

        layoutTransition.setDuration(2 * FADE_DURATION)
    }

    val a = activity as MainActivity
    private val cell = cellBase as Cell




    protected open fun startAnim() {
        a.leftStateHero = StateHero.Out
        a.rightStateHero = StateHero.Out
    }

    override fun releaseDate() {
        super.releaseDate()
        cell.onStartCellListeners.forEach {
            it.invoke(a)
        }
        startAnim()
        view.text.setText(cell.idText)

        nextCellListener()
    }

    protected open fun nextCellListener() {
        view.setDelayedClickListener(FADE_DURATION) {
            a.nextCell()
        }
    }
}

class ScreenCellHero(parent: ViewGroup, val cell: CellHero) :
    ScreenCell(parent, cell, R.layout.cell_hero) {
    private val cellHero = cell
    private var isAnimate = false

    override fun startAnim() {
        if (cellHero.side == LEFT) {
            a.rightStateHero = StateHero.Out
        } else if (cellHero.side == RIGHT) {
            a.leftStateHero = StateHero.Out
        }
    }

    override fun releaseDate() {
        super.releaseDate()
        view.hero_name.setText(a.src.getHeroName.invoke(cellHero.idHeroFon))

        isAnimate = when (a.lastCell.javaClass) {
            CellHero::class.java -> {
                val lastCell = a.lastCell as CellHero
                (lastCell.side != cellHero.side && a.src.getHeroName.invoke(lastCell.idHeroFon) != a.src.getHeroName.invoke(
                    cellHero.idHeroFon
                ))
            }
            CellDialog::class.java -> {
                LEFT != cellHero.side || a.src.getHeroName.invoke(R.drawable.hero_takko) != a.src.getHeroName.invoke(cellHero.idHeroFon)
            }
            CellSelectItem::class.java, BaseCell::class.java, Cell::class.java -> {
                true
            }
            else -> {
                false
            }
        }

        if (cellHero.side == LEFT) {
            a.setLeftHero(cellHero.idHeroFon)
            if (isAnimate) a.leftStateHero = StateHero.In
        } else if (cellHero.side == RIGHT) {
            a.setRightHero(cellHero.idHeroFon)
            if (isAnimate) a.rightStateHero = StateHero.In
            else if (a.lastCell is CellHero && a.src.getHeroName.invoke((a.lastCell as CellHero).idHeroFon) != a.src.getHeroName.invoke(cellHero.idHeroFon)) {
                a.rightStateHero = StateHero.Out
                a.rightStateHero = StateHero.In
            }
        }
    }
}

//region select_item
class ScreenCellSelectItem(parent: ViewGroup, cell: CellSelectItem) :
    ScreenCell(parent, cell, R.layout.cell_select_item), View.OnTouchListener {

    class ItemFragment(parent: ViewGroup, private var item: Item) :
        Fragment(parent, R.layout.select_item) {
        override fun releaseDate() {
            super.releaseDate()
            view.icon.setImageResource(item.idIcon)
            view.text.setText(item.idText)
        }
    }

    private var fromPosition = 0f
    private var toPosition = 0f
    lateinit var animSlideIn: Animation
    lateinit var animSlideOut: Animation
    lateinit var animFlipInForward: Animation
    lateinit var animFlipOutForward: Animation
    lateinit var animFlipInBackward: Animation
    lateinit var animFlipOutBackward: Animation

    private val cellSelectItem = cell

    override fun releaseDate() {
        super.releaseDate()
        main()
    }

    private fun getCurrentItem(): Item {
        return cellSelectItem.items[view.flipper.displayedChild]
    }

    private fun main() {
        view.flipper.setOnTouchListener(this)
        view.next.setText(cellSelectItem.idTextButton)
        val itemViews = arrayListOf<ItemFragment>()
        cellSelectItem.items.forEach { itemViews.add(ItemFragment(view.flipper, it)) }
        animSlideIn = AnimationUtils.loadAnimation(a, R.anim.slide_in)
        animSlideOut = AnimationUtils.loadAnimation(a, R.anim.slide_out)
        animFlipInForward = AnimationUtils.loadAnimation(a, R.anim.flip_in)
        animFlipOutForward = AnimationUtils.loadAnimation(a, R.anim.flip_out)
        animFlipInBackward = AnimationUtils.loadAnimation(a, R.anim.flip_in_reverse)
        animFlipOutBackward = AnimationUtils.loadAnimation(a, R.anim.flip_out_reverse)

        itemViews.forEach { it.show() }
        a.h.onCoinsChangeListeners.add(OnValueChange { view.next_fon.isEnabled = getCurrentItem().cost <= a.h.coins })

        view.next_fon.setSingleClickListener {
            it.isEnabled = false
            getCurrentItem().action.invoke()
            a.nextCell()
        }

        if (cellSelectItem.items.size == 1) {
            view.action_left.visibility  = View.INVISIBLE
            view.action_right.visibility = View.INVISIBLE
        } else {
            view.action_left.visibility = View.VISIBLE
            view.action_right.visibility = View.VISIBLE
        }

        view.action_left.setOnClickListener {
            swipeLeft()
            it.isEnabled = false
            view.next_fon.isEnabled = getCurrentItem().cost <= a.h.coins
            it.postDelayed(object : Runnable {
                override fun run() {
                    it.isEnabled = true
                    it.removeCallbacks(this)
                }
            }, 400)
        }
        view.action_right.setOnClickListener {
            swipeRight()
            it.isEnabled = false
            view.next_fon.isEnabled = getCurrentItem().cost <= a.h.coins
            it.postDelayed(object : Runnable {
                override fun run() {
                    it.isEnabled = true
                    it.removeCallbacks(this)
                }
            }, 400)
        }
    }

    override fun nextCellListener() {

    }

    private fun swipeLeft() {
        if (cellSelectItem.items.size > 1) {
            view.flipper.inAnimation = animFlipInBackward
            view.flipper.outAnimation = animFlipOutBackward
            view.flipper.showPrevious()
            view.next_fon.setBackgroundResource(getCurrentItem().idFonButton)
            view.next.isEnabled = getCurrentItem().cost <= a.h.coins
            if (getCurrentItem().cost > 0) {
                view.cost_container.visibility = View.VISIBLE
                view.cost.text = "${getCurrentItem().cost}"
            } else {
                view.cost_container.visibility = View.INVISIBLE
            }
        }
    }

    private fun swipeRight() {
        if (cellSelectItem.items.size > 1) {
            view.flipper.inAnimation = animFlipInForward
            view.flipper.outAnimation = animFlipOutForward
            view.flipper.showNext()
            view.next_fon.setBackgroundResource(getCurrentItem().idFonButton)
            view.next.isEnabled = getCurrentItem().cost <= a.h.coins
            if (getCurrentItem().cost > 0) {
                view.cost_container.visibility = View.VISIBLE
                view.cost.text = "${getCurrentItem().cost}"
            } else {
                view.cost_container.visibility = View.INVISIBLE
            }
        }
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> fromPosition = event.x
            MotionEvent.ACTION_UP -> {
                toPosition = event.x
                if (abs(toPosition - fromPosition) >= 100) {
                    v.isEnabled = false
                    view.next_fon.isEnabled = getCurrentItem().cost <= a.h.coins
                    v.postDelayed(object : Runnable {
                        override fun run() {
                            v.isEnabled = true
                            v.removeCallbacks(this)
                        }
                    }, 400)
                    if (fromPosition < toPosition) {
                        swipeRight()
                    } else if (fromPosition > toPosition) {
                        swipeLeft()
                    }
                }
            }
        }
        return true
    }
}
//endregion


//region dialog
class ScreenCellDialog(parent: ViewGroup, cell: CellDialog) :
    ScreenCell(parent, cell, R.layout.cell_dialog) {
    private val cellDialog = cell
    val idV = arrayOf(R.id.v1, R.id.v2, R.id.v3)
    val idCosts = arrayOf(R.id.cost1, R.id.cost2, R.id.cost3)
    val idContainers = arrayOf(R.id.cost_container1, R.id.cost_container2, R.id.cost_container2)
    var loopingThread = LoopingThread()
    private var questDuration = 10 //5 sec for speed x2
    var currentTime = 0.0

    init {

    }

    override fun startAnim() {
        a.leftStateHero = StateHero.In
        a.rightStateHero = StateHero.Out
    }

    override fun releaseDate() {
        super.releaseDate()
        view.isEnabled = false
        if (cellDialog.isATime) {
            view.answers.isEnabled = false
            if (a.soundEnable)
                a.trackTimer.start()
            view.timer_bar.setMaxValue(questDuration)
            currentTime = questDuration.toDouble()
            view.timer_bar.setValue(currentTime)
            loopingThread.run()

            loopingThread.onTickEventListeners.add(OnTickListener {
                currentTime -= 0.1
                view.timer_bar.setValue(currentTime)
                if (currentTime <= 0.0) {
                    view.answers.getChildAt(view.answers.childCount - 1).callOnClick()
                    for (i in 0 until view.answers.childCount) {
                        view.answers.getChildAt(i).setOnClickListener(null)
                    }
                    loopingThread.onStop()
                }
            })
            loopingThread.onStart()
        } else {
            view.timer_bar.visibility = View.INVISIBLE
        }
        a.setLeftHero(R.drawable.hero_takko)
        cellDialog.idTextButtons.forEachIndexed { index, idText ->
            view.findViewById<TextView>(idV[index]).setText(idText)
        }

        if (cellDialog.actions.size < 3) {
            cellDialog.idFonButtons[2] = 0
        }

        cellDialog.idFonButtons.forEachIndexed { index, idFon ->
            if(idFon != 0) {
                view.answers.getChildAt(index).setBackgroundResource(idFon)
                view.answers.getChildAt(index).visibility = View.VISIBLE
            } else {
                view.answers.getChildAt(index).visibility = View.GONE
            }
        }

        fun releaseCosts() {
            cellDialog.costs.forEachIndexed { index, cost ->
                if (cost != 0) {
                    view.findViewById<ViewGroup>(idContainers[index]).visibility = View.VISIBLE
                    view.findViewById<TextView>(idCosts[index]).text = "$cost"
                }
                view.answers.getChildAt(index).isEnabled = cost <= a.h.coins
            }
        }
        releaseCosts()
        a.h.onCoinsChangeListeners.add(OnValueChange { releaseCosts() })

        cellDialog.actions.forEachIndexed { index, action ->
            view.answers.getChildAt(index).setDelayedClickListener(FADE_DURATION) {
                if (it.isEnabled) {
                    for (i in 0 until view.answers.childCount) {
                        view.answers.getChildAt(i).isEnabled = false
                    }

                    if (cellDialog.isATime) {
                        if (a.soundEnable) {
                            a.trackTimer.pause()
                            a.trackTimer.seekTo(0)
                        }
                        loopingThread.onStop()
                    }
                    Log.i("MAction", "Action has been invoked")
                    action.invoke()
                    a.nextCell()
                }
            }
        }
    }

    override fun nextCellListener() {

    }
}
//endregion


class ScreenCellEpisodeEnd(parent: ViewGroup) :
    Screen(parent, R.layout.cell_episode_end) {
    private val a = activity as MainActivity


    override fun releaseDate() {
        view.isEnabled = false
        a.mp = a.trackFon
        a.h.episodeIndex++
        view.stat_value1.text = "${a.h.strength}"
        view.stat_value2.text = "${a.h.defence}"
        view.stat_value3.text = "${a.h.fame}"
        view.stat_delta1.text = "(${getSign(a.h.d.dStat1)}${a.h.d.dStat1})"
        view.stat_delta2.text = "(${getSign(a.h.d.dStat2)}${a.h.d.dStat2})"
        view.stat_delta3.text = "(${getSign(a.h.d.dStat3)}${a.h.d.dStat3})"
        a.h.coins += 2
        view.stat_value4.text = "${a.h.coins}"
        a.leftStateHero = StateHero.Out
        a.rightStateHero = StateHero.Out

        view.text.text = "${a.h.episodeIndex}"
        val colors = intArrayOf(
            Color.parseColor("#00FFFFFF"),
            Color.parseColor("#BF805638"),
            Color.parseColor("#BF805638"),
            Color.parseColor("#BF805638"),
            Color.parseColor("#00FFFFFF")
        )
        val gd = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors)
        gd.cornerRadius = 0f
        view.c1.background = gd

        view.next.setDelayedClickListener(FADE_DURATION) {
            it.isEnabled = false
            a.manager.setScreen(a.screenMenu)
            a.h.cellIndex = 0
        }
    }
}