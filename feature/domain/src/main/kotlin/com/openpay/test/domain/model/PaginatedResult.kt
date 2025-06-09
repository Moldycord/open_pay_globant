package com.openpay.test.domain.model

data class PaginatedResult<T>(
    val items: List<T>,
    val nextPage: Int?,
    val totalPages: Int
)