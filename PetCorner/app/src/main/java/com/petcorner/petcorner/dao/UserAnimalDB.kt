package com.petcorner.petcorner.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.petcorner.petcorner.model.Animal

@Database(entities = [Animal::class], version = 1, exportSchema = false)
abstract class UserAnimalDB: RoomDatabase() {
    abstract  val userAnimalDao: UserAnimalDao

    companion object{
        @Volatile
        private  var INSTANCE: UserAnimalDB? = null

        fun getDatabase( context : Context): UserAnimalDB{
            val tempInstance =  INSTANCE
            if (tempInstance!= null){
                return  tempInstance
            }
            synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    UserAnimalDB::class.java,
                    "user_animal_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE= instance
                return instance
            }
        }
    }


}