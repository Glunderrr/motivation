package com.example.myapplication.view.screens.bottom.add

import com.example.myapplication.R

sealed class DrawerElement(
    val route: String,
    val titleId: Int,
) {
    object Generate : DrawerElement(
        route = "generate",
        titleId = R.string.drawer_generate,
    )

    object CreateOwn : DrawerElement(
        route = "create_own",
        titleId = R.string.drawer_create_own,
    )

    object AboutProgram : DrawerElement(
        route = "about_program",
        titleId = R.string.drawer_about_program,
    )
}