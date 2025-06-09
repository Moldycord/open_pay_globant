package com.openpay.test.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openpay.test.domain.model.Character
import com.openpay.test.domain.usecase.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

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
        loadNextPage(reset = true)
    }

    fun loadNextPage(reset: Boolean = false) {
        val currentState = _uiState.value
        if (!currentState.hasNextPage || currentState.isLoading) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val result = getCharactersUseCase(currentState.page)
            val filtered = if (currentState.searchQuery.isNotBlank()) {
                result.items.filter {
                    it.name.contains(currentState.searchQuery, ignoreCase = true)
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
        }
    }
}

data class CharactersUiState(
    val characters: List<Character> = emptyList(),
    val page: Int = 1,
    val hasNextPage: Boolean = true,
    val isLoading: Boolean = false,
    val searchQuery: String = ""
)