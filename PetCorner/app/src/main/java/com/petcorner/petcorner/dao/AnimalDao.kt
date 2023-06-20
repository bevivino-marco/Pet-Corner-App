package com.petcorner.petcorner.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.petcorner.petcorner.model.Animal

@Dao
interface AnimalDao {
    @Insert
    fun insertAnimal(animal: Animal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnimals(animals: List<Animal>)

    @Query("SELECT * FROM animal_table")
    fun getAllAnimal(): LiveData<List<Animal>>

    @Query("SELECT DISTINCT provenance FROM animal_table")
    fun getProvenances(): LiveData<List<String>>


    @Query("DELETE FROM animal_table WHERE microchip=:microchip")
    fun deleteAnimalById(microchip: String)

    @Query("DELETE FROM animal_table WHERE owner=:owner")
    fun deleteAnimalByOwner(owner: String)

    @Query("SELECT * FROM animal_table WHERE owner=:username")
    fun getAnimalsByOwner(username: String): List<Animal>


}