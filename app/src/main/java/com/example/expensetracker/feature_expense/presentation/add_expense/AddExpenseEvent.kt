package com.example.expensetracker.feature_expense.presentation.add_expense

import com.example.expensetracker.feature_expense.domain.util.ExpenseCategory
import java.time.LocalDate

sealed class AddExpenseEvent {
    data class EnteredDescription(val description: String): AddExpenseEvent()
    data class PickedDate(val date: LocalDate): AddExpenseEvent()
    data class EnteredAmount(val amount: String): AddExpenseEvent()
    object DropDownMenuExpandStateChanged : AddExpenseEvent()
    data class PickedCategory(val expenseCategory: ExpenseCategory) : AddExpenseEvent()
    object SaveExpense : AddExpenseEvent()
}