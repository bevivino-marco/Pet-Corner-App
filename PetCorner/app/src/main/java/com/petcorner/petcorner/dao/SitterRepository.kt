package com.petcorner.petcorner.dao

import androidx.lifecycle.LiveData
import com.petcorner.petcorner.model.Sitter

class SitterRepository(private  val dao: SitterDao) {
    val getAllSitters: LiveData<List<Sitter>> = dao.getAllSitter()
    val provenances: LiveData<List<String>> = dao.getCity()

    fun addSitter(sitter: Sitter){

        dao.insertSitter(sitter)

    }


    fun setSitters(sitters: List<Sitter>) {
        dao.insertSitters(sitters = sitters)
    }

}