package com.francescapavone.menuapp.ui.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.francescapavone.menuapp.R
import com.francescapavone.menuapp.api.RestaurantApi
import com.francescapavone.menuapp.model.Course
import com.francescapavone.menuapp.model.RestaurantPreview
import com.francescapavone.menuapp.ui.theme.myGreen
import com.francescapavone.menuapp.ui.theme.myYellow
import com.francescapavone.menuapp.utils.NetworkImageComponentPicasso
import com.francescapavone.menuapp.utils.ScreenRouter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONException
import org.json.JSONObject

@Composable
fun RestaurantCard(
    restaurantPreview: RestaurantPreview,
    starters: SnapshotStateList<Course>,
    firstcourses: SnapshotStateList<Course>,
    secondcourses: SnapshotStateList<Course>,
    sides: SnapshotStateList<Course>,
    fruits: SnapshotStateList<Course>,
    desserts: SnapshotStateList<Course>,
    drinks: SnapshotStateList<Course>,
    portrait: Boolean
) {
    val total = rememberSaveable { mutableStateOf(0) }
    val context = LocalContext.current
    val s = RestaurantApi(context)
    val moreInfo = rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(end = 20.dp)
            .width(180.dp),
        shape = RoundedCornerShape(14.dp),
//        backgroundColor = MaterialTheme.colors.surface,
        elevation = 5.dp
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            NetworkImageComponentPicasso(
                url = restaurantPreview.poster,
                modifier = Modifier
                    .padding(5.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .align(Alignment.CenterHorizontally),
                size = 130)

            Column(
                modifier = Modifier
                    .padding(top = 10.dp)
            ) {
                Text(
                    text = restaurantPreview.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = restaurantPreview.type,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                if(!moreInfo.value && !portrait) {
                    Text(
                        text = "More info",
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier
                            .clickable { moreInfo.value = true }
                            .padding(top = 5.dp),
                        fontSize = 14.sp,
                        color = myGreen
                    )
                }else{
                    Divider(
                        color = Color.Gray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 3.dp)
                    )
                    Text(
                        text = restaurantPreview.price,
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    Divider(
                        color = Color.Gray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 3.dp)
                    )
                    Text(
                        text = restaurantPreview.address,
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    Divider(
                        color = Color.Gray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 3.dp)
                    )
                    Text(
                        text = restaurantPreview.city,
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    Divider(
                        color = Color.Gray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 3.dp)
                    )
                    Text(
                        text = restaurantPreview.phone,
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    if (!portrait)
                    Text(
                        text = "Less info",
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier
                            .clickable { moreInfo.value = false }
                            .padding(top = 5.dp),
                        fontSize = 14.sp,
                        color = myGreen
                    )
                }

            }

            FloatingActionButton(
                onClick = {
                    //visualizza il menu
                    println("Visualizza il menu per ${restaurantPreview.id}")
                    s.getMenu(
                        restaurantPreview.id,
                        {
                            val jo = JSONObject(it)
                            try {
                                total.value = jo.getInt("totalstarters")
                                val ja = jo.getJSONArray("starters")
                                val sType = object : TypeToken<List<Course>>() {}.type

                                val gson = Gson()
                                val l = gson.fromJson<List<Course>>(ja.toString(), sType)
                                println(l)
                                starters.clear()
                                starters.addAll(l)
                            } catch (e: JSONException) {
                                Toast.makeText(context, "Not Found", Toast.LENGTH_SHORT).show()
                            }

                            //set RESTAURANT ID
                            for (i in starters) {
                                i.restaurantId = restaurantPreview.id
                            }
                        },
                        {
                            Log.v("bo", "bo") //aggiungi commenti coerenti :(
                        }
                    )
                    s.getMenu(
                        restaurantPreview.id,
                        {
                            val jo = JSONObject(it)
                            try {
                                total.value = jo.getInt("totalfirstcourses")
                                val ja = jo.getJSONArray("firstcourses")
                                val sType = object : TypeToken<List<Course>>() {}.type

                                val gson = Gson()
                                val l = gson.fromJson<List<Course>>(ja.toString(), sType)
                                println(l)
                                firstcourses.clear()
                                firstcourses.addAll(l)
                            } catch (e: JSONException) {
                                Toast.makeText(context, "Not Found", Toast.LENGTH_SHORT).show()
                            }
                            for (i in firstcourses) {
                                i.restaurantId = restaurantPreview.id
                            }
                        },
                        {
                            Log.v("bo", "bo") //aggiungi commenti coerenti :(
                        }
                    )
                    s.getMenu(
                        restaurantPreview.id,
                        {
                            val jo = JSONObject(it)
                            try {
                                total.value = jo.getInt("totalsecondcourses")
                                val ja = jo.getJSONArray("secondcourses")
                                val sType = object : TypeToken<List<Course>>() {}.type

                                val gson = Gson()
                                val l = gson.fromJson<List<Course>>(ja.toString(), sType)
                                println(l)
                                secondcourses.clear()
                                secondcourses.addAll(l)
                            } catch (e: JSONException) {
                                Toast.makeText(context, "Not Found", Toast.LENGTH_SHORT).show()
                            }
                            for (i in secondcourses) {
                                i.restaurantId = restaurantPreview.id
                            }
                        },
                        {
                            Log.v("bo", "bo") //aggiungi commenti coerenti :(
                        }
                    )
                    s.getMenu(
                        restaurantPreview.id,
                        {
                            val jo = JSONObject(it)
                            try {
                                total.value = jo.getInt("totalsides")
                                val ja = jo.getJSONArray("sides")
                                val sType = object : TypeToken<List<Course>>() {}.type

                                val gson = Gson()
                                val l = gson.fromJson<List<Course>>(ja.toString(), sType)
                                println(l)
                                sides.clear()
                                sides.addAll(l)
                            } catch (e: JSONException) {
                                Toast.makeText(context, "Not Found", Toast.LENGTH_SHORT).show()
                            }
                            for (i in sides) {
                                i.restaurantId = restaurantPreview.id
                            }
                        },
                        {
                            Log.v("bo", "bo") //aggiungi commenti coerenti :(
                        }
                    )
                    s.getMenu(
                        restaurantPreview.id,
                        {
                            val jo = JSONObject(it)
                            try {
                                total.value = jo.getInt("totalfruits")
                                val ja = jo.getJSONArray("fruits")
                                val sType = object : TypeToken<List<Course>>() {}.type

                                val gson = Gson()
                                val l = gson.fromJson<List<Course>>(ja.toString(), sType)
                                println(l)
                                fruits.clear()
                                fruits.addAll(l)
                            } catch (e: JSONException) {
                                Toast.makeText(context, "Not Found", Toast.LENGTH_SHORT).show()
                            }
                            for (i in fruits) {
                                i.restaurantId = restaurantPreview.id
                            }
                        },
                        {
                            Log.v("bo", "bo") //aggiungi commenti coerenti :(
                        }
                    )
                    s.getMenu(
                        restaurantPreview.id,
                        {
                            val jo = JSONObject(it)
                            try {
                                total.value = jo.getInt("totaldesserts")
                                val ja = jo.getJSONArray("desserts")
                                val sType = object : TypeToken<List<Course>>() {}.type

                                val gson = Gson()
                                val l = gson.fromJson<List<Course>>(ja.toString(), sType)
                                println(l)
                                desserts.clear()
                                desserts.addAll(l)
                            } catch (e: JSONException) {
                                Toast.makeText(context, "Not Found", Toast.LENGTH_SHORT).show()
                            }
                            for (i in desserts) {
                                i.restaurantId = restaurantPreview.id
                            }
                        },
                        {
                            Log.v("bo", "bo") //aggiungi commenti coerenti :(
                        }
                    )
                    s.getMenu(
                        restaurantPreview.id,
                        {
                            val jo = JSONObject(it)
                            try {
                                total.value = jo.getInt("totaldrinks")
                                val ja = jo.getJSONArray("drinks")
                                val sType = object : TypeToken<List<Course>>() {}.type

                                val gson = Gson()
                                val l = gson.fromJson<List<Course>>(ja.toString(), sType)
                                println(l)
                                drinks.clear()
                                drinks.addAll(l)
                            } catch (e: JSONException) {
                                Toast.makeText(context, "Not Found", Toast.LENGTH_SHORT).show()
                            }
                            for (i in drinks) {
                                i.restaurantId = restaurantPreview.id
                            }
                        },
                        {
                            Log.v("bo", "bo") //aggiungi commenti coerenti :(
                        }
                    )
                    ScreenRouter.navigateTo(2)
                },
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.End),
                backgroundColor = myYellow,
                contentColor = myGreen,
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(3.dp),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.eye2),
                    tint = myGreen,
                    contentDescription = "eye",
                    modifier = Modifier.padding(6.dp)
                )
            }

        }
    }
}
