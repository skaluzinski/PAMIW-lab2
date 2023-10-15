package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class City(override val id: String = "",@SerialName("LocalizedName") override val englishName: String, @SerialName("AdministrativeArea") val administrativeArea: AdministrativeArea ) : Resource
