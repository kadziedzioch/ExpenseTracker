package com.example.expensetracker.feature_expense.domain.use_case

import com.example.expensetracker.feature_expense.domain.model.Expense
import com.example.expensetracker.feature_expense.domain.repository.ExpenseRepository
import com.example.expensetracker.feature_expense.domain.util.ExpenseCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class GetExpensesByDate(
    private val repository: ExpenseRepository
) {
    operator fun invoke(
        date: Long = LocalDate.now().withDayOfMonth(1).toEpochDay()
    ): Flow<Map<ExpenseCategory,List<Expense>>> {

        return repository.getExpensesByDate(date).map {
            it.groupBy { expense->
                ExpenseCategory.valueOf(expense.category)
            }
        }

    }

}