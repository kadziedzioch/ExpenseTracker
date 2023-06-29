package com.example.expensetracker.feature_expense.domain.use_case

import com.example.expensetracker.feature_expense.domain.model.Expense
import com.example.expensetracker.feature_expense.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetRecentExpenses(
    private val repository: ExpenseRepository
) {
    operator fun invoke() : Flow<List<Expense>> {
        return repository.getExpenses().map { expenses ->
            expenses.sortedByDescending { it.date }.take(4)
        }
    }

}