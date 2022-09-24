package fr.o80.puitsvieux.choose

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.o80.puitsvieux.data.Pizza
import fr.o80.puitsvieux.data.PizzaListProvider
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

private val TAG = PizzaListViewModel::class.simpleName

@HiltViewModel
class PizzaListViewModel @Inject constructor(
    pizzaListProvider: PizzaListProvider
) : ViewModel() {

    val state: StateFlow<PizzaListState> = flow {
        try {
            val pizzas = pizzaListProvider.fetch()
            emit(PizzaListState.Content(pizzas))
        } catch(e: Exception) {
            Log.e(TAG, "Failed to get pizzas", e)
            emit(PizzaListState.Error)
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        PizzaListState.empty()
    )

}

sealed interface PizzaListState {

    object Error : PizzaListState

    data class Content(
        val pizzas: List<Pizza>
    ) : PizzaListState

    companion object {
        fun empty(): PizzaListState = Content(emptyList())
    }
}