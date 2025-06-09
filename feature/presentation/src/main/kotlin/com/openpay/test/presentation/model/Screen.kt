package com.openpay.test.presentation.model

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Characters : Screen("characters")
    data object Episodes : Screen("episodes")
}
