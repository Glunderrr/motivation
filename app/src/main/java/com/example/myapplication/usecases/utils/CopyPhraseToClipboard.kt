package com.example.myapplication.usecases.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.Log
import com.example.myapplication.data.model.Phrase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CopyPhraseToClipboard @Inject constructor(
    @ApplicationContext
    private val applicationContext: Context,
) {
    fun invoke(
        phrase: Phrase,
    ) {
        val clipboardManager =
            applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Phrase", phrase.phrase)
        clipboardManager.setPrimaryClip(clipData)
    }
}