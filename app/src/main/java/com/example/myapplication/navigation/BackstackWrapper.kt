package com.example.myapplication.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

class BackstackWrapper(
    val value: NavBackStack
) {
    fun onBack() {
        if (value.last() == Routes.Profile)
            value.removeLastOrNull()
        else
            value.removeAll { it != Routes.Profile }
    }


    fun add(key: NavKey) {
        if (value.contains(key))
            value.remove(key)
        if (key == Routes.Profile)
            value.clear()
        value.add(key)
    }

    fun last(): NavKey {
        return value.last()
    }

    fun getPreviousRoute(): NavKey? {
        return value.getOrNull(value.lastIndex - 1)
    }
}