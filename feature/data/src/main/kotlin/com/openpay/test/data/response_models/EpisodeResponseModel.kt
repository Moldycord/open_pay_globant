package com.openpay.test.data.response_models

import kotlinx.serialization.Serializable


@Serializable
data class AllEpisodesResponseModel(
    val info: InfoResponseModel,
    val results: List<EpisodeResponseModel>
)

@Serializable
data class EpisodeResponseModel(
    val id: Int,
    val name: String,
    val air_date: String
)
