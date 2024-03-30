package com.francescapavone.menuapp.ui.layout

import android.app.Application
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.francescapavone.menuapp.R
import com.francescapavone.menuapp.db.RestaurantEntity
import com.francescapavone.menuapp.model.Course
import com.francescapavone.menuapp.ui.components.CourseCard
import com.francescapavone.menuapp.ui.theme.myGreen
import com.francescapavone.menuapp.ui.theme.myYellow
import com.francescapavone.menuapp.utils.ScreenRouter
import com.francescapavone.menuapp.viewmodel.MainViewModel
import com.github.sumimakito.awesomeqr.AwesomeQrRenderer
import com.github.sumimakito.awesomeqr.option.RenderOption


@Composable
fun Menu(
    restaurantId: MutableState<Int>,
    starters: SnapshotStateList<Course>,
    firstcourses: SnapshotStateList<Course>,
    secondcourses: SnapshotStateList<Course>,
    sides: SnapshotStateList<Course>,
    fruits: SnapshotStateList<Course>,
    desserts: SnapshotStateList<Course>,
    drinks: SnapshotStateList<Course>,
    subtotal: MutableState<Double>,
    orderList: MutableList<Course>
){
    val scaffoldState = rememberScaffoldState()

    val context = LocalContext.current.applicationContext
    val viewModel = MainViewModel(context as Application)
    val allFav by viewModel.allfavourite.observeAsState(listOf())
    val idList = mutableListOf<String>()

    val isFav = remember { mutableStateOf(true) }

//    val message = rememberSaveable{ mutableStateOf(if(!isFav.value) "Add to favourites" else "Remove from favourites") }
//    val favIcon = rememberSaveable { mutableStateOf(if(!restaurant.value.favourite) R.drawable.add_favorite else R.drawable.remove_favorite) }

    val openDialog = remember { mutableStateOf(false) }

    for(i in allFav){
        idList.add(i.id)
    }

    if(!starters.isEmpty()) {
        isFav.value = starters[0].restaurantId in idList
    }

    if (openDialog.value) {
        println(" Button pressed SHARE for id: " + starters[0].restaurantId)
        //va bene qualsiasi id, prendo per semplicità quello del primo della lista
        //CREATE QR CODE
        val color = com.github.sumimakito.awesomeqr.option.color.Color()
        color.light = 0xFFFFFFFF.toInt() // for blank spaces
        color.dark = 0xFF2E7855.toInt() // for non-blank spaces
        color.background =
            0xFFFFFFFF.toInt() // for the background (will be overriden by background images, if set)
        color.auto =
            false // set to true to automatically pick out colors from the background image (will only work if background image is present)
        val renderOption = RenderOption()
        renderOption.content =
            "https://github.com/al3ssandrocaruso/restaurantsappdata/raw/main/menus/PDFsMenu/${starters[0].restaurantId}.pdf" // content to encode
        println("Generated QR Code for url: " + renderOption.content)
        renderOption.size = 600 // size of the final QR code image
        renderOption.borderWidth = 20 // width of the empty space around the QR code
        renderOption.patternScale = 0.35f // (optional) specify a scale for patterns
        //renderOption.roundedPatterns = true // (optional) if true, blocks will be drawn as dots instead
        renderOption.clearBorder =
            true // if set to true, the background will NOT be drawn on the border area
        renderOption.color = color // set a color palette for the QR code
        val result = AwesomeQrRenderer.render(renderOption)
        Dialog(onDismissRequest = { openDialog.value = false }) {
            Image(
                bitmap = result.bitmap!!.asImageBitmap(),
                contentDescription = "some useful description",
            )
            // Image(painter = painterResource(id = R.drawable.qrcode), contentDescription = null)
        }
    }



    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.bg_app),
        contentDescription = "bg",
        contentScale = ContentScale.FillBounds
    )
    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = Color.Transparent,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { ScreenRouter.navigateTo(3) },
                modifier = Modifier.size(64.dp) ,
                backgroundColor = myYellow,
                contentColor = myGreen,
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(5.dp),
            ) {
                Image(painter = painterResource(id = R.drawable.shop_bag2), contentDescription = "shoppingCart")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
//        topBar = { CartTopBar(restaurant) }
    ) {
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(top = 20.dp)
        ) {

            /*Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .background(color = myYellow, shape = RoundedCornerShape(30))
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        restaurant.value.favourite = !restaurant.value.favourite
                        if (restaurant.value.favourite) message.value =
                            "Remove from favourites" else message.value = "Add to favourites"
                    }
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 20.dp, top = 10.dp, bottom = 10.dp),
                    text = message.value,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Divider(
                    color = myGreen,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxHeight()
                        .width(1.dp)
                )
                Image(
                    modifier = Modifier
                        .padding(end = 20.dp, top = 10.dp, bottom = 10.dp),
                    painter = painterResource(id = if (restaurant.value.favourite) R.drawable.remove_favourite else R.drawable.add_favourite),
                    contentDescription = null,
                )
            }
*/
            Row (
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ){
/*
                IconButton(
                    onClick = { restaurant.value.favourite = !restaurant.value.favourite
                        if (restaurant.value.favourite) favIcon.value = R.drawable.remove_favorite
                        else  favIcon.value = R.drawable.add_favorite
                    },
                    modifier = Modifier
                        .padding(end = 50.dp)
                        .align(Alignment.CenterVertically)
                        .width(50.dp)
                        .height(50.dp)
                        .background(color = myYellow, shape = RoundedCornerShape(50)))
                {
                    Icon(modifier = Modifier.padding(15.dp), painter = painterResource(id = R.drawable.add_favorite /*favIcon.value*/), tint = myGreen,  contentDescription = "add")
                }
