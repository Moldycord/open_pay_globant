package com.openpay.test.data.response_models

import kotlinx.serialization.Serializable

@Serializable
data class AllCharactersResponseModel(
    val info: InfoResponseModel,
    val results: List<CharacterResponseModel>
)

@Serializable
data class InfoResponseModel(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)

@Serializable
data class CharacterResponseModel(
    val id: Int,
    val name: String,
    val image: String,
    val episode: List<String>,
    val location: LocationCharacterResponseModel
)

@Serializable
data class LocationCharacterResponseModel(
    val name: String,
    val url: String
)