package com.openpay.test.domain.repository

import com.openpay.test.domain.model.Character
import com.openpay.test.domain.model.Episode
import com.openpay.test.domain.model.PaginatedResult

interface RickAndMortyRepository {

    suspend fun getCharacters(page: Int): PaginatedResult<Character>

    suspend fun getEpisodes(): PaginatedResult<Episode>

}