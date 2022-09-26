package fr.o80.design.atom

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.o80.design.PuitsVieuxTheme

@Composable
fun Chip(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(6.dp),
        border = BorderStroke(
            width = .3.dp,
            color = MaterialTheme.colors.secondary.copy(alpha = .5f),
        ),
        contentColor = MaterialTheme.colors.secondary
    ) {
        Box(
            modifier = Modifier.padding(4.dp),
            content = content
        )
    }
}

@Preview
@Composable
fun ChipPreview() {
    PuitsVieuxTheme {
        Chip {
            Text("This is a chip")
        }
    }
}