package com.example.expensetracker.feature_expense.presentation.history

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.expensetracker.feature_expense.domain.util.ExpenseCategory
import com.example.expensetracker.feature_expense.presentation.history.components.CategoryItem
import com.example.expensetracker.feature_expense.presentation.history.components.ExpenseItem
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.launch

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState
) {
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val categoryList by viewModel.categoryList.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val dialogState = rememberMaterialDialogState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface),
    )
    {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(10.dp)
        )
        {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { dialogState.show() }) {
                    Text(text = "Category")
                }
                Spacer(modifier = modifier.weight(1f))
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { viewModel.onEvent(HistoryEvent.SearchedText(it)) },
                    trailingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    },
                    placeholder = {
                        Text(text = "Search expenses...")
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = MaterialTheme.colors.onSurface
                    )
                )
            }
            if (categoryList.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .horizontalScroll(scrollState)
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    for (expenseCategory in categoryList) {
                        CategoryItem(
                            category = expenseCategory,
                            onDeleteCategory = {
                                viewModel.onEvent(
                                    HistoryEvent.CategoryClicked(
                                        expenseCategory
                                    )
                                )
                            }
                        )
                        Spacer(modifier = Modifier.width(7.dp))
                    }
                }
            }

            MaterialDialog(
                dialogState = dialogState,
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                ),
                shape = RoundedCornerShape(15.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(10.dp)
                ) {
                    for (expenseCategory in ExpenseCategory.values()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = categoryList.contains(expenseCategory),
                                onCheckedChange = {
                                    viewModel.onEvent(
                                        HistoryEvent.CategoryClicked(
                                            expenseCategory
                                        )
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(text = expenseCategory.name)
                        }
                    }
                }
            }


            when (val state = uiState) {
                is HistoryUiState.SearchResult -> {
                    LazyColumn(modifier = Modifier.padding(10.dp)) {
                        items(state.expenses) { expense ->
                            ExpenseItem(
                                expense = expense,
                                onDeleteClicked = {
                                    viewModel.onEvent(HistoryEvent.ExpenseDeleted(expense))
                                    scope.launch {
                                        val result = scaffoldState.snackbarHostState.showSnackbar(
                                            message = "Expense deleted",
                                            actionLabel = "Undo"
                                        )
                                        if (result == SnackbarResult.ActionPerformed) {
                                            viewModel.onEvent(HistoryEvent.RestoreExpense)
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
                is HistoryUiState.NothingFound -> {
                    Text(
                        text = "No expenses found",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp),
                        textAlign = TextAlign.Center
                    )
                }
                is HistoryUiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)
                    ){
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                }
            }
        }
    }

}