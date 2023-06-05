package com.petcorner.petcorner.dao

import androidx.lifecycle.LiveData
import com.petcorner.petcorner.model.Profile

class ProfileRepository(private  val dao: ProfileDao) {
    val getAllProfiles: LiveData<Profile> = dao.getInfo()

    fun addProfile(profile: Profile){
        dao.addProfile(profile)
    }

    fun deleteUser() {
        dao.deleteUsers()
    }

    fun updateUser(profile: Profile){
        dao.updateProfile(profile)
    }

    fun updateToken(token: String, username: String){
        dao.updateToken(token, username)
    }

    fun getProfile(username: String): Profile? {
        return dao.getProfile(username)
    }
}