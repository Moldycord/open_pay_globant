package com.openpay.test.domain.usecase

import com.openpay.test.domain.model.Character
import com.openpay.test.domain.model.PaginatedResult
import com.openpay.test.domain.repository.RickAndMortyRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val repository: RickAndMortyRepository
) {

    suspend operator fun invoke(page: Int): PaginatedResult<Character> {
        return repository.getCharacters(page)
    }
}