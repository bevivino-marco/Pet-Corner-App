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


}