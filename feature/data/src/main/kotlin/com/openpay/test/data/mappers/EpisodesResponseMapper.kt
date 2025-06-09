package com.openpay.test.data.mappers

import com.openpay.test.data.response_models.AllEpisodesResponseModel
import com.openpay.test.domain.model.Episode
import com.openpay.test.domain.model.PaginatedResult

fun AllEpisodesResponseModel.toDomain(): PaginatedResult<Episode> {
    return PaginatedResult(
        items = results.map { item ->
            Episode(id = item.id, name = item.name, airDate = item.air_date)
        },
        nextPage = info.next?.substringAfter("page=")?.toIntOrNull(),
        totalPages = info.pages
    )
}