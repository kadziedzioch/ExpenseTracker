package com.example.expensetracker.feature_expense.presentation.history

import com.example.expensetracker.feature_expense.domain.model.Expense

interface HistoryUiState {
    data class SearchResult(val expenses: List<Expense> = emptyList()) : HistoryUiState
    object NothingFound : HistoryUiState

    object Loading : HistoryUiState
}