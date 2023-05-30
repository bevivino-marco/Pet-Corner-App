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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

@OptIn(FlowPreview::class)
class AnimalViewModel(application: Application): AndroidViewModel(application) {
    private val animalService = AnimalService.create()

    private val dao = AnimalDb.getDatabase(application).animalDao
    private val repository: AnimalRepository= AnimalRepository(dao)

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()
    private val animalsList: LiveData<List<Animal>> = repository.getAllAnimals
    private val _animals = MutableStateFlow(animalsList)
    val animals = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .combine(_animals) { text, animals ->
            if(text.isBlank()) {
                animals
            } else {
                delay(2000L)
                Transformations.map(animals){
                    it.filter { it.doesMatchSearchQuery(text) }
                }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _animals.value
        )




//    var animals: LiveData<List<Animal>>
//    var provenances: LiveData<List<String>>
//    private val repository : AnimalRepository
    init {
//        val dao = AnimalDb.getDatabase(application).animalDao
//        repository = AnimalRepository(dao)
        viewModelScope.launch(Dispatchers.IO) {
            repository.setAnimals(animalService.getAnimals())
        }
//        animals = repository.getAllAnimals
//        provenances=repository.provenances
    }


    fun addAnimal(animal: Animal){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addAnimal(animal)
        }
    }




    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }




}