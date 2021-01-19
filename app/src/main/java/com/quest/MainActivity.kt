package com.quest

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.transition.Fade
import android.transition.Fade.*
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.transition.addListener
import com.quest.db.SQLiteOpenHelper
import com.quest.fragment.DialogCoins
import com.quest.fragment.DialogTimer
import com.quest.fragment.DialogWarning
import com.quest.model.*
import com.thekingames.screen.ScreenManager
import kotlinx.android.synthetic.main.root.*
import kotlinx.android.synthetic.main.root.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs
import kotlin.reflect.KFunction1


class MainActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {
    //region db
    companion object StartHeroConst {
        const val START_COINS = 5
        const val START_GEMS = 3
    }


    private lateinit var db: SQLiteOpenHelper
    //endregion

    //region music
    var soundEnable = true
        set(value) {
            field = value
            if (!field) {
                ANIM_TIME = ANIM_TIME_FAST
                mp?.pause()
            } else {
                ANIM_TIME = ANIM_TIME_NORMAL
                mp?.start()
            }
        }
    var mp: MediaPlayer? = null
        set(value) {
            if (soundEnable) {
                field?.pause()
                field = value
                field?.seekTo(0)
                field?.start()
            }
        }
    lateinit var trackStatUp: MediaPlayer
    lateinit var trackFon: MediaPlayer
    lateinit var trackLove: MediaPlayer
    lateinit var trackTown: MediaPlayer
    lateinit var trackTragedic: MediaPlayer
    lateinit var trackHorror: MediaPlayer
    lateinit var trackRoom: MediaPlayer
    lateinit var trackStress: MediaPlayer
    lateinit var choicePath: MediaPlayer
    lateinit var trackTimer: MediaPlayer
    //endregion

    lateinit var h: Hero
    lateinit var timer: CountDownTimer

    //region screens
    lateinit var screenMenu: ScreenMenu
    lateinit var manager: ScreenManager
    lateinit var dialogManager: ScreenManager
    lateinit var lastCell: BaseCell
    lateinit var viewStats: LinearLayout
    lateinit var viewRoot: ViewGroup
    lateinit var viewMessage: ViewGroup
    lateinit var dialogWarning: DialogWarning
    lateinit var dialogTimer: DialogTimer
    lateinit var dialogCoins: DialogCoins
    //endregion

