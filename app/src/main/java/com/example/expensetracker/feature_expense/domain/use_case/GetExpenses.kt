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
        expenseCategories: List<ExpenseCategory> = emptyList(),
        searchText: String = ""
    ): Flow<List<Expense>>{
        return repository.getExpenses().map { expenses ->
            if(expenseCategories.isNotEmpty()){
                if(searchText.isNotEmpty()){
                    expenses.sortedByDescending { it.date}.filter {
                        expenseCategories.contains(ExpenseCategory.valueOf(it.category)) &&
                                it.description.lowercase().contains(searchText.lowercase())
                    }
                }
                else{
                    expenses.sortedByDescending { it.date}.filter {
                        expenseCategories.contains(ExpenseCategory.valueOf(it.category))
                    }
                }
            }
            else{
                if(searchText.isNotEmpty()){
                    expenses.sortedByDescending {it.date}.filter {
                        it.description.lowercase().contains(searchText.lowercase())
                    }
                }
                else{
                    expenses.sortedByDescending {it.date}
                }
            }
        }
    }
}