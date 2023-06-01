package com.petcorner.petcorner.dao

import androidx.lifecycle.LiveData
import com.petcorner.petcorner.model.Profile

class ProfileRepository(private  val dao: ProfileDao) {
    val getAllProfiles: LiveData<List<Profile>> = dao.getInfo()

    fun addProfile(profile: Profile){
        dao.addProfile(profile)
    }

    fun deleteUser() {
        dao.deleteUsers()
    }

    fun updateUser(profile: Profile){
        dao.updateProfile(profile)
    }

    fun getProfile(username: String): Profile? {
        return dao.getProfile(username)
    }
}