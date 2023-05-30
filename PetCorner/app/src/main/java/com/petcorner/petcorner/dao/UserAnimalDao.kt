package com.petcorner.petcorner.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.petcorner.petcorner.model.Animal
@Dao
interface UserAnimalDao {

    @Insert
    fun insertUserAnimal(animal: Animal)



    @Query("SELECT * FROM animal_table")
    fun getAllUserAnimals(): LiveData<List<Animal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnimals(animals: List<Animal>)

}