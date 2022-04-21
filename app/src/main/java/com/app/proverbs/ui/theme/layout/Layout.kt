package com.app.proverbs.ui.theme.layout

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.app.proverbs.model.Proverb
import com.app.proverbs.viewmodel.MainViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.sp

@Composable
fun ProverbsLayout() {
    val viewModel = MainViewModel(
        LocalContext
            .current.applicationContext as Application
    )

    val allProverbs by viewModel.allProverbs.observeAsState(listOf())
    MainScreen(viewModel, allProverbs)

}


@Composable
fun MainScreen(vm: MainViewModel, prov: List<Proverb>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        val listOfProverbs = prov
        items(listOfProverbs) { proverb ->
            Card(modifier = Modifier.padding(8.dp)) {
                Column(
                    modifier = Modifier
                        .background(Color(0xFFFFF8DC))
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = proverb.text,
                        fontSize = 32.sp,
                        modifier = Modifier.background(Color(0xFFFFF8DC))
                    )
                    Divider(
                        color = Color.Blue,
                        thickness = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth(.5f)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}