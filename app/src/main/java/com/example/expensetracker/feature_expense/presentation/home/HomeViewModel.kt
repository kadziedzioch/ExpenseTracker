package com.example.expensetracker.feature_expense.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.feature_expense.domain.use_case.ExpenseUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val expenseUseCases: ExpenseUseCases
): ViewModel(){

    private val _state = mutableStateOf(HomeState())
    val state : State<HomeState> = _state

    val formattedDate = derivedStateOf {
        DateTimeFormatter
            .ofPattern("dd/MMM/yy")
            .format(_state.value.date)
    }

    private var getExpensesJob: Job? = null

    init {
        getExpenses(LocalDate.now().withDayOfMonth(1).toEpochDay())
        getRecentExpenses()
    }

    fun onEvent(homeEvent: HomeEvent){
        when(homeEvent){
            is HomeEvent.ChangeDate -> {
                if(homeEvent.date == state.value.date){
                    return
                }
                _state.value = state.value.copy(
                    date = homeEvent.date
                )
                getExpenses(homeEvent.date.toEpochDay())
            }
        }
    }

    private fun getRecentExpenses(){
        expenseUseCases.getRecentExpenses()
            .onEach { expenses ->
                _state.value = state.value.copy(
                    recentExpenses = expenses
                )
            }.launchIn(viewModelScope)
    }
    private fun getExpenses(date: Long){
        getExpensesJob?.cancel()
        getExpensesJob = expenseUseCases.getExpensesByDate(date)
            .onEach { expenses ->
                _state.value = state.value.copy(
                    expenses = expenses,
                    finalSum = expenses.values.sumOf{ it.sumOf { expense -> expense.amount }}
                )
            }.launchIn(viewModelScope)
    }


}