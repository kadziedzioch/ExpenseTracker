package com.example.expensetracker.feature_expense.presentation.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.expensetracker.feature_expense.presentation.history.components.ExpenseItem
import kotlinx.coroutines.launch

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState
) {
    val searchText by viewModel.searchText.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp))
    {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Category")
            }
            Spacer(modifier = modifier.weight(1f))
            OutlinedTextField(
                value = searchText,
                onValueChange = {viewModel.onEvent(HistoryEvent.SearchedText(it))},
                trailingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                },
                placeholder = {
                    Text(text = "Search expenses...")
                }
            )
        }
        when(val state = uiState){
            is HistoryUiState.SearchResult ->{
                LazyColumn(modifier = Modifier.padding(10.dp)){
                    items(state.expenses){
                        expense ->
                        ExpenseItem(
                            expense = expense,
                            onDeleteClicked = {
                                viewModel.onEvent(HistoryEvent.ExpenseDeleted(expense))
                                scope.launch {
                                    val result = scaffoldState.snackbarHostState.showSnackbar(
                                        message = "Expense deleted",
                                        actionLabel = "Undo"
                                    )
                                    if(result == SnackbarResult.ActionPerformed){
                                        viewModel.onEvent(HistoryEvent.RestoreExpense)
                                    }
                                }
                            }
                        )
                    }
                }
            }
            is HistoryUiState.NothingFound ->{
                Text(
                    text = "No expenses found",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

    }
}