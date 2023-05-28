package com.petcorner.petcorner.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.petcorner.petcorner.model.Profile


@Database(entities = [Profile::class], version = 2, exportSchema = false)
abstract class ProfileDB: RoomDatabase() {
    abstract  val profileDao: ProfileDao

    companion object{
        @Volatile
        private  var INSTANCE: ProfileDB? = null

        fun getDatabase( context : Context): ProfileDB{
            val tempInstance =  INSTANCE
            if (tempInstance!= null){
                return  tempInstance
            }
            synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    ProfileDB::class.java,
                    "profile_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE= instance
                return instance
            }
        }
    }

}