*/
                IconButton(
                    onClick = { openDialog.value = true },
                    modifier = Modifier
                        .padding(end = 50.dp)
                        .align(Alignment.CenterVertically)
                        .size(50.dp)
                        .background(color = myYellow, shape = RoundedCornerShape(50)))
                {
                    Icon(modifier = Modifier.padding(15.dp), painter = painterResource(id = R.drawable.share), tint = myGreen,  contentDescription = "add")
                }

                TextButton(
                    onClick = {
                        val restaurant = RestaurantEntity()
                        restaurant.id = starters[0].restaurantId

                        if(!isFav.value){ //se non è tra i preferiti
                            println("pressed for adding to fav id: ${starters[0].restaurantId}")
                            viewModel.updateFavoriteOn(restaurant)
                        } else {
                            println("pressed for remove from fav id: ${starters[0].restaurantId}")
                            viewModel.updateFavoriteOff(restaurant)
                        }
                    },
                    modifier = Modifier
                        .height(50.dp),
                    shape = RoundedCornerShape(50),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = myYellow,
                        contentColor = myGreen)
                ) {
                    Text(
                        text = if(!isFav.value) "Add to favorite" else "Remove",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                        )
                }

                IconButton(
                    onClick = {  },
                    modifier = Modifier
                        .padding(start = 50.dp)
                        .align(Alignment.CenterVertically)
                        .size(50.dp)
                        .background(color = myYellow, shape = RoundedCornerShape(50)))
                {
                    Icon(
                        modifier = Modifier.padding(13.dp),
                        painter = painterResource(id = R.drawable.download),
                        tint = myGreen,
                        contentDescription = "add"
                    )
                }
            }

            Title(title = stringResource(R.string.starters))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(20.dp),
            ){
                items(
                    items = starters,
                    itemContent = {
                        CourseCard(course = it, subtotal =  subtotal, orderList = orderList, restaurantId = restaurantId)
                    }
                )
            }

            Title(title = stringResource(R.string.first))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(20.dp)
            ){
                items(
                    items = firstcourses,
                    itemContent = {
                        CourseCard(course = it, subtotal = subtotal, orderList = orderList, restaurantId = restaurantId)
                    }
                )
            }

            Title(title = stringResource(R.string.second))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(20.dp)
            ){
                items(
                    items = secondcourses,
                    itemContent = {
                        CourseCard(course = it, subtotal = subtotal, orderList = orderList, restaurantId = restaurantId)
                    }
                )
            }

            Title(title = stringResource(R.string.sides))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(20.dp)
            ){
                items(
                    items = sides,
                    itemContent = {
                        CourseCard(course = it, subtotal = subtotal, orderList = orderList, restaurantId = restaurantId)
                    }
                )
            }

            Title(title = stringResource(R.string.fruits))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(20.dp)
            ){
                items(
                    items = fruits,
                    itemContent = {
                        CourseCard(course = it, subtotal = subtotal, orderList = orderList, restaurantId = restaurantId)
                    }
                )
            }

            Title(title = stringResource(R.string.Desserts))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(20.dp)
            ){
                items(
                    items = desserts,
                    itemContent = {
                        CourseCard(course = it, subtotal = subtotal, orderList = orderList, restaurantId = restaurantId)
                    }
                )
            }

            Title(title = stringResource(R.string.drinks))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(20.dp)
            ){
                items(
                    items = drinks,
                    itemContent = {
                        CourseCard(course = it, subtotal = subtotal, orderList = orderList, restaurantId = restaurantId)
                    }
                )
            }
            Spacer(modifier = Modifier
                .height(100.dp)
                .fillMaxWidth())
        }

    }
    BackHandler() {
        ScreenRouter.navigateTo(1)
    }
}



/*@Composable
fun CartTopBar(restaurant: MutableState<Restaurant>){
}*/

@Composable
fun Title(title: String){
    Divider(
        modifier = Modifier.padding(20.dp),
        color = Color.Black,
        thickness = 1.dp
    )
    Text(
        modifier = Modifier.padding(start = 20.dp, bottom = 10.dp),
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
}
