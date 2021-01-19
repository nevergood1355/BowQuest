package com.quest.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.app.ActivityOptionsCompat.makeBasic
import com.quest.MainActivity
import com.quest.R
import com.thekingames.screen.Fragment
import com.thekingames.screen.Screen
import kotlinx.android.synthetic.main.dialog_hint_permission.view.*


class DialogWarning(parent: ViewGroup) : Screen(parent, R.layout.dialog_hint_permission) {
    val a = activity as MainActivity
    init {
        view.buy.setOnClickListener {
            openApplicationSettings()
            parent.removeView(view)
        }

        view.close.setOnClickListener {
            parent.removeView(view)
        }
    }

    fun openApplicationSettings() {
        val appSettingsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(activity, appSettingsIntent, ((activity as MainActivity).PERMISSION_LOCATION_REQUEST_CODE), Bundle(makeBasic().toBundle()))
    }
}