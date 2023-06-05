package com.example.expensetracker.feature_expense.domain.repository


import com.example.expensetracker.feature_expense.domain.model.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {

    fun getExpensesByDate(date: Long) : Flow<List<Expense>>

    fun getExpenses(): Flow<List<Expense>>

    suspend fun getExpenseById(id: Int): Expense

    suspend fun insertExpense(expense: Expense)

    suspend fun deleteExpense(expense: Expense)
}