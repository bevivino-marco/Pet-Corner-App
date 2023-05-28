package com.petcorner.petcorner.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import org.jetbrains.annotations.Nullable

@Serializable
@Entity(tableName = "profile_table", indices =[Index(value= ["id", "username"], unique = true )])
data class Profile(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String,
    val username: String ,
    val age: Int ,
    val password: String ,
    val providerId: String ,
    val cod_fisc: String ,
    val piva: String ,
    val country: String ,
    val city: String ,
    val address: String ,
    val image: String,
    val token: String?,

) {

}