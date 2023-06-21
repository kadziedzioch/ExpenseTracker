package com.example.expensetracker.feature_expense.presentation.add_expense

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.feature_expense.domain.model.Expense
import com.example.expensetracker.feature_expense.domain.model.InvalidExpenseException
import com.example.expensetracker.feature_expense.domain.use_case.ExpenseUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val expenseUseCases: ExpenseUseCases
): ViewModel() {

    private val _dropDownMenuState = mutableStateOf(DropDownMenuState())
    val dropDownMenuState : State<DropDownMenuState> = _dropDownMenuState

    private val _descriptionState = mutableStateOf(ExpenseTextFieldState())
    val descriptionState : State<ExpenseTextFieldState> = _descriptionState

    private val _amountState = mutableStateOf(ExpenseTextFieldState())
    val amountState : State<ExpenseTextFieldState> = _amountState

    private val _eventFlow =  MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _dateState = mutableStateOf(
        DateTimeFormatter.ofPattern("dd/MMM/yy").format(LocalDate.now())
    )
    val dateState : State<String> = _dateState

    fun onEvent(event: AddExpenseEvent){
        when(event){
            is AddExpenseEvent.DropDownMenuExpandStateChanged -> {
                _dropDownMenuState.value = dropDownMenuState.value.copy(
                    isExpanded = !dropDownMenuState.value.isExpanded
                )
            }
            is AddExpenseEvent.EnteredAmount -> {
                _amountState.value = amountState.value.copy(
                    text = event.amount
                )
            }
            is AddExpenseEvent.SaveExpense -> {
                viewModelScope.launch {
                    try {
                        expenseUseCases.addExpense(
                            Expense(
                                amount = amountState.value.text.toLong(),
                                category = dropDownMenuState.value.selectedCategory.name,
                                description = descriptionState.value.text,
                                date = LocalDate
                                    .parse(
                                        dateState.value,DateTimeFormatter.ofPattern("dd/MMM/yy")
                                    )
                                    .toEpochDay()
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveExpense)
                    }
                    catch (e: InvalidExpenseException){
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                e.message ?: "Couldn't save expense"
                            )
                        )

                    }
                }
            }
            is AddExpenseEvent.EnteredDescription ->{
                _descriptionState.value = descriptionState.value.copy(
                    text = event.description
                )
            }
            is AddExpenseEvent.PickedDate ->{
                _dateState.value =  DateTimeFormatter
                    .ofPattern("dd/MMM/yy")
                    .format(event.date)
            }
            is AddExpenseEvent.PickedCategory -> {
                _dropDownMenuState.value = dropDownMenuState.value.copy(
                    selectedCategory = event.expenseCategory,
                    isExpanded = false
                )

            }
        }

    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        object SaveExpense: UiEvent()
    }


}