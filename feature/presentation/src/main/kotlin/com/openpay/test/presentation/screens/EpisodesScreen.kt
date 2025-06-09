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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.openpay.test.domain.model.Episode
import com.openpay.test.feature.presentation.R
import com.openpay.test.presentation.viewmodels.EpisodesViewModel
import com.openpay.test.presentation.views.ErrorDialog

@Composable
fun EpisodesScreen(navController: NavController, viewModel: EpisodesViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()
    val searchLabel = stringResource(R.string.search_episode)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.spacing_large))
    ) {
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = viewModel::onSearch,
            label = { Text(searchLabel) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium)))

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
                            .padding(dimensionResource(R.dimen.spacing_large))
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
        if (state.errorMessage.isNotEmpty()) {
            ErrorDialog(
                message = state.errorMessage,
                onRetry = {
                    viewModel.clearError()
                    viewModel.loadNextPage()
                },
                onDismiss = {
                    viewModel.clearError()
                    navController.popBackStack()
                }
            )
        }
    }

}

@Composable
fun EpisodeItem(episode: Episode) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(R.dimen.spacing_medium))
    ) {
        Text(text = episode.name, fontWeight = FontWeight.Bold)
        Text(text = stringResource(R.string.air_date_label, episode.airDate))
    }
}