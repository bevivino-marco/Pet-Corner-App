package com.petcorner.petcorner.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.petcorner.petcorner.model.Sitter
@Dao
interface SitterDao {

    @Insert
    fun insertSitter(sitter: Sitter)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSitters(sitters: List<Sitter>)



    @Query("SELECT * FROM sitter_table")
    fun getAllSitter(): LiveData<List<Sitter>>

    @Query("SELECT DISTINCT locality FROM sitter_table")
    fun getCity(): LiveData<List<String>>
    @Query("DELETE FROM sitter_table WHERE email=:username")
    fun deleteSitterByUsername(username:String)

}