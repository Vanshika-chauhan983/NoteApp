package com.vanshika.notesapp.feature_note.presentation.add_edit_note.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vanshika.notesapp.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun WavesAnimationOverlay(isSpoken: Boolean) {
    val waves = remember { List(4) { Animatable(0f) } }

    LaunchedEffect(Unit) {
        if (isSpoken) {
            waves.forEachIndexed { index, animatable ->
                launch {
                    delay(index * 300L)
                    animatable.animateTo(
                        targetValue = 1f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(2000, easing = LinearEasing),
                            repeatMode = RepeatMode.Restart
                        )
                    )
                }
            }
        }else{
            waves.forEach { animatable ->
                animatable.snapTo(0f)
            }
        }
    }
    if (isSpoken) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            waves.forEach { animatable ->
                Box(
                    Modifier
                        .size(150.dp)
                        .graphicsLayer {
                            scaleX = animatable.value * 3 + 1
                            scaleY = animatable.value * 3 + 1
                            alpha = 1 - animatable.value
                        }
                        .background(Color.Gray.copy(alpha = 0.4f), CircleShape)
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.mic),
                contentDescription = "Mic Recording",
                tint = Color.White,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}