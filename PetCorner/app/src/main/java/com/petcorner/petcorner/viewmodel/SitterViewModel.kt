package com.petcorner.petcorner.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.petcorner.petcorner.dao.SitterDB
import com.petcorner.petcorner.dao.SitterRepository
import com.petcorner.petcorner.model.Sitter
import com.petcorner.petcorner.service.SitterService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SitterViewModel(application: Application): AndroidViewModel(application) {
    private val sitterService = SitterService.create()

    private val dao = SitterDB.getDatabase(application).sitterDao
    private val repository: SitterRepository = SitterRepository(dao)

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()
    private val sittersList: LiveData<List<Sitter>> = repository.getAllSitters
    private val _sitters = MutableStateFlow(sittersList)
    val sitters = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .combine(_sitters) { text, sitters ->
            if(text.isBlank()) {
                sitters
            } else {
                delay(2000L)
                Transformations.map(sitters){
                    it.filter { it.doesMatchSearchQuery(text) }
                }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _sitters.value
        )



    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.setSitters(sitterService.getSitters())
        }
    }


    fun addSitter(sitter: Sitter){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSitter(sitter)
        }
    }


    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }





}