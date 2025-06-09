package com.openpay.test.data.response_models


data class AllEpisodesResponseModel(
    val info: InfoResponseModel,
    val results: List<EpisodeResponseModel>
)


data class EpisodeResponseModel(
    val id: Int,
    val name: String,
    val air_date: String
)
