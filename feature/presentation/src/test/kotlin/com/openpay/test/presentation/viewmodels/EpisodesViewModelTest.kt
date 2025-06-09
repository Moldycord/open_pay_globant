package com.openpay.test.presentation.viewmodels

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.openpay.test.domain.model.Episode
import com.openpay.test.domain.model.PaginatedResult
import com.openpay.test.domain.providers.ResourceProvider
import com.openpay.test.domain.usecase.GetEpisodesUseCase
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Assert.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class EpisodesViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val getEpisodesUseCase = mockk<GetEpisodesUseCase>()
    private val resourceProvider = mockk<ResourceProvider>()

    private val application: Application = mockk(relaxed = true)

    private lateinit var viewModel: EpisodesViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = EpisodesViewModel(application, getEpisodesUseCase, resourceProvider)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `loadNextPage emits loading then loaded episodes`() = runTest {
        val episodes = listOf(
            Episode(id = 1, name = "Pilot", airDate = "December 2, 2013"),
            Episode(id = 2, name = "Lawnmower Dog", airDate = "December 9, 2013")
        )
        val paginatedResult = PaginatedResult(episodes, nextPage = 2, totalPages = 5)

        coEvery { getEpisodesUseCase(1) } returns paginatedResult

        viewModel.uiState.test {
            val firstState = awaitItem() // initial state

            viewModel.loadNextPage()

            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)

            val loadedState = awaitItem()
            assertFalse(loadedState.isLoading)
            assertEquals(episodes, loadedState.episodes)
            assertEquals(2, loadedState.page)
            assertTrue(loadedState.hasNextPage)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadNextPage handles error and sets errorMessage`() = runTest {
        val exception = Exception("Network error")
        coEvery { getEpisodesUseCase(any()) } throws exception

        viewModel.uiState.test {
            val firstState = awaitItem()

            viewModel.loadNextPage()

            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)

            val errorState = awaitItem()
            assertFalse(errorState.isLoading)
            assertEquals("Network error", errorState.errorMessage)

            cancelAndIgnoreRemainingEvents()
        }
    }

}