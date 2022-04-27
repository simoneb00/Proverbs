package com.app.proverbs.ui.theme.layout

import android.app.Application
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import com.app.proverbs.model.Proverb
import com.app.proverbs.ui.theme.layout.ScreenRouter.prov
import com.app.proverbs.viewmodel.MainViewModel

object ScreenRouter {
    var currentScreen: MutableState<Int> = mutableStateOf(1)
    var prov: Proverb = Proverb()

    fun navigateTo(destination: Int, proverb: Proverb) {
        currentScreen.value = destination
        prov = proverb
    }
}

@Composable
fun ProverbsLayout() {

    val viewModel = MainViewModel(
        LocalContext
            .current.applicationContext as Application
    )

    val allProverbs by viewModel.allProverbs.observeAsState(listOf())

    when (ScreenRouter.currentScreen.value) {
        1 -> MainScreen(viewModel, allProverbs)
        2 -> ProverbScreen(prov)
        3 -> favoritesScreen(allProverbs)
    }
}