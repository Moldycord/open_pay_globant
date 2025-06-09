package com.openpay.test.data.mappers

import com.openpay.test.data.response_models.AllCharactersResponseModel
import com.openpay.test.data.response_models.CharacterResponseModel
import com.openpay.test.data.response_models.InfoResponseModel
import com.openpay.test.data.response_models.LocationCharacterResponseModel
import org.junit.Test
import org.junit.Assert.assertEquals

class CharacterResponseMapperTest {

    @Test
    fun `toDomain maps correctly from AllCharactersResponseModel`() {
        // Arrange
        val characterResponse = CharacterResponseModel(
            id = 1,
            name = "Rick Sanchez",
            image = "https://image.url/rick.png",
            location = LocationCharacterResponseModel(name = "Earth", url = ""),
            episode = listOf("S01E01", "S01E02")
        )

        val apiResponse = AllCharactersResponseModel(
            info = InfoResponseModel(
                count = 10,
                pages = 5,
                next = "https://api.com/?page=2",
                prev = "https://api.com/?page=1"
            ),
            results = listOf(characterResponse)
        )

        // Act
        val domainResult = apiResponse.toDomain()

        // Assert
        assertEquals(1, domainResult.items.size)
        assertEquals("Rick Sanchez", domainResult.items[0].name)
        assertEquals("Earth", domainResult.items[0].location)
        assertEquals("S01E01", domainResult.items[0].firstEpisode)
        assertEquals("https://image.url/rick.png", domainResult.items[0].image)
        assertEquals(2, domainResult.nextPage)
        assertEquals(5, domainResult.totalPages)
    }
}