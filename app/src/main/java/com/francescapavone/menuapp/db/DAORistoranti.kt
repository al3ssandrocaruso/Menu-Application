package com.francescapavone.menuapp.db
import androidx.lifecycle.LiveData
import androidx.room.*
import com.francescapavone.menuapp.db.RestaurantEntity

@Dao
interface DAORistoranti {
    @Delete
    fun removeOne(restaurantEntity: RestaurantEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOne(restaurantEntity: RestaurantEntity)

    @Query("SELECT * FROM RestaurantEntity ORDER BY id")
    fun getAll():LiveData<List<RestaurantEntity>>


}
