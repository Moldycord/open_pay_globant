package com.openpay.test.data.response_models

data class AllCharactersResponseModel(
    val info: InfoResponseModel,
    val results: List<CharacterResponseModel>
)

data class InfoResponseModel(
    val count: Int,
    val pages: Int,
    val next: String,
    val prev: String?
)

data class CharacterResponseModel(
    val id: Int,
    val name: String,
    val image: String,
    val episode: List<String>,
    val location: LocationCharacterResponseModel
)

data class LocationCharacterResponseModel(
    val name: String,
    val url: String
)