package com.example.myapplication.navigation

import android.util.Log
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

class BackstackWrapper(
    val value: NavBackStack
) {
    fun onBack() {
        if (value.size > 1)
            value.removeAll { it != Routes.Profile }
        else
            value.removeLastOrNull()
    }

    fun add(key: NavKey) {
        value.removeAll { it::class == key::class }
        value.add(key)
        Log.d("BackstackWrapperSize", " ${value.size}")
    }

    fun last(): NavKey {
        return value.last()
    }

    fun getPreviousRoute(): NavKey? {
        return value.getOrNull(value.lastIndex - 1)
    }
}