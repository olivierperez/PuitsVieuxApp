package fr.o80.puitsvieux.choose

import java.math.BigDecimal

class PizzaUiModel(
    val name: String,
    val price: BigDecimal,
    val elements: List<ElementUiModel>
)

class ElementUiModel(
    val name: String,
    val selected: Boolean
)
