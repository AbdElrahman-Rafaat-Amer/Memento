package com.abdelrahman.raafat.memento.di

import android.content.Context
import androidx.room.Room
import com.abdelrahman.raafat.memento.data.local.dao.ReminderDao
import com.abdelrahman.raafat.memento.data.local.db.MementoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    fun provideReminderDao(appDatabase: MementoDatabase): ReminderDao = appDatabase.reminderDao()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): MementoDatabase =
        Room.databaseBuilder(
            context = context,
            klass = MementoDatabase::class.java,
            name = "memento_database"
        ).fallbackToDestructiveMigration(true)
            .build()
}