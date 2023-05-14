package com.petcorner.petcorner.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.petcorner.petcorner.model.Animal

@Dao
interface AnimalDao {


    @Insert
    fun insertAnimal(animal: Animal)

    @Query("SELECT * FROM animal_table")
    fun getAllAnimal(): LiveData<List<Animal>>

}