package com.openpay.test.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.openpay.test.domain.model.Episode
import com.openpay.test.presentation.viewmodels.EpisodesViewModel

@Composable
fun EpisodesScreen(viewModel: EpisodesViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = viewModel::onSearch,
            label = { Text("Buscar episodio") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            itemsIndexed(state.episodes) { index, episode ->
                if (index >= state.episodes.size - 1 && state.hasNextPage && !state.isLoading) {
                    viewModel.loadNextPage()
                }

                EpisodeItem(episode)
            }

            if (state.isLoading) {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }

}

@Composable
fun EpisodeItem(episode: Episode) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text = episode.name, fontWeight = FontWeight.Bold)
        Text(text = "Fecha de emisión: ${episode.airDate}")
    }
}