package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AdministrativeArea(
    @SerialName("ID") override val id: String,
    @SerialName("EnglishName") override val englishName: String)
    : Resource
