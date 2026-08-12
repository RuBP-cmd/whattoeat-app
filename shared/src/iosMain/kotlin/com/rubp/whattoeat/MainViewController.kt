package com.rubp.whattoeat

import androidx.compose.ui.window.ComposeUIViewController
import com.rubp.whattoeat.data.local.database.AppDatabase
import com.rubp.whattoeat.data.local.database.getDatabaseBuilder

fun MainViewController() = ComposeUIViewController {
    AppDatabase.init(getDatabaseBuilder())
    App()
}