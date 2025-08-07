package com.example.myapplication.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import com.example.myapplication.R
import com.example.myapplication.data.model.Phrase
import com.example.myapplication.view.screens.bottom.add.DrawerElement
import kotlinx.serialization.Serializable
import kotlin.Boolean

@Serializable
sealed class Routes(
    val labelId: Int
) : NavKey {
    abstract fun getIcon(): ImageVector
    @Serializable
    data class Add(
        val drawerElement: String = DrawerElement.Generate.route,
        val phrase: Phrase? = null,
    ) : Routes(R.string.add) {
        override fun getIcon() = Icons.Filled.Add
    }

    @Serializable
    data object Favorites :
        Routes(R.string.favorites) {
        override fun getIcon() = Icons.Filled.Favorite
    }

    @Serializable
    data object Profile :
        Routes(R.string.profile) {
        override fun getIcon() = Icons.Default.Person
    }
}