package com.openpay.test.presentation.providers

import android.content.Context
import com.openpay.test.domain.providers.ResourceProvider
import javax.inject.Inject

class DefaultResourceProvider @Inject constructor(
    private val context: Context
) : ResourceProvider {

    override fun getString(resId: Int): String = context.getString(resId)
}