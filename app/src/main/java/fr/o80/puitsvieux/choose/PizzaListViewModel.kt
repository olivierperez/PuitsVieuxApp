package fr.o80.puitsvieux.choose

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.o80.puitsvieux.data.Pizza
import fr.o80.puitsvieux.data.PizzaListProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private val TAG = PizzaListViewModel::class.simpleName

@HiltViewModel
class PizzaListViewModel @Inject constructor(
    pizzaListProvider: PizzaListProvider
) : ViewModel() {

    private val _state: MutableStateFlow<PizzaListState> = MutableStateFlow(PizzaListState.empty())

    val state: StateFlow<PizzaListState> = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        PizzaListState.empty()
    )

    init {
        viewModelScope.launch {
            try {
                val pizzas = pizzaListProvider.fetch()
                _state.update { it.copy(pizzas = pizzas, hasError = false, canCallPizzeria = true) }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to get pizzas", e)
                _state.update {
                    it.copy(
                        pizzas = emptyList(),
                        hasError = true,
                        canCallPizzeria = false
                    )
                }
            }
        }
    }

    fun callPizzeria() {
        _state.update { it.copy(callPizzeria = true) }
    }

    fun onPizzeriaCalled() {
        _state.update { it.copy(callPizzeria = false) }
    }

}

data class PizzaListState(
    val pizzas: List<Pizza>,
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