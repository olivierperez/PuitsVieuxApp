package fr.o80.puitsvieux.data

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import fr.o80.puitsvieux.CommonDispatchers
import fr.o80.puitsvieux.R
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PizzaListProvider @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val dispatchers: CommonDispatchers
) {

    private val json = Json

    suspend fun fetch(): List<Pizza> {
        return withContext(dispatchers.io) {
            context.resources.openRawResource(R.raw.pizzas)
                .use { inputStream -> json.decodeFromStream<PizzaFile>(inputStream) }
                .pizzas
        }
    }
}