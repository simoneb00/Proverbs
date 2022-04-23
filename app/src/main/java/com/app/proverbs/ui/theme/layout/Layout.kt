package com.app.proverbs.ui.theme.layout

import android.app.Application
import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import com.app.proverbs.model.Proverb
import com.app.proverbs.viewmodel.MainViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.sharp.Home
import androidx.compose.material.icons.twotone.Home
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.app.proverbs.ui.theme.Purple700
import com.app.proverbs.ui.theme.layout.ScreenRouter.prov

@Composable
fun MainScreen(vm: MainViewModel, prov: List<Proverb>) {

    var keyword by remember { mutableStateOf("") }
    var searchButtonClicked by remember { mutableStateOf(false) }

    Scaffold(topBar = {

        if (!searchButtonClicked) {

            TopAppBar(contentColor = Color.White) {

                Text(
                    text = "Proverbi",
                    modifier = Modifier.padding(horizontal = 20.dp),
                    fontSize = 20.sp,
                    fontWeight = Bold
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.End
                ) {

                    IconButton(onClick = {
                        val proverb: Proverb = Proverb()
                        ScreenRouter.navigateTo(3, proverb)
                    }) {
                        Icon(
                            Icons.Default.Favorite,
                            contentDescription = "favorites icon"
                        )
                    }

                    IconButton(onClick = {
                        searchButtonClicked = true
                    }, modifier = Modifier.align(Alignment.Bottom)) {

                        Icon(
                            Icons.Default.Search,
                            contentDescription = "search icon"
                        )
                    }
                }
            }
        } else {
            TopAppBar(contentColor = Color.White) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp), color = Purple700
                ) {
                    TextField(
                        value = keyword,
                        onValueChange = { keyword = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .alpha(ContentAlpha.medium),
                        colors = TextFieldDefaults.textFieldColors(Color.White),
                        placeholder = { Text(text = "Cerca", color = Color.White) },
                        singleLine = true,
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    if (keyword.isNotEmpty())
                                        keyword = ""
                                },
                                modifier = Modifier.alpha(ContentAlpha.medium)
                            ) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = "delete icon",
                                    tint = Color.White
                                )
                            }
                        },
                        leadingIcon = {
                            IconButton(
                                onClick = {
                                    keyword = ""
                                    searchButtonClicked = false
                                }
                            ) {
                                Icon(
                                    Icons.Default.ArrowBack,
                                    contentDescription = "back icon",
                                    tint = Color.White
                                )
                            }
                        }
                    )
                }
            }
        }
    }, content = {


        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {

            val filteredList: List<Proverb> = prov.filter { proverb ->
                (proverb.text.uppercase().contains(keyword.uppercase()))
            }

            items(filteredList) { proverb ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .background(Color(0xFFFFF8DC))
                            .padding(8.dp)
                    ) {

                        TextButton(
                            onClick = { ScreenRouter.navigateTo(2, proverb) },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                        )
                        {
                            Text(
                                text = proverb.text,
                                fontSize = 20.sp,
                                modifier = Modifier.background(Color(0xFFFFF8DC))
                            )
                        }

                        Divider(
                            color = Color.Black,
                            thickness = 1.dp,
                            modifier = Modifier
                                .fillMaxWidth(.5f)
                                .align(Alignment.CenterHorizontally)
                                .padding(10.dp)
                        )
                    }
                }

            }


        }

    })
}


@Composable
fun ProverbScreen(proverb: Proverb) {

    var color: Color by remember { mutableStateOf(Color.Black) }
    var isRed: Boolean by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(20.dp),
    ) {

        IconButton(
            onClick = {
                if (!isRed) {
                    color = Color.Red
                    isRed = true
                } else {
                    color = Color.Black
                    isRed = false
                }
            }, modifier = Modifier
                .align(Alignment.End)
                .padding(vertical = 12.dp)
                .size(23.dp)
        ) {
            Icon(
                Icons.Filled.Favorite,
                contentDescription = "",
                tint = color
            )
        }


        Card(backgroundColor = Color(0xFFFFF8DC), modifier = Modifier.fillMaxWidth()) {
            var newProverbText: String = ""
            TextField(
                value = proverb.text,
                onValueChange = { newProverbText = it })
        }

        Row(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .align(Alignment.End)
        ) {
            Button(onClick = {
                val proverb: Proverb = Proverb()
                ScreenRouter.navigateTo(1, proverb)
            }, modifier = Modifier.padding(horizontal = 10.dp)) {
                Text(text = "Indietro")
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Salva")
            }
        }
    }

}

