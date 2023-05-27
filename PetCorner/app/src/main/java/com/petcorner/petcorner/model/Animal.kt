package com.petcorner.petcorner.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "animal_table")
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
}
