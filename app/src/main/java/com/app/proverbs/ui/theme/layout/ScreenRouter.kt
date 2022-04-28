package com.app.proverbs.ui.theme.layout

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import com.app.proverbs.model.Proverb
import com.app.proverbs.ui.theme.layout.ScreenRouter.prov
import com.app.proverbs.viewmodel.MainViewModel

object ScreenRouter {
    var currentScreen: MutableState<Int> = mutableStateOf(1)
    var prov: Proverb = Proverb()

    fun navigateTo(destination: Int) {
        currentScreen.value = destination
    }

    fun showProverb(proverb: Proverb) {
        currentScreen.value = 2
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
    val favoriteProverbs by viewModel.favoriteProverbs.observeAsState(listOf())
    var isFavorite = false

    when (ScreenRouter.currentScreen.value) {
        1 -> MainScreen(viewModel, allProverbs)

        2 -> {
            for (i in favoriteProverbs.indices) {
                if (favoriteProverbs[i].id == prov.id)
                    isFavorite = true
            }
            ProverbScreen(LocalContext.current.applicationContext, viewModel, prov, isFavorite)

        }

        3 -> FavoritesScreen(favoriteProverbs)
    }
}