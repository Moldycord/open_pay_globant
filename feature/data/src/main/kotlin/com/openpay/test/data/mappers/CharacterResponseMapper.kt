package com.openpay.test.data.mappers

import com.openpay.test.data.response_models.AllCharactersResponseModel
import com.openpay.test.domain.model.Character
import com.openpay.test.domain.model.PaginatedResult

fun AllCharactersResponseModel.toDomain(): PaginatedResult<Character> {
    return PaginatedResult(
        items = results.map { item ->
            Character(
                id = item.id,
                image = item.image,
                name = item.name,
                location = item.location.name,
                firstEpisode = item.episode.first()
            )
        },
        nextPage = info.next?.substringAfter("page=")?.toIntOrNull(),
        totalPages = info.pages
    )
}