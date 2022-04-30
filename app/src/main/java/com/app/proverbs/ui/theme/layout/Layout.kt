package com.app.proverbs.ui.theme.layout

import android.app.Application
import android.content.Context
import android.provider.ContactsContract
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import com.app.proverbs.model.Proverb
import com.app.proverbs.viewmodel.MainViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.sharp.Home
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import com.app.proverbs.database.RepositoryProverb
import com.app.proverbs.ui.theme.Purple700

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(context: Context, vm: MainViewModel, prov: List<Proverb>) {

    var keyword by remember { mutableStateOf("") }
    var searchButtonClicked by remember { mutableStateOf(false) }
    val rep = RepositoryProverb(vm.dao)

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
                        ScreenRouter.navigateTo(3)
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
                            .padding(8.dp).combinedClickable(
                                onClick = { ScreenRouter.showProverb(proverb) },
                                onLongClick = {
                                    rep.removeProverb(proverb)
                                    Toast
                                        .makeText(
                                            context,
                                            "Elemento eliminato",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }
                            )
                    ) {
                            Text(
                                text = proverb.text,
                                fontSize = 20.sp,
                                modifier = Modifier.background(Color(0xFFFFF8DC)).fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontWeight = SemiBold
                            )


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
    },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val proverb = Proverb()
                ScreenRouter.navigateTo(4)
            }, backgroundColor = Purple700) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Button Icon",
                    tint = Color.White
                )
            }
        }
    )
}

@Composable
fun AddProverbScreen(context: Context, viewModel: MainViewModel) {

    var text by remember { mutableStateOf("") }
    val rep = RepositoryProverb(viewModel.dao)

    Scaffold(topBar = {
        TopAppBar(contentColor = Color.White) {

            Text(
                text = "Proverbi",
                modifier = Modifier.padding(horizontal = 20.dp),
                fontSize = 20.sp,
                fontWeight = Bold
            )
        }
    }, content = {
        Column(
            modifier = Modifier.padding(20.dp),
        ) {

            Card(backgroundColor = Color(0xFFFFF8DC), modifier = Modifier.fillMaxWidth()) {
                TextField(value = text, onValueChange = { text = it })
            }

            Row(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .align(Alignment.End)
            ) {
                Button(onClick = {
                    ScreenRouter.navigateTo(1)
                }, modifier = Modifier.padding(horizontal = 10.dp)) {
                    Text(text = "Indietro")
                }
                Button(onClick = {
                    val proverb = Proverb()
                    proverb.text = text
                    proverb.language = "Italiano"
                    rep.insertProverb(proverb)
                    Toast.makeText(context, "Elemento inserito correttamente", Toast.LENGTH_SHORT)
                        .show()
                }) {
                    Text(text = "Inserisci")
                }
            }
        }
    })
}


@Composable
fun ProverbScreen(context: Context, viewModel: MainViewModel, proverb: Proverb) {

    val col: Color = if (proverb.favorite == 1) Color.Red else Color.Black
    var color: Color by remember { mutableStateOf(col) }
    val rep = RepositoryProverb(viewModel.dao)
    var newText by remember { mutableStateOf(proverb.text) }

    Scaffold(topBar = {
        TopAppBar(contentColor = Color.White) {

            Text(
                text = "Proverbi",
                modifier = Modifier.padding(horizontal = 20.dp),
                fontSize = 20.sp,
                fontWeight = Bold
            )
        }
    }, content = {
        Column(
            modifier = Modifier.padding(20.dp),
        ) {

            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.align(Alignment.End)
            ) {
                IconButton(
                    onClick = {
                        if (color != Color.Red) {
                            color = Color.Red
                            rep.addToFavorites(proverb)
                        } else {
                            color = Color.Black
                            rep.removeFromFavorites(proverb)
                        }
                    }, modifier = Modifier
                        .padding(vertical = 12.dp)
                        .size(23.dp)
                ) {
                    Icon(
                        Icons.Filled.Favorite,
                        contentDescription = "Add to Favorites icon",
                        tint = color
                    )
                }

                IconButton(
                    onClick = {
                        rep.removeProverb(proverb)
                        Toast.makeText(context, "Elemento eliminato", Toast.LENGTH_SHORT).show()
                        ScreenRouter.navigateTo(1)
                    }, modifier = Modifier
                        .padding(vertical = 12.dp)
                        .size(23.dp)
                ) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "Delete icon",
                        tint = Color.Black
                    )
                }
            }



            Card(backgroundColor = Color(0xFFFFF8DC), modifier = Modifier.fillMaxWidth()) {
                TextField(value = newText, onValueChange = { newText = it })
            }

            Row(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .align(Alignment.End)
            ) {
                Button(onClick = {
                    ScreenRouter.navigateTo(1)
                }, modifier = Modifier.padding(horizontal = 10.dp)) {
                    Text(text = "Indietro")
                }
                Button(onClick = {
                    rep.updateProverbText(proverb, newText)
                    Toast.makeText(context, "Elemento salvato correttamente", Toast.LENGTH_SHORT)
                        .show()
                    ScreenRouter.navigateTo(1)
                }) {
                    Text(text = "Salva")
                }
            }
        }
    })

}

@Composable
fun FavoritesScreen(prov: List<Proverb>) {

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
                        ScreenRouter.navigateTo(1)
                    }) {
                        Icon(
                            Icons.Sharp.Home,
                            contentDescription = "home icon",
                            tint = Color.White,
                            modifier = Modifier.alpha(ContentAlpha.medium)
                        )
                    }

                    IconButton(onClick = {
                        ScreenRouter.navigateTo(3)
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
                            onClick = { ScreenRouter.showProverb(proverb) },
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
