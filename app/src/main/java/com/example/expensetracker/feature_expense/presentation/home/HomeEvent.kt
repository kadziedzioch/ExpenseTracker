package com.example.expensetracker.feature_expense.presentation.home

sealed class HomeEvent {
    data class ChangeDate(val date: Long): HomeEvent()
}