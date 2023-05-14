package com.petcorner.petcorner.viewmodel

data class AnimalPost(
      val animalId: Long,
      val animalName: String,
      val animalAge: Int,
      val image: String,
      val owner: Long,
      val location: String,
      val therapy: Boolean,
      val adopt: Boolean
      )



