package com.example.myapplication.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

sealed class Paddings(val dp: Dp) {
    data object ExtraSmall : Paddings(4.dp)
    data object Small : Paddings(8.dp)
    data object Medium : Paddings(12.dp)
    data object Large : Paddings(16.dp)

    data object ExtraLarge : Paddings(20.dp)
}
