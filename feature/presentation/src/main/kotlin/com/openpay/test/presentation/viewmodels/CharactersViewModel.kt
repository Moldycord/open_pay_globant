package com.openpay.test.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.openpay.test.domain.model.Character
import com.openpay.test.domain.providers.ResourceProvider
import com.openpay.test.domain.usecase.GetCharactersUseCase
import com.openpay.test.feature.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    application: Application,
    private val getCharactersUseCase: GetCharactersUseCase,
    private val resourceProvider: ResourceProvider
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(CharactersUiState())
    val uiState: StateFlow<CharactersUiState> = _uiState

    init {
        loadNextPage()
    }

    fun onSearch(query: String) {
        _uiState.update {
            it.copy(
                searchQuery = query,
                characters = emptyList(),
                page = 1,
                hasNextPage = true
            )
        }
        loadNextPage(reset = true, searchQuery = query)
    }

    fun loadNextPage(reset: Boolean = false, searchQuery: String = "") {
        val currentState = _uiState.value
        if (!currentState.hasNextPage || currentState.isLoading) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val result = getCharactersUseCase(currentState.page)
                val filtered = if (searchQuery.isNotBlank()) {
                    result.items.filter {
                        it.name.contains(searchQuery, ignoreCase = true)
                    }
                } else result.items

                _uiState.update {
                    it.copy(
                        characters = if (reset) filtered else it.characters + filtered,
                        page = (it.page + 1),
                        hasNextPage = result.nextPage != null,
                        isLoading = false
                    )
                }
            } catch (error: Exception) {
                _uiState.update {
                    val errorMessage =
                        error.localizedMessage ?: resourceProvider.getString(R.string.error_unknown)
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

data class CharactersUiState(
    val characters: List<Character> = emptyList(),
    val page: Int = 1,
    val hasNextPage: Boolean = true,
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val errorMessage: String = ""
)