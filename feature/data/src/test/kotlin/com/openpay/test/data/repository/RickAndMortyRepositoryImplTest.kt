package com.openpay.test.data.repository

import com.openpay.test.data.mappers.toDomain
import com.openpay.test.data.response_models.AllCharactersResponseModel
import com.openpay.test.data.response_models.AllEpisodesResponseModel
import com.openpay.test.data.response_models.CharacterResponseModel
import com.openpay.test.data.response_models.EpisodeResponseModel
import com.openpay.test.data.response_models.InfoResponseModel
import com.openpay.test.data.response_models.LocationCharacterResponseModel
import com.openpay.test.data.service.RickAndMortyService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals

class RickAndMortyRepositoryImplTest {

    private lateinit var repository: RickAndMortyRepositoryImpl
    private val service: RickAndMortyService = mockk()

    @Before
    fun setUp() {
        repository = RickAndMortyRepositoryImpl(service)
    }

    @Test
    fun `getCharacters returns mapped result`() = runBlocking {
        val response = AllCharactersResponseModel(
            info = InfoResponseModel(2, 5, "https://api?page=2", null),
            results = listOf(
                CharacterResponseModel(
                    1,
                    "Rick",
                    "url",
                    listOf("Ep1"),
                    LocationCharacterResponseModel("Earth", "www.url.com")
                )
            )
        )
        val expected = response.toDomain()

        coEvery { service.getCharacters(1) } returns response

        val result = repository.getCharacters(1)

        assertEquals(expected, result)
    }

    @Test
    fun `getEpisodes returns mapped result`() = runBlocking {
        val response = AllEpisodesResponseModel(
            info = InfoResponseModel(2, 5, "https://api?page=2", null),
            results = listOf(
                EpisodeResponseModel(1, "Pilot", "December 2, 2013")
            )
        )
        val expected = response.toDomain()

        coEvery { service.getEpisodes(1) } returns response

        val result = repository.getEpisodes(1)

        assertEquals(expected, result)
    }

}