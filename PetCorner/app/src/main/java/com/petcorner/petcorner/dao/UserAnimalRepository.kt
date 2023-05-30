package com.petcorner.petcorner.dao

import androidx.lifecycle.LiveData
import com.petcorner.petcorner.model.Animal

class UserAnimalRepository(private  val dao: UserAnimalDao ) {

    val getAllAnimals: LiveData<List<Animal>> = dao.getAllUserAnimals()

    fun addAnimal(animal: Animal){

        dao.insertUserAnimal(animal)

    }
    fun setAnimals(animals: List<Animal>) {
        dao.insertAnimals(animals = animals)
    }




}