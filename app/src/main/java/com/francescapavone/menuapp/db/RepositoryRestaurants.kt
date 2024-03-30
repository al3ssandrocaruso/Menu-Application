package com.francescapavone.menuapp.db
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepositoryRestaurants (private val daoRistoranti:DAORistoranti){
    var allfavourite: LiveData<List<RestaurantEntity>> = daoRistoranti.getAll()

    fun changeOnFavorite(restaurantEntity: RestaurantEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            daoRistoranti.insertOne(restaurantEntity)
        }
    }
    fun changeOffFavorite(restaurantEntity: RestaurantEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            daoRistoranti.removeOne(restaurantEntity)
        }
    }
}