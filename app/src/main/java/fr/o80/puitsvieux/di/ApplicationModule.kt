package fr.o80.puitsvieux.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.o80.puitsvieux.CommonDispatchers
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {
    @Provides
    fun provideDispatchers() = CommonDispatchers(
        default = Dispatchers.Default,
        io = Dispatchers.IO,
        main = Dispatchers.Main,
        unconfined = Dispatchers.Unconfined,
    )
}