package com.petcorner.petcorner.viewmodel

import android.app.Application
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.petcorner.petcorner.dao.AnimalDb
import com.petcorner.petcorner.dao.AnimalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.petcorner.petcorner.model.Animal
import com.petcorner.petcorner.service.AnimalService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class AnimalViewModel(application: Application): AndroidViewModel(application) {




    var animals: LiveData<List<Animal>>
    var provenances: LiveData<List<String>>
    private val repository : AnimalRepository
    private val animalService = AnimalService.create()
    init {
        val dao = AnimalDb.getDatabase(application).animalDao
        repository = AnimalRepository(dao)
        viewModelScope.launch(Dispatchers.IO) {
            repository.setAnimals(animalService.getAnimals())
        }
        animals = repository.getAllAnimals
        provenances=repository.provenances
    }


    fun addAnimal(animal: Animal){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addAnimal(animal)
        }
    }

    fun filterByProvenance(provenance: String){
        if (provenance== "ALL"){
            animals= repository.getAllAnimals
        }else {
            animals = Transformations.map(animals){ it ->
                it.filter {                 it.provenance.toLowerCase().equals(
                    provenance.toLowerCase(

                    )
                ) }
            }
        }
        System.out.println(animals.value.toString())
    }




}