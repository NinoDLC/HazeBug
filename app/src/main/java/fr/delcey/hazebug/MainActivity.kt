package fr.delcey.hazebug

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import fr.delcey.hazebug.ui.theme.HazeBugTheme
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HazeBugTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Content(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        var isRotating by rememberSaveable { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            delay(1.seconds)
            isRotating = true
        }

        val rotation: Float by animateFloatAsState(
            targetValue = if (!isRotating) 0F else 1F,
            animationSpec = tween(durationMillis = 10_000),
            label = "rotation",
        )

        Card(
            modifier = Modifier
                .size(width = 218.dp, height = 386.dp)
                .graphicsLayer {
                    rotationZ = rotation * 360
                }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                val hazeState = rememberHazeState()

                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .hazeSource(state = hazeState),
                    painter = painterResource(R.drawable.background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .hazeEffect(
                            state = hazeState,
                            style = HazeStyle(
                                backgroundColor = Color(0xFF666666),
                                tint = HazeTint(Color(0x12666666)),
                                blurRadius = 24.dp,
                                noiseFactor = 0F,
                            )
                        )
                ) {
                    Text(
                        modifier = Modifier
                            .padding(
                                start = 8.dp,
                                top = 12.dp,
                                end = 8.dp,
                                bottom = 20.dp,
                            )
                            .align(Alignment.Center),
                        text = "12M chances to find your perfect place",
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HazeBugTheme {
        Content()
    }
}