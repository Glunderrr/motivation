package com.example.myapplication.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey


class BackstackWrapper(
    val value: NavBackStack
) {
    fun onBack(): NavKey? {
        return value.removeLastOrNull()
    }

    fun add(key: NavKey) {
        if (key == Routes.Profile) {
            value.clear()
            value.add(Routes.Profile)
        } else {
            value.removeAll { it != Routes.Profile }
            if (!value.contains(Routes.Profile)) {
                value.add(Routes.Profile)
            }
            value.add(key)
        }
    }

    fun last(): NavKey {
        return value.last()
    }

    fun getPreviousRoute(): NavKey? {
        return value.getOrNull(value.lastIndex - 1)
    }
}