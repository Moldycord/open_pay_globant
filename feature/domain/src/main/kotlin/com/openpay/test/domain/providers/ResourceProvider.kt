package com.openpay.test.domain.providers

interface ResourceProvider {
    fun getString(resId: Int): String
}