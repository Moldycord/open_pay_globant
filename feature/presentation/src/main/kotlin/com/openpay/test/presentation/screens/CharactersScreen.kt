package com.openpay.test.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.openpay.test.domain.model.Character
import com.openpay.test.feature.presentation.R
import com.openpay.test.presentation.viewmodels.CharactersViewModel
import com.openpay.test.presentation.views.ErrorDialog

@Composable
fun CharactersScreen(
    navController: NavController,
    viewModel: CharactersViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.spacing_large))
    ) {
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = viewModel::onSearch,
            label = { Text(stringResource(R.string.search_character)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium)))

        LazyColumn {
            itemsIndexed(state.characters) { index, character ->
                if (index >= state.characters.size - 1 && state.hasNextPage && !state.isLoading) {
                    viewModel.loadNextPage()
                }

                CharacterItem(character)
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
fun CharacterItem(character: Character) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(R.dimen.spacing_medium)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = character.image,
            contentDescription = null,
            modifier = Modifier
                .size(dimensionResource(R.dimen.img_size))
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.spacing_large)))
        Column {
            Text(text = character.name, fontWeight = FontWeight.Bold)
            Text(text = stringResource(R.string.location_label, character.location))
            Text(text = stringResource(R.string.first_episode_label, character.firstEpisode))
        }
    }
}
