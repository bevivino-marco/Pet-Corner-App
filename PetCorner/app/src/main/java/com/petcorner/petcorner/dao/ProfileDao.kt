package com.petcorner.petcorner.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.petcorner.petcorner.model.Profile

@Dao
interface ProfileDao {




    @Query("SELECT * FROM profile_table")
    fun getInfo(): LiveData<List<Profile>>

    @Transaction
    @Insert
    fun addProfile(profile:Profile)




}