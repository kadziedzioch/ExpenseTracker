package com.example.expensetracker.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.example.expensetracker.feature_expense.data.data_source.ExpenseDatabase
import com.example.expensetracker.feature_expense.data.repository.ExpenseRepositoryImpl
import com.example.expensetracker.feature_expense.domain.repository.ExpenseRepository
import com.example.expensetracker.feature_expense.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

private const val LIMIT_PREFERENCES = "limit_preferences"
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideExpenseDatabase(app: Application): ExpenseDatabase{
        return Room.databaseBuilder(
            app,
            ExpenseDatabase::class.java,
            ExpenseDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideExpenseRepository(db: ExpenseDatabase):ExpenseRepository {
        return ExpenseRepositoryImpl(db.expenseDao)
    }

    @Provides
    @Singleton
    fun provideExpenseUseCases(expenseRepository: ExpenseRepository): ExpenseUseCases {
        return ExpenseUseCases (
            addExpense = AddExpense(expenseRepository),
            deleteExpense = DeleteExpense(expenseRepository),
            getExpenses = GetExpenses(expenseRepository),
            getExpensesByDate = GetExpensesByDate(expenseRepository),
            getRecentExpenses = GetRecentExpenses(expenseRepository)
        )
    }

    @Provides
    @Singleton
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(appContext,LIMIT_PREFERENCES)),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(LIMIT_PREFERENCES) }
        )
    }



}