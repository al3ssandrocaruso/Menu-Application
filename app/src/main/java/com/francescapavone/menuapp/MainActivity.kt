package com.francescapavone.menuapp

import android.app.DownloadManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import com.francescapavone.menuapp.model.Course
import com.francescapavone.menuapp.model.RestaurantPreview
import com.francescapavone.menuapp.ui.layout.Cart
import com.francescapavone.menuapp.ui.layout.HomePage
import com.francescapavone.menuapp.ui.layout.Menu
import com.francescapavone.menuapp.ui.layout.QrCodeScanner
import com.francescapavone.menuapp.ui.theme.MenuAppTheme
import com.francescapavone.menuapp.utils.ScreenRouter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MenuAppTheme {
//                val restaurant = rememberSaveable {mutableStateOf(Restaurant(0, " ", " ", " ", " "," ", " ", 0, false)) }
                val subtotal = rememberSaveable { mutableStateOf(0.0)}

                val orderList = rememberSaveable { mutableListOf<Course>() }
//                val orderList = rememberSaveable { mutableListOf<Dish>() }

                val previewslist = remember { mutableStateListOf<RestaurantPreview>() }

                val dm = getSystemService(DOWNLOAD_SERVICE) as DownloadManager

                val starters = remember { mutableStateListOf<Course>() }
                val firstcourses = remember { mutableStateListOf<Course>() }
                val secondcourses = remember { mutableStateListOf<Course>() }
                val sides = remember { mutableStateListOf<Course>() }
                val fruits = remember { mutableStateListOf<Course>() }
                val desserts = remember { mutableStateListOf<Course>() }
                val drinks = remember { mutableStateListOf<Course>() }

                val restaurantId = rememberSaveable { mutableStateOf(-1) }

                when(ScreenRouter.currentScreen.value) {
                    1 -> HomePage(previewslist, starters, firstcourses, secondcourses, sides, fruits, desserts, drinks)
                    2 -> Menu(restaurantId, starters, firstcourses, secondcourses, sides, fruits, desserts, drinks, subtotal, orderList)
                    3 -> Cart(subtotal, orderList)
                    4 -> QrCodeScanner(dm)

                }

            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MenuAppTheme {
        Greeting("Android")
    }
}