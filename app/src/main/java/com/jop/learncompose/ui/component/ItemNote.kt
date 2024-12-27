package com.jop.learncompose.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jop.learncompose.data.models.Note
import com.jop.learncompose.ui.routes.Route
import com.jop.learncompose.ui.theme.Padding
import com.jop.learncompose.ui.theme.surfaceBrightLight
import com.jop.learncompose.ui.theme.surfaceLight
import com.jop.learncompose.utils.toColor

@Composable
fun ItemNote(navController: NavHostController, note: Note, isGrid: Boolean){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Padding.PADDING_8))
            .background(note.backgroundColor.toColor())
            .clickable {
                navController.currentBackStackEntry?.savedStateHandle?.set("note", note)
                navController.navigate(Route.MANAGE_NOTE)
            }
    ){
        Column(modifier = Modifier
            .padding(Padding.PADDING_8)
            .fillMaxHeight()) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = surfaceLight
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(Padding.PADDING_4))
            Text(
                modifier = Modifier.animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ),
                text = note.message,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = surfaceBrightLight,
                    fontSize = 10.sp,
                    lineHeight = 12.sp
                ),
                maxLines = if(isGrid) 10 else 6,
                minLines = if(isGrid) 10 else 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}