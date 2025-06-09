package com.openpay.test.data.mappers

import com.openpay.test.data.response_models.AllEpisodesResponseModel
import com.openpay.test.data.response_models.EpisodeResponseModel
import com.openpay.test.data.response_models.InfoResponseModel
import com.openpay.test.domain.model.Episode
import com.openpay.test.domain.model.PaginatedResult
import org.junit.Assert.assertEquals
import org.junit.Test

class EpisodesResponseMapperTest {

    @Test
    fun `toDomain maps AllEpisodesResponseModel to PaginatedResult correctly`() {
        val info = InfoResponseModel(
            count = 2,
            pages = 1,
            next = "https://rickandmortyapi.com/api/episode?page=2",
            prev = null
        )

        val results = listOf(
            EpisodeResponseModel(id = 1, name = "Pilot", air_date = "December 2, 2013"),
            EpisodeResponseModel(id = 2, name = "Lawnmower Dog", air_date = "December 9, 2013")
        )

        val response = AllEpisodesResponseModel(info = info, results = results)


        val domainResult = response.toDomain()

        val expected = PaginatedResult(
            items = listOf(
                Episode(id = 1, name = "Pilot", airDate = "December 2, 2013"),
                Episode(id = 2, name = "Lawnmower Dog", airDate = "December 9, 2013")
            ),
            nextPage = 2,
            totalPages = 1
        )

        assertEquals(expected, domainResult)
    }
}