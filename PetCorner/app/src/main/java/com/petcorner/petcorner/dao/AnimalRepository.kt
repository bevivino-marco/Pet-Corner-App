package com.petcorner.petcorner.dao

import androidx.lifecycle.LiveData
import com.petcorner.petcorner.model.Animal

class AnimalRepository(private  val dao: AnimalDao) {

    val getAllAnimals: LiveData<List<Animal>> = dao.getAllAnimal()

    fun addAnimal(animal: Animal){

        dao.insertAnimal(animal)

    }



}