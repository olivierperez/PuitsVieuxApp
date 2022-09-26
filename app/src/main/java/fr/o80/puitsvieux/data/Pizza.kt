package fr.o80.puitsvieux.data

import fr.o80.puitsvieux.data.serializer.BigDecimalSerializer
import fr.o80.puitsvieux.data.serializer.ElementsSerializer
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
    @Serializable(with = ElementsSerializer::class)
    val elements: List<String>
)