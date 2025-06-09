package com.openpay.test.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openpay.test.domain.model.Episode
import com.openpay.test.domain.usecase.GetEpisodesUseCase
import com.openpay.test.feature.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    application: Application,
    private val getEpisodesUseCase: GetEpisodesUseCase
) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(EpisodesUiState())
    val uiState: StateFlow<EpisodesUiState> = _uiState

    init {
        loadNextPage()
    }

    fun onSearch(query: String) {
        _uiState.update {
            it.copy(
                searchQuery = query,
                episodes = emptyList(),
                page = 1,
                hasNextPage = true
            )
        }
        loadNextPage(reset = true)
    }

    fun loadNextPage(reset: Boolean = false) {
        val currentState = _uiState.value
        if (!currentState.hasNextPage || currentState.isLoading) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val result = getEpisodesUseCase(currentState.page)
                val filtered = if (currentState.searchQuery.isNotBlank()) {
                    result.items.filter {
                        it.name.contains(currentState.searchQuery, ignoreCase = true)
                    }
                } else result.items

                _uiState.update { uiState ->
                    val currentEpisodes = if (reset) emptyList() else uiState.episodes
                    val newEpisodes = filtered.filterNot { new ->
                        currentEpisodes.any { existing -> existing.id == new.id }
                    }
                    uiState.copy(
                        episodes = currentEpisodes + newEpisodes,
                        page = (uiState.page + 1),
                        hasNextPage = result.nextPage != null,
                        isLoading = false
                    )
                }
            } catch (error: Exception) {
                _uiState.update {
                    val errorMessage =
                        error.localizedMessage ?: getApplication<Application>()
                            .getString(
                                R.string.error_unknown
                            )
                    it.copy(
                        isLoading = false,
                        errorMessage = errorMessage
                    )
                }

            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = "") }
    }
}

data class EpisodesUiState(
    val episodes: List<Episode> = emptyList(),
    val page: Int = 1,
    val hasNextPage: Boolean = true,
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val errorMessage: String = ""
)
