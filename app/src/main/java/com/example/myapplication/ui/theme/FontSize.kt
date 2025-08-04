package com.example.myapplication.ui.theme

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

sealed class FontSize(val sp: TextUnit) {

    data object ExtraSmall : FontSize(8.sp)
    data object Small : FontSize(12.sp)
    data object Medium : FontSize(16.sp)
    data object Large : FontSize(20.sp)
    data object ExtraLarge : FontSize(24.sp)
}