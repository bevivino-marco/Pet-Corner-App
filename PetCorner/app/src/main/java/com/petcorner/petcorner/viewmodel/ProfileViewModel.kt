package com.petcorner.petcorner.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.petcorner.petcorner.dao.*
import com.petcorner.petcorner.model.Animal
import com.petcorner.petcorner.model.Profile
import com.petcorner.petcorner.model.Sitter
import com.petcorner.petcorner.service.AnimalService
import com.petcorner.petcorner.service.RegistrationService
import com.petcorner.petcorner.service.SitterService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application): AndroidViewModel(application) {
    private val animalService = AnimalService.create()
    private val registrationService = RegistrationService.create()
    private val sitterService = SitterService.create()

    private val dao = ProfileDB.getDatabase(application).profileDao
    private val repository: ProfileRepository = ProfileRepository(dao)

    private val userAnimalDao = UserAnimalDB.getDatabase(application).userAnimalDao
    private val userAnimalRepository: UserAnimalRepository = UserAnimalRepository(userAnimalDao)

    private val animalDao = AnimalDb.getDatabase(application).animalDao
    private val animalRepository: AnimalRepository = AnimalRepository(animalDao)

    private val sitterDao = SitterDB.getDatabase(application).sitterDao
    private val sitterRepository: SitterRepository = SitterRepository(sitterDao)

    private val animalsList: LiveData<List<Animal>> = userAnimalRepository.getAllAnimals
    private val _animals = MutableStateFlow(animalsList)
    val animals = _animals.asStateFlow().stateIn(viewModelScope,
        SharingStarted.WhileSubscribed(500),_animals.value)

    private val userInfoDao: LiveData<Profile> = repository.getAllProfiles
    private val _userInfo = MutableStateFlow(userInfoDao)
    val userInfo = _userInfo.asStateFlow().stateIn(viewModelScope,
        SharingStarted.WhileSubscribed(500),_userInfo.value)

    suspend fun addUser(profile: Profile, path: String?){
        viewModelScope.launch(Dispatchers.IO) {
            val roles = profile.roles
            profile.roles = emptyList()
            registrationService.addUser(profile, path)
            repository.addProfile(profile)
            if(roles != emptyList<String>()){
                if(roles.contains("Sitter")){
                    val token = registrationService.loginUser(profile.username, profile.password)
                    if(token != null){
                        val sitter : Sitter = Sitter (
                            profile.id!!,
                            profile.name,
                            profile.name,
                            profile.age,
                            profile.city,
                            "Description",
                            "Dog",
                            2,
                            "Sitting",
                            profile.username,
                            profile.image
                        )
                        sitterService.addSitter(sitter, token)
                        sitterRepository.addSitter(sitter)
                    }
                }
                if(profile.roles.contains("Trainer")){
                    // TODO:
                }
            }
        }
    }

    fun getAnimalsProfile(email:String) {
        viewModelScope.launch(Dispatchers.IO) {
            userAnimalRepository.setAnimals(animalService.getAnimalsForUser(email = email))
        }
    }

    fun addNewAnimal(token: String?, animal: Animal){
        viewModelScope.launch(Dispatchers.IO) {

            if (token != null) {

                animalService.AddAnimal(animal, token)
                userAnimalRepository.addAnimal(animal)

            }
        }
    }

    fun deleteAnimalForUser(animal: Animal, token: String?) {
        viewModelScope.launch(Dispatchers.IO) {

            if (token != null) {
                animalService.deleteAnimal(animal.microchip, token)
                userAnimalRepository.deleteAnimal(animal.id)
                animalRepository.deleteAnimal(animal.microchip)

            }
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            userAnimalRepository.deleteAnimalsForUser()
            repository.deleteUser()
        }
    }

    suspend fun login(username: String, psw: String) : Boolean{
        var result = viewModelScope.async(Dispatchers.IO) {
            var tmp = false
            val token = registrationService.loginUser(username, psw)
            if(token != null){
                // get info user
                val profile = registrationService.getInfo(username, token)
                if (profile != null) {
                    profile.token = token
                    // check if exist
                    if(repository.getProfile(username) != null) {
                        //repository.updateUser(profile)
                        repository.updateToken(profile.token!!, profile.username)
                    } else {
                        repository.addProfile(profile)
                    }
                    tmp = true
                }
            }
            return@async tmp
        }
        return result.await()
    }

    fun checkTokenExists() : Boolean {
        val token = repository.getAllProfiles.value?.token
        return token != "" && token != null
    }

}