package fr.o80.puitsvieux.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PizzaFile(
    @SerialName("pizzas")
    val pizzas: List<Pizza>
)