@Composable
fun favoritesScreen(prov: List<Proverb>) {

    var keyword by remember { mutableStateOf("") }
    var searchButtonClicked by remember { mutableStateOf(false) }

    Scaffold(topBar = {

        if (!searchButtonClicked) {

            TopAppBar(contentColor = Color.White) {

                Text(
                    text = "Preferiti",
                    modifier = Modifier.padding(horizontal = 20.dp),
                    fontSize = 20.sp,
                    fontWeight = Bold
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.End
                ) {

                    IconButton(onClick = {
                        val proverb: Proverb = Proverb()
                        ScreenRouter.navigateTo(1, proverb)
                    }) {
                        Icon(
                            Icons.Sharp.Home,
                            contentDescription = "home icon",
                            tint = Color.White,
                            modifier = Modifier.alpha(ContentAlpha.medium)
                        )
                    }

                    IconButton(onClick = {
                        val proverb: Proverb = Proverb()
                        ScreenRouter.navigateTo(3, proverb)
                    }) {
                        Icon(
                            Icons.Default.Favorite,
                            contentDescription = "favorites icon",
                            tint = Color.Red
                        )
                    }

                    IconButton(onClick = {
                        searchButtonClicked = true
                    }, modifier = Modifier.align(Alignment.Bottom)) {

                        Icon(
                            Icons.Default.Search,
                            contentDescription = "search icon"
                        )
                    }
                }
            }
        } else {
            TopAppBar(contentColor = Color.White) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp), color = Purple700
                ) {
                    TextField(
                        value = keyword,
                        onValueChange = { keyword = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .alpha(ContentAlpha.medium),
                        colors = TextFieldDefaults.textFieldColors(Color.White),
                        placeholder = { Text(text = "Cerca", color = Color.White) },
                        singleLine = true,
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    if (keyword.isNotEmpty())
                                        keyword = ""
                                },
                                modifier = Modifier.alpha(ContentAlpha.medium)
                            ) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = "delete icon",
                                    tint = Color.White
                                )
                            }
                        },
                        leadingIcon = {
                            IconButton(
                                onClick = {
                                    keyword = ""
                                    searchButtonClicked = false
                                }
                            ) {
                                Icon(
                                    Icons.Default.ArrowBack,
                                    contentDescription = "back icon",
                                    tint = Color.White
                                )
                            }
                        }
                    )
                }
            }
        }
    }, content = {


        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {

            val filteredList: List<Proverb> = prov.filter { proverb ->
                (proverb.text.uppercase().contains(keyword.uppercase()))
            }

            items(filteredList) { proverb ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .background(Color(0xFFFFF8DC))
                            .padding(8.dp)
                    ) {

                        TextButton(
                            onClick = { ScreenRouter.navigateTo(2, proverb) },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                        )
                        {
                            Text(
                                text = proverb.text,
                                fontSize = 20.sp,
                                modifier = Modifier.background(Color(0xFFFFF8DC))
                            )
                        }

                        Divider(
                            color = Color.Black,
                            thickness = 1.dp,
                            modifier = Modifier
                                .fillMaxWidth(.5f)
                                .align(Alignment.CenterHorizontally)
                                .padding(10.dp)
                        )
                    }
                }

            }


        }

    })
}


@Preview
@Composable
fun PreviewFunction() {

    val viewModel = MainViewModel(
        LocalContext
            .current.applicationContext as Application
    )

    val proverbs by viewModel.allProverbs.observeAsState(listOf())

    MainScreen(vm = viewModel, prov = proverbs)
}



