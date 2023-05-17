package com.petcorner.petcorner.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.petcorner.petcorner.dao.AnimalDb
import com.petcorner.petcorner.dao.AnimalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.petcorner.petcorner.model.Animal


class AnimalViewModel(application: Application): AndroidViewModel(application) {

    var animals: LiveData<List<Animal>>
    private val repository : AnimalRepository


    init {
        val dao = AnimalDb.getDatabase(application).animalDao
        repository = AnimalRepository(dao)
        animals = repository.getAllAnimals
    }


    fun addAnimal(animal: Animal){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addAnimal(animal)
        }
    }

    fun getAllAnimals(): LiveData<List<Animal>>{
        return repository.getAllAnimals
    }




}