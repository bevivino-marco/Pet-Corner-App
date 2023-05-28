package com.petcorner.petcorner.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.petcorner.petcorner.dao.ProfileDB
import com.petcorner.petcorner.dao.ProfileRepository
import com.petcorner.petcorner.model.Profile
import com.petcorner.petcorner.service.RegistrationService

class ProfileViewModel(application: Application): AndroidViewModel(application) {
    private val registrationService = RegistrationService.create()

    private val dao = ProfileDB.getDatabase(application).profileDao
    private val repository: ProfileRepository = ProfileRepository(dao)


    suspend fun addUser(profile: Profile, path: String?){
        registrationService.addUser(profile, path)
        dao.addProfile(profile)
    }


}