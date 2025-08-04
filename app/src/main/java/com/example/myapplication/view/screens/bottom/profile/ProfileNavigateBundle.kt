package com.example.myapplication.view.screens.bottom.profile

import okhttp3.internal.http2.Settings

data class ProfileNavigateBundle(
    val navigateToAddScreen: () -> Unit = {},
    val navigateToEditProfileScreen: () -> Unit = {},
    val navigateToOwnPhraseScreen: () -> Unit= {}
)
