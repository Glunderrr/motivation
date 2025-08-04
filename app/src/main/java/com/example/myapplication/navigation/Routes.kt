package com.example.myapplication.navigation

import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.myapplication.R
import com.example.myapplication.data.model.Phrase
import com.example.myapplication.view.screens.bottom.add.DrawerElement
import com.google.gson.Gson
import javax.inject.Singleton

sealed class Routes(
    val icon: ImageVector,
    val labelId: Int,
) {

    data class Add(
        val drawerElement: DrawerElement = DrawerElement.Generate,
        val phrase: Phrase? = null,
    ) : Routes(Icons.Filled.Add, R.string.add)

    object Favorites :
        Routes(Icons.Filled.Favorite, R.string.favorites)

    object Profile :
        Routes(Icons.Filled.Person, R.string.profile)

    object EditProfile :
        Routes(Icons.Default.Edit, R.string.edit_profile)

    companion object {
        val bottomBarRoutes = listOf<Routes>(
            Add(),
            Favorites,
            Profile,
        )
    }
}