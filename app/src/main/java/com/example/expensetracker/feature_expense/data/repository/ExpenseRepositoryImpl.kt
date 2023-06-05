package com.example.expensetracker.feature_expense.data.repository

import com.example.expensetracker.feature_expense.data.data_source.ExpenseDao
import com.example.expensetracker.feature_expense.domain.model.Expense
import com.example.expensetracker.feature_expense.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow

class ExpenseRepositoryImpl(
    private val expenseDao: ExpenseDao
) : ExpenseRepository {
    override fun getExpensesByDate(date: Long): Flow<List<Expense>> {
        return expenseDao.getExpensesByDate(date)
    }

    override fun getExpenses(): Flow<List<Expense>> {
        return expenseDao.getExpenses()
    }

    override suspend fun getExpenseById(id: Int): Expense {
        return expenseDao.getExpenseById(id)
    }

    override suspend fun insertExpense(expense: Expense) {
       expenseDao.insertExpense(expense)
    }

    override suspend fun deleteExpense(expense: Expense) {
        expenseDao.deleteExpense(expense)
    }
}