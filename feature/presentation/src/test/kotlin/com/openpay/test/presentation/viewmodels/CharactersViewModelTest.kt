package com.openpay.test.presentation.viewmodels

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.openpay.test.domain.model.Character
import com.openpay.test.domain.model.PaginatedResult
import com.openpay.test.domain.providers.ResourceProvider
import com.openpay.test.domain.usecase.GetCharactersUseCase
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharactersViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val getCharactersUseCase: GetCharactersUseCase = mockk()
    private val resourceProvider = mockk<ResourceProvider>()

    private val application: Application = mockk(relaxed = true)

    private lateinit var viewModel: CharactersViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = CharactersViewModel(application, getCharactersUseCase, resourceProvider)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `loadNextPage should update uiState with characters`() = runTest {
        val characters = listOf(
            Character(1, "Rick", "Earth", "Episode 1", "url"),
            Character(2, "Morty", "Mars", "Episode 2", "url")
        )
        val result = PaginatedResult(characters, nextPage = 2, totalPages = 3)

        coEvery { getCharactersUseCase(1) } returns result

        viewModel = CharactersViewModel(application, getCharactersUseCase, resourceProvider)

        viewModel.uiState.test {
            advanceUntilIdle()
            val initial = awaitItem()
            val loading = awaitItem()
            val loaded = awaitItem()

            assertEquals(true, loading.isLoading)
            assertEquals(characters, loaded.characters)
            assertEquals(2, loaded.page)
            assertEquals(false, loaded.isLoading)
        }
    }

    @Test
    fun `loadNextPage should update uiState with error`() = runTest {
        val exceptionMessage = "Network error"
        coEvery { getCharactersUseCase(1) } throws RuntimeException(exceptionMessage)

        every { resourceProvider.getString(any()) } returns "Error desconocido"

        viewModel = CharactersViewModel(application, getCharactersUseCase, resourceProvider)

        viewModel.uiState.test {
            val initial = awaitItem()
            val loading = awaitItem()
            val error = awaitItem()

            assertEquals(true, loading.isLoading)
            assertEquals("Network error", error.errorMessage)
            assertEquals(false, error.isLoading)
        }
    }

}