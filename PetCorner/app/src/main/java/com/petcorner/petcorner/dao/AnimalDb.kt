package com.petcorner.petcorner.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.petcorner.petcorner.model.Animal

@Database(entities = [Animal::class], version = 2, exportSchema = false)
abstract class AnimalDb: RoomDatabase() {

    abstract  val animalDao: AnimalDao

    companion object{
        @Volatile
        private  var INSTANCE: AnimalDb? = null

        fun getDatabase( context : Context): AnimalDb{
            val tempInstance =  INSTANCE
            if (tempInstance!= null){
                return  tempInstance
            }
            synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    AnimalDb::class.java,
                    "animal_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE= instance
                return instance
            }
        }
    }


}