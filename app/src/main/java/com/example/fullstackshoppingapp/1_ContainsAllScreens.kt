package com.example.fullstackshoppingapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllScreens(){

    val navController:NavHostController= rememberNavController()
    Scaffold(
      topBar = {
          TopAppBar(
              title = {
                  Text(
                      text="Zerodha",
                      fontSize = 36.sp,
                      fontWeight = FontWeight.ExtraBold,
                      textAlign = TextAlign.Center,
                      modifier = Modifier.fillMaxWidth()
                  )}
          )
      }, bottomBar = {
            NavigationBar{
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                val screens=listOf(BottomNavScreen.HomeScreen, BottomNavScreen.SearchScreen,
                    BottomNavScreen.CategoryScreen, BottomNavScreen.ProfileScreen)

                screens.forEach {screen->
                    NavigationBarItem(
                        label={Text(screen.title)},
                        icon={ Icon(screen.icon, contentDescription = null)},
                        selected = currentDestination?.hierarchy?.any{it.route==screen.route}==true,
                        onClick = {
                            navController.navigate(screen.route){
                                popUpTo(
                                    navController.graph.findStartDestination().id
                                ){
                                    saveState=true
                                }
                                launchSingleTop=true
                                restoreState=true
                            }
                        }
                    )
                }

            }
      }

    ){innerPadding->
        AppNavHost(navController =
        navController,
            modifier=Modifier.padding(innerPadding))


    }
}