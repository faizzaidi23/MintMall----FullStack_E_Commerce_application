package com.example.fullstackshoppingapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier:Modifier= Modifier
){
    NavHost(
        navController = navController,
        startDestination = BottomNavScreen.HomeScreen.route
    ){
        composable(route= BottomNavScreen.HomeScreen.route){
            HomeScreen()
        }
        composable(route= BottomNavScreen.SearchScreen.route){
            SearchScreen()
        }
        composable(route= BottomNavScreen.CategoryScreen.route){
            CategoryScreen()
        }
        composable(route= BottomNavScreen.ProfileScreen.route){
            ProfileScreen()
        }
    }
}