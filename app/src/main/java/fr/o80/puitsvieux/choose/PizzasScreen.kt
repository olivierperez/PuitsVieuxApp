package fr.o80.puitsvieux.choose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.o80.design.PuitsVieuxTheme
import fr.o80.puitsvieux.collectAsStateLifecycleAware
import fr.o80.puitsvieux.data.Pizza
import java.math.BigDecimal

@Composable
fun PizzasScreen(
    modifier: Modifier,
    viewModel: PizzaListViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateLifecycleAware()

    when (val valState = state) {
        is PizzaListState.Content -> PizzasList(modifier, valState.pizzas)
        PizzaListState.Error -> Text("An error occurred!", modifier)
    }
}

@Composable
private fun PizzasList(
    modifier: Modifier,
    pizzas: List<Pizza>
) {
    LazyColumn(
        modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(pizzas, key = { pizza -> pizza.name }) { pizza ->
            PizzaCard(pizza)
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
        PizzasList(
            Modifier,
            listOf(
                Pizza("Margarita", BigDecimal.valueOf(13.45), "tomate - crème - salade"),
                Pizza("Calzone", BigDecimal.valueOf(27.95), "tomate - oeuf - salade"),
                Pizza("Ananas", BigDecimal.valueOf(95.27), "tomate - ananas - salade"),
            )
        )
    }
}