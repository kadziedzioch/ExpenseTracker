package com.example.expensetracker.feature_expense.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.feature_expense.domain.model.Expense
import com.example.expensetracker.feature_expense.domain.use_case.ExpenseUseCases
import com.example.expensetracker.feature_expense.domain.util.ExpenseCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val expenseUseCases: ExpenseUseCases
) : ViewModel(){

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _categoryList = MutableStateFlow(emptyList<ExpenseCategory>())
    val categoryList = _categoryList.asStateFlow()

    private var recentlyDeletedExpense: Expense? = null


    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val uiState = _searchText
        .debounce(500)
        .combine(_categoryList){
            text, list ->
            Pair(list, text)
        }
        .flatMapLatest { pair ->
            expenseUseCases.getExpenses(pair.first, pair.second)
        }
        .map {
            result ->
            if (result.isEmpty()) HistoryUiState.NothingFound
            else HistoryUiState.SearchResult(result)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = HistoryUiState.NothingFound
        )


    fun onEvent(historyEvent: HistoryEvent){
        when(historyEvent){
            is HistoryEvent.CategoryClicked -> {
                val categories = mutableListOf<ExpenseCategory>()
                for (category in categoryList.value){
                    categories.add(category)
                }
                if(categories.contains(historyEvent.expenseCategory)){
                    categories.remove(historyEvent.expenseCategory)
                }
                else{
                    categories.add(historyEvent.expenseCategory)
                }
                _categoryList.value = categories
            }
            is HistoryEvent.ExpenseDeleted -> {
                viewModelScope.launch {
                    expenseUseCases.deleteExpense(historyEvent.expense)
                    recentlyDeletedExpense = historyEvent.expense
                }
            }
            is HistoryEvent.RestoreExpense -> {
                viewModelScope.launch {
                    expenseUseCases.addExpense(recentlyDeletedExpense ?: return@launch)
                    recentlyDeletedExpense = null
                }
            }
            is HistoryEvent.SearchedText -> {
                _searchText.value = historyEvent.text
            }
        }
    }








}