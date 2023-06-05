package com.example.expensetracker.feature_expense.data.data_source

import androidx.room.*
import com.example.expensetracker.feature_expense.domain.model.Expense
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expense WHERE date >= :date")
    fun getExpensesByDate(date: Long) : Flow<List<Expense>>

    @Query("SELECT * FROM expense")
    fun getExpenses(): Flow<List<Expense>>

    @Query("SELECT * FROM expense WHERE id = :id")
    suspend fun getExpenseById(id: Int): Expense

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense:Expense)

    @Delete
    suspend fun deleteExpense(expense: Expense)

}