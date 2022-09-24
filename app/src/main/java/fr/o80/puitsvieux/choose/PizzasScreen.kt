package fr.o80.puitsvieux.choose

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.o80.design.PuitsVieuxTheme
import fr.o80.design.TreeDots
import fr.o80.puitsvieux.collectAsStateLifecycleAware
import fr.o80.puitsvieux.data.Pizza
import java.math.BigDecimal

@Composable
fun PizzasScreen(
    modifier: Modifier,
    viewModel: PizzaListViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateLifecycleAware()

    if (state.callPizzeria) {
        val context = LocalContext.current
        LaunchedEffect(null) {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:0478200881")
            }
            context.startActivity(intent)
            viewModel.onPizzeriaCalled()
        }
    }

    when {
        state.hasError -> Text("An error occurred!", modifier)
        else -> PizzaList(modifier, state.pizzas, viewModel::callPizzeria)
    }
}

@Composable
private fun PizzaList(
    modifier: Modifier,
    pizzas: List<Pizza>,
    callPizzeria: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = callPizzeria) {
                Icon(Icons.Default.Phone, contentDescription = "Call")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier.padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Text(
                    "Les Pizzas du puits vieux",
                    style = MaterialTheme.typography.h2,
                    textAlign = TextAlign.Center
                )
            }
            item {
                TreeDots(
                    Modifier
                        .width(200.dp)
                        .height(48.dp)
                )
            }
            items(pizzas, key = { pizza -> pizza.name }) { pizza ->
                PizzaCard(pizza)
            }
        }
    }
}

@Composable
private fun PizzaCard(pizza: Pizza) {
    Card {
        Column(
            Modifier.padding(8.dp)
        ) {
            Row {
                Text(pizza.name, style = MaterialTheme.typography.h4)
                Spacer(Modifier.weight(1f))
                Text(pizza.price.toString() + "€", style = MaterialTheme.typography.h5)
            }
            Text(pizza.elements)
        }
    }
}

@Preview
@Composable
fun PizzasCardPreview() {
    PuitsVieuxTheme {
        PizzaCard(
            Pizza("Margarita", BigDecimal.ZERO, "tomate - crème - salade")
        )
    }
}

@Preview
@Composable
fun PizzasListPreview() {
    PuitsVieuxTheme {
        PizzaList(
            Modifier,
            listOf(
                Pizza("Margarita", BigDecimal.valueOf(13.45), "tomate - crème - salade"),
                Pizza("Calzone", BigDecimal.valueOf(27.95), "tomate - oeuf - salade"),
                Pizza("Ananas", BigDecimal.valueOf(95.27), "tomate - ananas - salade"),
            ),
            callPizzeria = {}
        )
    }
}