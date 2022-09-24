package fr.o80.puitsvieux.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class Pizza(
    @SerialName("name")
    val name: String,

    @SerialName("price")
    @Serializable(with = BigDecimalSerializer::class)
    val price: BigDecimal,

    @SerialName("elements")
    val elements: String
)