package com.petcorner.petcorner.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.petcorner.petcorner.model.Profile

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile_table LIMIT 1")
    fun getInfo(): LiveData<Profile>

    @Transaction
    @Insert
    fun addProfile(profile:Profile)

    @Query("DELETE FROM profile_table")
    fun deleteUsers()

    @Transaction
    @Update
    fun updateProfile(profile:Profile)

    @Query("SELECT * FROM profile_table WHERE username = :username")
    fun getProfile(username: String) : Profile?
}