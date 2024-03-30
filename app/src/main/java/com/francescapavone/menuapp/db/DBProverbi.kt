package com.francescapavone.menuapp.db
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RestaurantEntity::class],version = 1)
abstract class DBProverbi: RoomDatabase() {
    companion object{
        private var db: DBProverbi? = null
        fun getInstance(context : Context): DBProverbi {
            val dbName:DBName = DBName()
            if(db == null)
                db = Room.databaseBuilder(context.applicationContext,
                    DBProverbi::class.java,dbName.DBNAME)
                    .createFromAsset(dbName.DBNAME).build()
            return db as DBProverbi
        }

    }
    abstract fun proverbiDAO():DAORistoranti
}