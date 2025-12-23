package com.abdelrahman.raafat.memento.di

import com.abdelrahman.raafat.memento.data.repository.OfflineReminderRepository
import com.abdelrahman.raafat.memento.domain.ReminderRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule{
    @Binds
    abstract fun provideReminderRepository(
        implementation: OfflineReminderRepository
    ) : ReminderRepository
}