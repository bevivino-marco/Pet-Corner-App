package com.petcorner.petcorner.model

import androidx.compose.runtime.MutableState
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "animal_table",  indices =[Index(value= ["id", "microchip"], unique = true )])
data class Animal(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val age: Int,
    val size: Int,
    val type: String,
    val description: String,
    val owner: String,
    val provenance: String,
    val microchip: String,
    val sex: String,
    val image: String

){
    override fun toString(): String{
        return name
    }


    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "$provenance",
            "${provenance.first()}",
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }

}
