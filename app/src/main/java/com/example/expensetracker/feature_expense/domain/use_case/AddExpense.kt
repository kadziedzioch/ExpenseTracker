package com.example.expensetracker.feature_expense.domain.use_case

import com.example.expensetracker.feature_expense.domain.model.Expense
import com.example.expensetracker.feature_expense.domain.model.InvalidExpenseException
import com.example.expensetracker.feature_expense.domain.repository.ExpenseRepository


class AddExpense(
    private val repository: ExpenseRepository
) {

    @Throws(InvalidExpenseException::class)
    suspend operator fun invoke(
        expense: Expense
    ){
        if(expense.amount <=0 ){
            throw InvalidExpenseException("The amount can't be less or equal to 0")
        }
        if(expense.description.isBlank() ){
            throw InvalidExpenseException("The description can't be empty")
        }
        repository.insertExpense(expense)
    }
}