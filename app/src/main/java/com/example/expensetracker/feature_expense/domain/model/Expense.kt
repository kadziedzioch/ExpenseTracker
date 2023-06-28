package com.example.expensetracker.feature_expense.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Expense (
    val amount : Long,
    val category: String,
    val description: String,
    val date: Long,
    @PrimaryKey val id: Int? = null
)

class InvalidExpenseException(override val message: String) : Exception()