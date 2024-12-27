package com.jop.learncompose.data

import android.content.Context

class AppData(context: Context) {
    private val PREFS_NAME = "NOTE_ME"
    private val IS_GRID = "IS_GRID"

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setIsGrid(isGrid: Boolean) {
        prefs.edit().putBoolean(IS_GRID, isGrid).apply()
    }

    fun getIsGrid() = prefs.getBoolean(IS_GRID, false)
}