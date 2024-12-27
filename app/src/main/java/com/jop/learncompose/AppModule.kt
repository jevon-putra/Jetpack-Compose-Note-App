package com.jop.learncompose

import android.content.Context
import androidx.room.Room
import com.jop.learncompose.data.repositories.NoteRepository
import com.jop.learncompose.data.local.LocalDatabase
import com.jop.learncompose.data.local.dao.NoteDao
import com.jop.learncompose.data.AppData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun buildLocalDatabase(@ApplicationContext context: Context): LocalDatabase {
        return Room.databaseBuilder(context, LocalDatabase::class.java, "learn_compose")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideNoteDao(localDatabase: LocalDatabase): NoteDao {
        return localDatabase.noteDao()
    }

    @Provides
    fun provideNoteRepository(noteDao: NoteDao): NoteRepository {
        return NoteRepository(noteDao)
    }

    @Provides
    fun provideAppData(@ApplicationContext context: Context): AppData {
        return AppData(context)
    }
}