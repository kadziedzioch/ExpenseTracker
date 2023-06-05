package com.example.expensetracker.di

import android.app.Application
import androidx.room.Room
import com.example.expensetracker.feature_expense.data.data_source.ExpenseDatabase
import com.example.expensetracker.feature_expense.data.repository.ExpenseRepositoryImpl
import com.example.expensetracker.feature_expense.domain.repository.ExpenseRepository
import com.example.expensetracker.feature_expense.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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
    fun provideExpenseUseCases(expenseRepository: ExpenseRepository):ExpenseUseCases{
        return ExpenseUseCases (
            addExpense = AddExpense(expenseRepository),
            deleteExpense = DeleteExpense(expenseRepository),
            getExpenses = GetExpenses(expenseRepository),
            getExpensesByDate = GetExpensesByDate(expenseRepository)
        )
    }

}