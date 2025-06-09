package com.openpay.test.data.service

import com.openpay.test.data.response_models.AllCharactersResponseModel
import com.openpay.test.data.response_models.AllEpisodesResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyService {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int? = null
    ): AllCharactersResponseModel


    @GET("episode")
    suspend fun getEpisodes(
        @Query("page") page: Int? = null
    ): AllEpisodesResponseModel

}