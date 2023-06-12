package com.petcorner.petcorner.dao

import androidx.lifecycle.LiveData
import com.petcorner.petcorner.model.Animal

class AnimalRepository(private  val dao: AnimalDao) {

    val getAllAnimals: LiveData<List<Animal>> = dao.getAllAnimal()
    val provenances: LiveData<List<String>> = dao.getProvenances()

    fun addAnimal(animal: Animal){

        dao.insertAnimal(animal)

    }


    fun setAnimals(animals: List<Animal>) {
        dao.insertAnimals(animals = animals)
    }
    fun deleteAnimal(microchip: String) {
        dao.deleteAnimalById(microchip)
    }

    fun deleteAnimalByUsername(username: String) {
        dao.deleteAnimalByOwner(username)
    }

}