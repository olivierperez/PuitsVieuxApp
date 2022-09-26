package fr.o80.design.atom

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.o80.design.PuitsVieuxTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Chip(
    onClick: () -> Unit,
    selected: Boolean,
    content: @Composable RowScope.() -> Unit
) {
    val borderColor by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colors.primary else Color.Transparent
    )

    FilterChip(
        onClick = onClick,
        selected = selected,
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(1.dp, borderColor),
        content = content
    )
}

@Preview
@Composable
fun ChipPreview() {
    PuitsVieuxTheme {
        Column {
            Chip(
                onClick = {},
                selected = false
            ) {
                Text("This is a chip")
            }
            Chip(
                onClick = {},
                selected = true
            ) {
                Text("Selected one")
            }
        }
    }
}