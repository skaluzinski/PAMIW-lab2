package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.Serial

@Serializable
data class Locations(val locations: List<Location>)

@Serializable
data class Location(
    @SerialName("ID") override val id: String,
    @SerialName("LocalizedName") val localizedName: String,
    @SerialName("EnglishName") override val englishName: String
) : Resource
