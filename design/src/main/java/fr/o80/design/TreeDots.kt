package fr.o80.design

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TreeDots(
    modifier: Modifier
) {
    Canvas(modifier) {
        val canvasHeight = size.height
        val canvasWidth = size.width
        val circlesRadius = size.height / 4

        drawCircle(
            color = Color.Green,
            center = Offset(circlesRadius * 2, canvasHeight / 2),
            radius = circlesRadius
        )

        drawCircle(
            color = Color.White,
            center = Offset(canvasWidth / 2, canvasHeight / 2),
            radius = circlesRadius
        )

        drawCircle(
            color = Color.Red,
            center = Offset(canvasWidth - circlesRadius * 2, canvasHeight / 2),
            radius = circlesRadius
        )
    }
}

@Preview
@Composable
private fun TreeDotsPreview() {
    PuitsVieuxTheme {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TreeDots(
                modifier = Modifier
                    .width(200.dp)
                    .height(48.dp)
            )
            TreeDots(
                modifier = Modifier
                    .width(400.dp)
                    .height(64.dp)
            )
        }
    }
}