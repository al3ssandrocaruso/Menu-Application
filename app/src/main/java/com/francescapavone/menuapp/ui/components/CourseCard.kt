package com.francescapavone.menuapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.francescapavone.menuapp.model.Course
import com.francescapavone.menuapp.ui.theme.myGreen
import com.francescapavone.menuapp.ui.theme.myYellow
import com.francescapavone.menuapp.utils.NetworkImageComponentPicasso

@Composable
fun CourseCard(course: Course/*dish: Dish*/, subtotal: MutableState<Double>, /*orderList: MutableList<Dish>*/orderList: MutableList<Course>, restaurantId: MutableState<Int> ) {

    println("here")
    val (count, updateCount) = rememberSaveable { mutableStateOf(course.count) }
    val openDialogDescription = remember { mutableStateOf(false) }
    val openDialogNewRestaurant = remember { mutableStateOf(false) }

    if (openDialogDescription.value) {
        AlertDialog(
            shape = RoundedCornerShape(14.dp),
//            modifier = Modifier.background(MaterialTheme.colors.surface),
            onDismissRequest = {
                openDialogDescription.value = false
            },
            title = {
                Text(text = course.name, fontSize = 16.sp, color = MaterialTheme.colors.onSurface, fontWeight = FontWeight.Bold)
            },
            text = {
                Text(text = course.description, fontSize = 14.sp, color = MaterialTheme.colors.onSurface)
            },
            buttons = {
                Row(
                modifier = Modifier.padding(all = 14.dp),
                horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { openDialogDescription.value = false },
                        colors = ButtonDefaults.buttonColors(backgroundColor = myYellow)
                    ) {
                        Text(
                            text = "OK",
                            color = myGreen,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        )
    }

    if (openDialogNewRestaurant.value) {
        AlertDialog(
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.background(MaterialTheme.colors.surface),
            onDismissRequest = {
                openDialogNewRestaurant.value = false
            },
            text = { Text(
                text = "Sono presenti nel carrello prodotti di un altro ristorante.\nVuoi svuotare il carrello ed ordinare da questo ristorante?",
                color = MaterialTheme.colors.onSurface
            ) },
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 14.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier
                            .wrapContentWidth(Alignment.Start),
                        onClick = {
                            orderList.removeAll(orderList)
                            orderList.add(course)
                            updateCount(count + 1)
                            course.count = count + 1
                            subtotal.value = course.price.toDouble()
                            openDialogNewRestaurant.value = false
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = myYellow)
                    ) {
                        Text(
                            text = "Accept",
                            color = myGreen,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Button(
                        modifier = Modifier.wrapContentWidth(Alignment.End),
                        onClick = {
                            openDialogNewRestaurant.value = false
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = myYellow)
                    ) {
                        Text(
                            text = "Dismiss",
                            color = myGreen,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        )
    }

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .padding(start = 20.dp)
            .clickable { openDialogDescription.value = true }
        ) {
        Card(
            modifier = Modifier
                .padding(top = 30.dp)
                .width(180.dp),
            shape = RoundedCornerShape(14.dp),
            elevation = 5.dp
        ) {
            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .padding(top = 100.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
            ) {
                Text(
                    text = course.name,
                    modifier = Modifier.padding(top = 5.dp),
                    fontSize = 16.sp
                )
                Text(
                    text = "€ ${String.format("%.2f", course.price.toDouble())}",
                    color = myYellow,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                QuantityCounter(
                    modifier = Modifier
                        .align(Alignment.End),
                    count = count,
                    remove = {
                        if (count == 1) {
                            orderList.remove(course)
                            if (orderList.isEmpty())
                                restaurantId.value = -1
                        }
                        if (count > 0) {
                            updateCount(count - 1)
                            course.count = count -1
                            subtotal.value = subtotal.value - course.price.toDouble()
                        }
                    },
                    add = {
                        if (orderList.isEmpty())
                            restaurantId.value = course.restaurantId.toInt()
                        if (count == 0 && course.restaurantId.toInt() == restaurantId.value)
                            orderList.add(course)
                        if (course.restaurantId.toInt() == restaurantId.value) {
                            updateCount(count + 1)
                            course.count = count + 1
                            subtotal.value = subtotal.value + course.price.toDouble()
                        }else{
                            openDialogNewRestaurant.value = true
                        }

                    }
                )
            }

        }
        NetworkImageComponentPicasso(url = course.poster, modifier = Modifier.clip(CircleShape), 130)

        /*Image(
            alignment = Alignment.Center,
            painter = painterResource(id = dish.image),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .size(130.dp)
        )*/
    }
}

@Composable
fun OrderedDishCard(course: Course/*dish: Dish*/, subtotal: MutableState<Double>, orderList: MutableList</*Dish*/Course>){

    val (count, updateCount) = rememberSaveable { mutableStateOf(course.count) }
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .padding(start = 20.dp)
    ){
        Card(
            modifier = Modifier
                .padding(end = 30.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(14.dp),
            elevation = 5.dp
        ){
           Row(
               horizontalArrangement = Arrangement.Start,
               modifier = Modifier
                   .padding(end = 100.dp, start = 20.dp, top = 10.dp, bottom = 10.dp)

           ) {
               NetworkImageComponentPicasso(url = course.poster, modifier = Modifier.clip(CircleShape), 90)

               /*Image(
                   alignment = Alignment.Center,
                   painter = painterResource(id = dish.image),
                   contentDescription = null,
                   modifier = Modifier
                       .clip(CircleShape)
                       .size(90.dp)
               )*/

               Column(
                   horizontalAlignment = Alignment.Start,
                   modifier = Modifier.padding(start = 10.dp)
               ) {
                   Text(
                       text = course.name,
                       modifier = Modifier.padding(top = 5.dp),
                       fontSize = 16.sp
                   )
                   Text(
                       modifier = Modifier.padding(top = 10.dp),
                       text = "€ ${String.format("%.2f", course.price.toDouble())}",
                       color = myYellow,
                       fontSize = 18.sp,
                       fontWeight = FontWeight.Bold
                   )
               }
           }
        }
        QuantityCounter(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .background(color = myYellow, shape = RoundedCornerShape(50))
                .padding(5.dp),
            count = count,
            remove = {
                if (count == 1)
                    orderList.remove(course)
                if (count > 0){
                    updateCount(count - 1)
                    course.count = count - 1
                    subtotal.value = subtotal.value - course.price.toDouble()
                }
            },
            add = {
                if(!orderList.contains(course))
                    orderList.add(course)
                updateCount(count + 1)
                course.count = count + 1
                subtotal.value = subtotal.value + course.price.toDouble()
            }
        )
    }
}
