package com.example.expensetracker.feature_expense.domain.use_case

import com.example.expensetracker.feature_expense.domain.model.Expense
import com.example.expensetracker.feature_expense.domain.repository.ExpenseRepository
import com.example.expensetracker.feature_expense.domain.util.ExpenseCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetExpenses(
    private val repository: ExpenseRepository
) {
    operator fun invoke(
        expenseCategory: ExpenseCategory? = null
    ): Flow<List<Expense>>{
        return repository.getExpenses().map { expenses ->
            if(expenseCategory != null){
                expenses.sortedByDescending { it.date}.filter {
                    it.category == expenseCategory.name
                }
            }
            else{
                expenses.sortedByDescending {it.date}
            }
        }
    }
}