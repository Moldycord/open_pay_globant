package com.openpay.test.data.repository

import com.openpay.test.data.mappers.toDomain
import com.openpay.test.data.service.RickAndMortyService
import com.openpay.test.domain.model.Character
import com.openpay.test.domain.model.Episode
import com.openpay.test.domain.model.PaginatedResult
import com.openpay.test.domain.repository.RickAndMortyRepository
import javax.inject.Inject

class RickAndMortyRepositoryImpl @Inject constructor(
    private val rickAndMortyService: RickAndMortyService
) : RickAndMortyRepository {


    override suspend fun getCharacters(page: Int): PaginatedResult<Character> {
        return rickAndMortyService.getCharacters(page).toDomain()
    }

    override suspend fun getEpisodes(page: Int): PaginatedResult<Episode> {
        return rickAndMortyService.getEpisodes(page).toDomain()
    }
}