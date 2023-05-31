package com.petcorner.petcorner.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.petcorner.petcorner.model.Animal
@Dao
interface UserAnimalDao {

    @Insert
    fun insertUserAnimal(animal: Animal)



    @Query("SELECT * FROM animal_table")
    fun getAllUserAnimals(): LiveData<List<Animal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnimals(animals: List<Animal>)

    @Query("DELETE FROM animal_table WHERE id=:id")
    fun deleteAnimalById(id: Int)

    @Query("DELETE FROM animal_table")
    fun deleteAllAnimals()

}