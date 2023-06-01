package com.petcorner.petcorner.model

import androidx.room.*
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.lang.reflect.Type

@Serializable()
@TypeConverters(Converters::class)
@Entity(tableName = "profile_table", indices =[Index(value= ["id", "username"], unique = true )])
data class Profile(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String,
    val username: String,
    val age: Int = 0,
    val password: String,
    val providerId: String,
    val cod_fisc: String,
    val piva: String,
    val country: String,
    val city: String,
    val address: String,
    val image: String = "",
    var token: String? = "",
    var roles: List<String>
    ) {

}

class Converters {
    @TypeConverter
    fun fromList(value : List<String>) = Json.encodeToString(value)

    @TypeConverter
    fun toList(value: String) = Json.decodeFromString<List<String>>(value)
}