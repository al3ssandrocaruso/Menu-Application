package com.francescapavone.menuapp.viewmodel
import android.app.Application
import androidx.lifecycle.LiveData
import com.francescapavone.menuapp.db.DBProverbi
import com.francescapavone.menuapp.db.RestaurantEntity
import com.francescapavone.menuapp.db.RepositoryRestaurants

class MainViewModel(application: Application) {
    var allfavourite: LiveData<List<RestaurantEntity>>
    private val rep: RepositoryRestaurants

    init{
        val db = DBProverbi.getInstance(application)
        val dao = db.proverbiDAO()
        rep = RepositoryRestaurants(dao)

        allfavourite = rep.allfavourite
    }

    fun updateFavoriteOn(restaurantEntity: RestaurantEntity){
        rep.changeOnFavorite(restaurantEntity)
        println("Added ${restaurantEntity.id} to favourite")
    }

    fun updateFavoriteOff(restaurantEntity: RestaurantEntity){
        rep.changeOffFavorite(restaurantEntity)
        println("Removed ${restaurantEntity.id} from favourite")
    }

}