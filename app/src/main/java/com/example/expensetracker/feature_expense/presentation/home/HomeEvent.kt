package com.example.expensetracker.feature_expense.presentation.home

import java.time.LocalDate

sealed class HomeEvent {
    data class ChangeDate(val date: LocalDate): HomeEvent()
}