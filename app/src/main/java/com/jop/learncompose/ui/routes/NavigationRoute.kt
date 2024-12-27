package com.jop.learncompose.ui.routes

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jop.learncompose.data.models.Note
import com.jop.learncompose.module.archiveNote.screen.ArchiveNoteScreen
import com.jop.learncompose.module.main.screen.MainScreen
import com.jop.learncompose.module.manageNote.screen.ManageNoteScreen

@Composable
fun NavigationRoute(modifier: Modifier){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.MAIN,
        enterTransition = { scaleIn(tween(700), initialScale = 0.5f) + fadeIn(tween(50)) },
        exitTransition = { scaleOut(tween(500), targetScale = 0.5f) + fadeOut(tween(50)) },
        popEnterTransition = { scaleIn(tween(700), initialScale = 0.5f) + fadeIn(tween(50)) },
        popExitTransition = { scaleOut(tween(500), targetScale = 0.5f) + fadeOut(tween(50)) }
    ){
        composable(Route.MAIN){
            MainScreen(navController)
        }
        composable(Route.MANAGE_NOTE){
            val note = navController.previousBackStackEntry?.savedStateHandle?.get<Note>("note")
            ManageNoteScreen(navController = navController, note = note)
        }
        composable(Route.ARCHIVE_NOTE){
            ArchiveNoteScreen(navController)
        }
    }
}