package fr.o80.puitsvieux

import kotlinx.coroutines.CoroutineDispatcher

class CommonDispatchers(
    val default: CoroutineDispatcher,
    val io: CoroutineDispatcher,
    val main: CoroutineDispatcher,
    val unconfined: CoroutineDispatcher,
)