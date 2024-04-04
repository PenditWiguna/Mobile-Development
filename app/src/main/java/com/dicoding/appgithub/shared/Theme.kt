package com.dicoding.appgithub.shared

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class Theme(cntx: Context?) {
    private val prefs: SharedPreferences

    init {
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx)
    }

    var choose: String?
        get() = prefs.getString("choose", "White")
        set(choose) {
            prefs.edit().putString("choose", choose).commit()
        }
}