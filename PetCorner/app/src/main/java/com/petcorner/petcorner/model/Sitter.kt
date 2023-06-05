package com.petcorner.petcorner.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "sitter_table", indices =[Index(value= ["id", "email"], unique = true )])
data class Sitter(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,    
    val surname: String ,
    val age: Int ,
    val locality: String ,
    val personalDescription: String ,
    val animalsAllowed: String ,
    val sizeAllowed: Int ,
    val serviceOffered: String ,
    val email: String ,
    val image: String
) {
    override fun toString(): String{
        return name
    }
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "$name",
            "${name.first()}",
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}