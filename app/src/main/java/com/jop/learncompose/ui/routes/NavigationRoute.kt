package com.jop.learncompose.ui.routes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jop.learncompose.ui.module.main.screen.MainScreen
import com.jop.learncompose.ui.module.manageNote.screen.ManageNoteScreen

@Composable
fun NavigationRoute(modifier: Modifier = Modifier){
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = Route.MAIN){
        composable(Route.MAIN){
            MainScreen()
        }
        composable(Route.MANAGE_NOTE){
            ManageNoteScreen()
        }
    }
}