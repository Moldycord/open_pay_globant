package com.danieer.galvez.openpaytest.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.openpay.test.presentation.model.Screen
import com.openpay.test.presentation.screens.CharactersScreen
import com.openpay.test.presentation.screens.EpisodesScreen
import com.openpay.test.presentation.screens.HomeScreen

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Characters.route) { CharactersScreen() }
        composable(Screen.Episodes.route) { EpisodesScreen() }
    }
}
