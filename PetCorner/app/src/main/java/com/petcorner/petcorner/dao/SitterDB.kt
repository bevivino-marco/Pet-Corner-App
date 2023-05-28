package com.petcorner.petcorner.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.petcorner.petcorner.model.Sitter

@Database(entities = [Sitter::class], version = 1, exportSchema = false)
abstract class SitterDB: RoomDatabase() {

    abstract  val sitterDao: SitterDao

    companion object{
        @Volatile
        private  var INSTANCE: SitterDB? = null

        fun getDatabase( context : Context): SitterDB{
            val tempInstance =  INSTANCE
            if (tempInstance!= null){
                return  tempInstance
            }
            synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    SitterDB::class.java,
                    "sitter_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE= instance
                return instance
            }
        }
    }
}