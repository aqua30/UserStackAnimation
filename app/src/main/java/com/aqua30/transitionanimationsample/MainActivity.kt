package com.aqua30.transitionanimationsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val configuration = LocalConfiguration.current
            val screenHeight = configuration.screenHeightDp.dp
            val screenWidth = configuration.screenWidthDp.dp
            TransitionSampleScreen(
                screenWidth, screenHeight
            )
        }
    }
}

val userImages = listOf(
    R.drawable.img_user_1,
    R.drawable.img_user_2,
    R.drawable.img_user_3,
    R.drawable.img_user_4,
    R.drawable.img_user_5,
)

@Composable
fun TransitionSampleScreen(
    screenWidth: Dp,
    screenHeight: Dp
) {
    var isHorizontal by remember {
        mutableStateOf(false)
    }

    val animationDuration = 500
    val easing = FastOutSlowInEasing

    Box(modifier = Modifier.fillMaxSize()) {
        userImages.forEachIndexed { index, drawable ->
            val offsetX = (screenWidth - 25.dp) / 2 + if (isHorizontal) (index * 25).dp else 0.dp
            val offsetY = ((screenHeight  - 25.dp) / 2) + if (isHorizontal) 0.dp else (index * 50).dp
            val lastElementVisibility = if (isHorizontal && index == userImages.lastIndex) 0f else 1f
            val targetCordX by animateDpAsState(
                targetValue = offsetX,
                animationSpec = tween(
                    easing = easing,
                    durationMillis = animationDuration
                )
            )
            val targetCordY by animateDpAsState(
                targetValue = offsetY,
                animationSpec = tween(
                    easing = easing,
                    durationMillis = animationDuration
                )
            )
            val alpha by animateFloatAsState(
                targetValue = lastElementVisibility,
                animationSpec = tween(
                    easing = easing,
                    durationMillis = animationDuration
                )
            )
            val modifier = Modifier
                .size(50.dp)
                .absoluteOffset(
                    x = targetCordX,
                    y = targetCordY,
                )
                .alpha(alpha)
            UserImage(modifier = modifier, image = drawable)
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = { isHorizontal = isHorizontal.not() }
        ) {
            Text("Click")
        }
    }
}

@Composable
fun UserImage(modifier: Modifier, image: Int) {
    Card(
        modifier = modifier,
        shape = CircleShape,
        border = BorderStroke(
            width = 3.dp,
            color = Color.Black
        ),
        elevation = 5.dp
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = "user image",
        )
    }
}