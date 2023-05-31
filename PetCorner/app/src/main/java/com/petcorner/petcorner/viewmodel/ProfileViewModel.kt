package com.petcorner.petcorner.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.petcorner.petcorner.dao.ProfileDB
import com.petcorner.petcorner.dao.ProfileRepository
import com.petcorner.petcorner.dao.UserAnimalDB
import com.petcorner.petcorner.dao.UserAnimalRepository
import com.petcorner.petcorner.model.Animal
import com.petcorner.petcorner.model.Profile
import com.petcorner.petcorner.service.AnimalService
import com.petcorner.petcorner.service.RegistrationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application): AndroidViewModel(application) {
    private val animalService = AnimalService.create()
    private val registrationService = RegistrationService.create()

    private val dao = ProfileDB.getDatabase(application).profileDao
    private val repository: ProfileRepository = ProfileRepository(dao)

    private val userAnimalDao = UserAnimalDB.getDatabase(application).userAnimalDao
    private val userAnimalRepository: UserAnimalRepository = UserAnimalRepository(userAnimalDao)

    private val animalsList: LiveData<List<Animal>> = userAnimalRepository.getAllAnimals
    private val _animals = MutableStateFlow(animalsList)
    val animals = _animals.asStateFlow().stateIn(viewModelScope,
        SharingStarted.WhileSubscribed(500),_animals.value)

    private val userInfoList: LiveData<List<Profile>> = repository.getAllProfiles
    private val _userInfo = MutableStateFlow(userInfoList)
    val userInfo = _userInfo.asStateFlow().stateIn(viewModelScope,
        SharingStarted.WhileSubscribed(500),_userInfo.value)




    suspend fun addUser(profile: Profile, path: String?){

        viewModelScope.launch(Dispatchers.IO) {
            registrationService.addUser(profile, path)
            dao.addProfile(profile)
        }
    }


    fun getAnimalsProfile(email:String) {
        viewModelScope.launch(Dispatchers.IO) {
            userAnimalRepository.setAnimals(animalService.getAnimalsForUser(email = email))
        }
    }

    fun addNewAnimal(animal: Animal){
        viewModelScope.launch(Dispatchers.IO) {
//            userInfo.value.value?.get(0)?.token?.let {
//                animalService.AddAnimal(animal, it) }

                animalService.AddAnimal(animal, "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJUaGVCcm9ja0BtYWlsLmNvbSIsInJvbGVzIjpbXSwiaXNzIjoiaHR0cDovL2hvc3QuZG9ja2VyLmludGVybmFsOjkwMDAvcHJvZmlsZS92Mi9sb2dpbiIsImV4cCI6MTY4NTQ4MTA5N30.DL4C0DTiR2Gi8ivxFB9MdBlYh_Ct5u3hZ_-ZNFg7U_g")
            userAnimalRepository.addAnimal(animal)
    }


    }

    fun deleteAnimalForUser(animal: Animal) {
        viewModelScope.launch(Dispatchers.IO) {

            animalService.deleteAnimal(animal.microchip
                , "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJUaGVCcm9ja0BtYWlsLmNvbSIsInJvbGVzIjpbXSwiaXNzIjoiaHR0cDovLzZjOGZmOTc0YmNiMDo5MDAwL3Byb2ZpbGUvdjIvbG9naW4iLCJleHAiOjE2ODU1NjAxMjB9.ac4C6kvGuQyRomhd6LY0F0ysQ83EqQtFsMc4kpNUC6M")
            userAnimalRepository.deleteAnimal(animal.id)
        }


    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            userAnimalRepository.deleteAnimalsForUser()
            repository.deleteUser()

        }

    }


}