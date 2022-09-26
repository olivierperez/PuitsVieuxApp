package fr.o80.puitsvieux.choose

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.o80.puitsvieux.CommonDispatchers
import fr.o80.puitsvieux.data.Pizza
import fr.o80.puitsvieux.data.PizzaListProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private val TAG = PizzaListViewModel::class.simpleName

@HiltViewModel
class PizzaListViewModel @Inject constructor(
    pizzaListProvider: PizzaListProvider,
    dispatchers: CommonDispatchers
) : ViewModel() {

    private val pizzas: MutableStateFlow<List<Pizza>> = MutableStateFlow(emptyList())
    private val selectedElements: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    private val error: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val callPizzeria: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val state: StateFlow<PizzaListState> =
        combine(
            pizzas,
            selectedElements,
            error,
            callPizzeria
        ) { pizzas, selectedElements, error, callPizzeria ->
            withContext(dispatchers.default) {
                PizzaListState(
                    pizzas = pizzas
                        .filter { pizza -> selectedElements.isEmpty() || selectedElements.all { pizza.elements.contains(it) } }
                        .map { it.toUiModel(selectedElements) },
                    callPizzeria = callPizzeria,
                    canCallPizzeria = pizzas.isNotEmpty(),
                    hasError = error
                )
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            PizzaListState.empty()
        )

    init {
        viewModelScope.launch {
            try {
                val pizzas = pizzaListProvider.fetch()
                this@PizzaListViewModel.pizzas.update { pizzas }
                error.update { false }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to get pizzas", e)
                error.update { true }
            }
        }
    }

    fun callPizzeria() {
        callPizzeria.update { true }
    }

    fun onPizzeriaCalled() {
        callPizzeria.update { false }
    }

    fun onElementClicked(element: ElementUiModel) {
        selectedElements.update { previouslySelected ->
            if (element.selected) {
                previouslySelected.filterNot { it == element.name }
            } else {
                previouslySelected + element.name
            }
        }
    }

}

fun Pizza.toUiModel(selectedElements: List<String>): PizzaUiModel {
    return PizzaUiModel(
        name,
        price,
        elements.map {
            ElementUiModel(
                it,
                it in selectedElements
            )
        }
    )
}

data class PizzaListState(
    val pizzas: List<PizzaUiModel>,
    val canCallPizzeria: Boolean,
    val callPizzeria: Boolean,
    val hasError: Boolean
) {

    companion object {
        fun empty() = PizzaListState(
            pizzas = emptyList(),
            callPizzeria = false,
            canCallPizzeria = false,
            hasError = false
        )
    }
}