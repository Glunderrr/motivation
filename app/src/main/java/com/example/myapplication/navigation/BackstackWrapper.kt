package com.example.myapplication.navigation

import android.util.Log
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

class BackstackWrapper(
    val value: NavBackStack
) {
    // Повертає до попереднього екрана, залишаючи Profile як корінний екран навігації
    fun onBack() {
        if (value.size > 1)
            value.removeAll { it != Routes.Profile }
        else
            value.removeLastOrNull()
    }

    // Додає новий екран до стеку, видаляючи попереднє входження того ж типу для уникнення дублікатів
    fun add(key: NavKey) {
        value.removeAll { it::class == key::class }
        value.add(key)
        Log.d("BackstackWrapperSize", " ${value.size}")
    }

    // Повертає поточний активний маршрут (верхній елемент стеку)
    fun last(): NavKey {
        return value.last()
    }

    // Повертає попередній маршрут у стеку для визначення напряму анімації переходу
    fun getPreviousRoute(): NavKey? {
        return value.getOrNull(value.lastIndex - 1)
    }
}
