package com.openpay.test.domain.usecase

import com.openpay.test.domain.model.Character
import com.openpay.test.domain.model.PaginatedResult
import com.openpay.test.domain.repository.RickAndMortyRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetCharactersUseCaseTest {

    private val repository: RickAndMortyRepository = mockk()
    private lateinit var useCase: GetCharactersUseCase

    @Before
    fun setup() {
        useCase = GetCharactersUseCase(repository)
    }

    @Test
    fun `invoke should return characters from repository`() = runTest {

        val page = 1
        val characters = listOf(Character(1, "IMG", "Rick", "Earth", "Episode1"))
        val expected = PaginatedResult(characters, nextPage = 2, totalPages = 3)

        coEvery { repository.getCharacters(page) } returns expected


        val result = useCase(page)


        assertEquals(expected, result)
        coVerify(exactly = 1) { repository.getCharacters(page) }
    }
}