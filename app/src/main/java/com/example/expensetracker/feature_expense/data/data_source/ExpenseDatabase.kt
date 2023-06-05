package com.example.expensetracker.feature_expense.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.expensetracker.feature_expense.domain.model.Expense

@Database(
    entities = [Expense::class],
    version = 1
)
abstract class ExpenseDatabase :RoomDatabase() {

    abstract val expenseDao: ExpenseDao

    companion object{
        const val DATABASE_NAME = "expense_db"
    }
}