    //region animations
    private var handler = Handler(Looper.getMainLooper())
    private lateinit var animMessageOut: Animation
    var leftStateHero: StateHero? = null
        set(value) {
            if (field != value) {
                field = value
                if (field == StateHero.In) {
                    animLeftHeroIn()
                } else if (field == StateHero.Out) {
                    animLeftHeroOut()
                }
            }
        }
    var rightStateHero: StateHero? = null
        set(value) {
            if (field != value) {
                field = value
                if (field == StateHero.In) {
                    animRightHeroIn()
                } else if (field == StateHero.Out) {
                    animRightHeroOut()
                }
            }
        }
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.root)

        //region load hero
        db = SQLiteOpenHelper(this)

        val hero = db.loadHero(1)
        h = if (hero is Hero) {
            hero
        } else {
            val sample: ArrayList<Number> = arrayListOf(0, 0, 0, START_COINS, START_GEMS, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
            Hero(sample)
        }
        db.saveHero(h)
        h.bind(this)

        episodeSourceMain = EpisodeSource({ (h.episode) }, { h.cellIndex }, { h.cellIndex++ }, this::getHeroName)
        episodeSourceTiters = EpisodeSource({ h.episodeTiters }, { h.titersIndex }, { h.titersIndex++ }, this::getTitersHeroName)
        src = episodeSourceMain
        //endregion

        viewStats = stats
        viewRoot = root
        viewMessage = message
        loadMedia()
        screenMenu = ScreenMenu(root)
        manager = ScreenManager(screenMenu, this)
        dialogManager = ScreenManager(screenMenu, this)
        dialogWarning = DialogWarning(dialog_container)
        dialogTimer = DialogTimer(dialog_container)
        dialogCoins = DialogCoins(dialog_container)
        manager.setScreen(screenMenu)
        val animFire = AnimationUtils.loadAnimation(this, R.anim.fire)

        //region bind views
        stats.stat1.text = "${h.strength}"
        h.onStrengthChange.add(OnValueChange {
            icon_stat.setImageResource(R.drawable.icon_stat1)
            text_stat.setText(R.string.stat1)
            text_stat.text = text_stat.text.toString().toLowerCase(Locale.getDefault())
            delta.text = "${getSign(it)}$it"
            animMessage()
            screenMenu.statsAnimTop()
            stats.stat1.text = "${h.strength}"
            stats.stat1.startAnimation(animFire)
            stats.icon_stat1.startAnimation(animFire)
            handler.postDelayed({ screenMenu.statsAnimDown() }, 3500L)
        })

        stats.stat2.text = "${h.defence}"
        h.onDefenceChange.add(OnValueChange {
            icon_stat.setImageResource(R.drawable.icon_stat2)
            text_stat.setText(R.string.stat2)
            text_stat.text = text_stat.text.toString().toLowerCase(Locale.getDefault())
            delta.text = "${getSign(it)}$it"
            animMessage()
            screenMenu.statsAnimTop()

            stats.stat2.text = "${h.defence}"
            stats.stat2.startAnimation(animFire)
            stats.icon_stat2.startAnimation(animFire)
            handler.postDelayed({ screenMenu.statsAnimDown() }, 3500L)
        })

        stats.stat3.text = "${h.fame}"
        h.onFameChange.add(OnValueChange {
            icon_stat.setImageResource(R.drawable.icon_stat3)
            text_stat.setText(R.string.stat3)
            text_stat.text = text_stat.text.toString().toLowerCase(Locale.getDefault())
            delta.text = "${getSign(it)}$it"
            animMessage()
            screenMenu.statsAnimTop()

            stats.stat3.text = "${h.fame}"
            stats.stat3.startAnimation(animFire)
            stats.icon_stat3.startAnimation(animFire)
            handler.postDelayed({ screenMenu.statsAnimDown() }, 3500L)
        })
        coins.text = "${h.coins}"
        h.onCoinsChangeListeners.add(OnValueChange {
            coins.text = "${h.coins}"
            coins.startAnimation(animFire)
            icon1.startAnimation(animFire)
        })

        class GemsView {
            val items = arrayOf(gem1, gem2, gem3)
            val active = getDrawable(R.drawable.icon_gems)
            val unactivated = getDrawable(R.drawable.icon_gems_unactivated)

            fun start() {
                items.forEach { it.setImageDrawable(unactivated) }
            }

            fun add(delta: Int) {
                repeat(delta) {
                    val v = items.first { it.drawable == unactivated }
                    v.setImageDrawable(active)
                    v.startAnimation(animFire)
                }
            }

            fun spend(delta: Int) {
                repeat(abs(delta)) {
                    val v = items.last { it.drawable == active }
                    v.setImageDrawable(unactivated)
                    v.startAnimation(animFire)
                }
            }
        }

        val gemsView = GemsView()
        gemsView.start()
        gemsView.add(h.gems)
        h.onGemsChange.add(OnValueChange {
            if (it > 0) {
                gemsView.add(it)
            } else {
                gemsView.spend(it)
            }
            buy_gems.isEnabled = h.gems < 3
        })

        buy_coins.setOnClickListener {
            dialogCoins.show()
        }
        buy_gems.setOnClickListener {
            dialogTimer.show()
        }

        //endregion

        leftStateHero = StateHero.Out
        rightStateHero = StateHero.Out

        //region timer
        timer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
            override fun onTick(interval: Long) {
                if (accessPermissionLocation) {
                    trySetNewTime()
                }
            }

            override fun onFinish() {
                timer.start()
            }
        }
        timer.start()
        if (!trySetNewTime()) {
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSION_LOCATION_REQUEST_CODE)
        } else if ((baseContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager).isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            accessProvider = true
        } else {
            dialogManager.setScreen(dialogWarning)
        }
        //endregion
    }

    //region permissions
    val PERMISSION_LOCATION_REQUEST_CODE = 32
    var accessPermissionLocation = false
    var accessProvider = true
    var mDif = 0L
    val MINIMUM_DISTANCE_CHANGE_FOR_UPDATES: Float = 0F
    val MINIMUM_TIME_BETWEEN_UPDATES: Long = 1000
    var warningCounter = 0

    fun trySetNewTime(): Boolean {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val locationManager = (baseContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager)
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                MINIMUM_TIME_BETWEEN_UPDATES,
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, object : LocationListener {
                    override fun onLocationChanged(location: Location) {

                    }
                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
                    override fun onProviderEnabled(provider: String) {
                        accessProvider = true
                    }
                    override fun onProviderDisabled(provider: String) {
                        accessProvider = false
                        if (warningCounter >= 2) {
                            dialogManager.setScreen(dialogWarning)
                            warningCounter++
                        }
                    }
                })

            val location = (baseContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager).getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            if (location != null) {
                setNewTime(location)
                accessPermissionLocation = true
            }
            return true
        } else {
            return false
        }
    }

    fun setNewTime(l: Location) {
        val time = l.time
        Log.i("P0", SimpleDateFormat("hh:mm:ss", Locale.US).format(Date(time)))
        if (h.gems < 3) {
            val date1 = Date(h.timeMills)
            val date2 = Date(time)
            val dMills = date2.time - date1.time
            mDif = (dMills / (60 * 1000)) % 60
            Log.i("Date Time", "Difference between dates in minutes: $mDif")
            when {
                mDif >= 3 -> {
                    h.gems += 3
                    h.timeMills = time
                }
                mDif >= 2 -> {
                    h.gems += 2
                    h.timeMills = time
                }
                mDif >= 1 -> {
                    h.gems += 1
                    h.timeMills = time
                }
            }
        } else {
            Log.i("P3", "Сброс времени")
            h.timeMills = time
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_LOCATION_REQUEST_CODE) {
            val index1 = permissions.indexOf(Manifest.permission.ACCESS_COARSE_LOCATION)
            val index2 = permissions.indexOf(Manifest.permission.ACCESS_FINE_LOCATION)
            Log.i("P1", permissions.contentDeepToString())
            Log.i("P1", grantResults.contentToString())
            if (grantResults[index1] == PackageManager.PERMISSION_GRANTED && grantResults[index2] == PackageManager.PERMISSION_GRANTED) {
                trySetNewTime()
            } else {
                Log.i("P1", "Permission access deny")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PERMISSION_LOCATION_REQUEST_CODE) {
            trySetNewTime()
        }
    }
    //endregion

    private fun loadMedia() {
        trackStatUp = MediaPlayer.create(this, R.raw.stat_up)
        trackFon = MediaPlayer.create(this, R.raw.fon_music)
        trackFon.isLooping = true
        trackLove = MediaPlayer.create(this, R.raw.fon_love)
        trackLove.isLooping = true
        trackTown = MediaPlayer.create(this, R.raw.fon_town)
        trackTown.isLooping = true
        trackTragedic = MediaPlayer.create(this, R.raw.fon_tragedic)
        trackTragedic.isLooping = true
        trackHorror = MediaPlayer.create(this, R.raw.fon_horror)
        trackHorror.isLooping = true
        trackRoom = MediaPlayer.create(this, R.raw.fon_room)
        trackRoom.isLooping = true
        trackStress = MediaPlayer.create(this, R.raw.stress)
        choicePath = MediaPlayer.create(this, R.raw.choice_path)
        trackStress.isLooping = true

        trackTimer = MediaPlayer.create(this, R.raw.dialog_timer)

        mp = trackFon
    }

    //region effects
    fun blood() {
        blood.setBackgroundResource(R.color.colorBlood)
        animMessageOut = AnimationUtils.loadAnimation(this, R.anim.message_out)
        animMessageOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                blood.alpha = 0.0f
            }

            override fun onAnimationStart(animation: Animation?) {}

        })
        blood.animate().setDuration(600L).alpha(1F).start()
        handler.postDelayed({ blood.startAnimation(animMessageOut) }, 800L)
    }

    fun sleep() {
        blood.setBackgroundResource(R.color.colorBlack)
        blood.setOnTouchListener { v, _ ->
            v.performClick()
        }
        animMessageOut = AnimationUtils.loadAnimation(this, R.anim.message_out)
        animMessageOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                blood.alpha = 0.0f
            }

            override fun onAnimationStart(animation: Animation?) {}
        })
        blood.animate().setDuration(600L).alpha(1F).start()
        handler.postDelayed({ blood.startAnimation(animMessageOut) }, 800L)
    }

    //endregion

    //region messages
    private fun clearMessage() {
        delta.text = ""
        icon_stat.setImageResource(0)
    }

    fun setPath(idText: Int) {
        text_stat.setText(idText)
        clearMessage()
        when (idText) {
            R.string.path_fame_h,
            R.string.path_fame_l -> {
                icon_stat.setImageResource(R.drawable.icon_stat3)
            }
            R.string.path_stat1 -> {
                icon_stat.setImageResource(R.drawable.icon_stat1)
            }
            R.string.path_stat2 -> {
                icon_stat.setImageResource(R.drawable.icon_stat2)
            }
        }
        startMessageAnimation()
        if (soundEnable) {
            choicePath.seekTo(0)
            choicePath.start()
        }
    }

    private fun startMessageAnimation() {
        val fadeIn = Fade().apply { duration = 1000L; mode = MODE_IN; addTarget(message) }
        val fadeOut = Fade().apply { duration = 1000L; mode = MODE_OUT; addTarget(message) }
        fadeIn.addListener(onEnd = {
            handler.postDelayed({
                TransitionManager.beginDelayedTransition(message.parent as ViewGroup?, fadeOut)
                message.visibility = View.INVISIBLE
            }, 2800L)
        })
        TransitionManager.beginDelayedTransition(message.parent as ViewGroup?, fadeIn)
        message.visibility = View.VISIBLE
    }

    private fun animMessage() {
        startMessageAnimation()
        if (soundEnable) {
            trackStatUp.start()
        }
    }
    //endregion

    fun setFon(idFon: Int) {
        setFon(idFon, true)
    }

    fun setFon(idFon: Int, b: Boolean) {
        if(b) {
            h.idFon = idFon
        }
        val duration = if (h.cellIndex == 0) {
            0
        } else {
            ANIM_TIME / 2
        }
        fon.animate().alpha(0F).setDuration(duration).withEndAction {
            fon.setBackgroundResource(idFon)
            fon.animate().alpha(1F).setDuration(ANIM_TIME / 2).start()
        }.start()
    }

    //region setters state heroes
    fun setLeftHero(idHeroFon: Int) {
        hero_l.setImageResource(idHeroFon)
    }

    fun setRightHero(idHeroFon: Int) {
        hero_r.setImageResource(idHeroFon)
    }
    //endregion

    //region in/out anim heroes
    private fun animLeftHeroIn() {
        hero_l.visibility = View.VISIBLE
        val animationIn = AnimationUtils.loadAnimation(this, R.anim.flip_in_reverse)
        hero_l.startAnimation(animationIn)
    }

    private fun animLeftHeroOut() {
        val animationOut = AnimationUtils.loadAnimation(this, R.anim.flip_in_reverse_back)
        animationOut.repeatMode = Animation.REVERSE
        animationOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                hero_l.visibility = View.INVISIBLE
            }

            override fun onAnimationStart(animation: Animation?) {}

        })
        hero_l.startAnimation(animationOut)
    }

    private fun animRightHeroIn() {
        hero_r.visibility = View.VISIBLE
        val animationIn = AnimationUtils.loadAnimation(this, R.anim.flip_in_reverse)
        hero_r.startAnimation(animationIn)
    }

    private fun animRightHeroOut() {
        val animationOut = AnimationUtils.loadAnimation(this, R.anim.flip_in_reverse_back)
        animationOut.repeatMode = Animation.REVERSE
        animationOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                hero_r.visibility = View.INVISIBLE
            }

            override fun onAnimationStart(animation: Animation?) {}

        })
        hero_r.startAnimation(animationOut)
    }
    //endregion

    protected fun getHeroName(source: Int): Int {
        return when (source) {
            R.drawable.hero_takko,
            R.drawable.hero_takko_smile,
            R.drawable.hero_takko_wonder,
            R.drawable.hero_takko_sadness,
            R.drawable.hero_takko_rage -> R.string.takko
            R.drawable.hero_kaysa -> R.string.kaysa
            R.drawable.hero_agnet,
            R.drawable.hero_agnet_smile,
            R.drawable.hero_agnet_wonder,
            R.drawable.hero_agnet_sadness,
            R.drawable.hero_agnet_rage -> R.string.agnet
            R.drawable.hero_veren -> R.string.veren
            R.drawable.hero_judge -> R.string.judge
            R.drawable.hero_old_lady -> R.string.old_lady
            R.drawable.hero_magraf,
            R.drawable.hero_magraf_smile,
            R.drawable.hero_magraf_wonder,
            R.drawable.hero_magraf_sadness,
            R.drawable.hero_magraf_rage -> R.string.margraf
            R.drawable.hero_ditmar -> R.string.ditmar
            R.drawable.hero_worker -> R.string.worker
            R.drawable.hero_widow -> R.string.widow
            R.drawable.hero_gg -> R.string.hero_gg
            else -> R.string.takko
        }
    }

    protected fun getTitersHeroName(source: Int): Int {
        return when (source) {
            R.drawable.hero_takko -> R.string.akka_holm
            R.drawable.hero_kaysa -> R.string.tester
            R.drawable.hero_agnet -> R.string.alla
            R.drawable.hero_veren -> R.string.nikita
            R.drawable.hero_magraf -> R.string.kostya
            else -> R.string.takko
        }
    }

    data class EpisodeSource(
        var episode: () -> Episode, var index: () -> Int, val inc: () -> Unit, val getHeroName: KFunction1<Int, Int>
    )

    lateinit var episodeSourceMain: EpisodeSource
    lateinit var episodeSourceTiters: EpisodeSource
    lateinit var src: EpisodeSource

    fun nextCell() {
        if (src.index.invoke() < src.episode.invoke().cells.size) {
            if (src.index.invoke() > 0) {
                lastCell = src.episode.invoke().cells[src.index.invoke() - 1]
            }
            val c = src.episode.invoke().cells[src.index.invoke()]

            when (c.javaClass) {
                Cell::class.java -> {
                    val screen = ScreenCell(root, c)
                    manager.setScreen(screen)
                }
                CellHero::class.java -> {
                    val screen = ScreenCellHero(root, c as CellHero)
                    manager.setScreen(screen)
                }
                CellDialog::class.java -> {
                    val screen = ScreenCellDialog(root, c as CellDialog)
                    manager.setScreen(screen)
                }
                CellSelectItem::class.java -> {
                    val screen = ScreenCellSelectItem(root, c as CellSelectItem)
                    manager.setScreen(screen)
                }
                BaseCell::class.java -> {
                    val screen = ScreenCellEpisodeEnd(root)
                    manager.setScreen(screen)
                }
            }
            src.inc.invoke()
            Log.e("cellIndex", src.index.invoke().toString())
        } else {
            src = episodeSourceMain
            manager.setScreen(screenMenu)
        }
    }


    override fun onResume() {
        super.onResume()
        mp?.start()
    }

    override fun onPause() {
        super.onPause()
        mp?.pause()
        db.updateHero(h)
    }


    override fun onDestroy() {
        super.onDestroy()
        mp?.release()
    }

    override fun onBackPressed() {
        val dialogs = arrayOf(dialogWarning, dialogTimer, dialogCoins)
        if (!dialogs.contains(manager.currentScreen)) {
            if (manager.currentScreen != screenMenu) {
                manager.setScreen(screenMenu)
            } else {
                super.onBackPressed()
            }
        }
    }
}
