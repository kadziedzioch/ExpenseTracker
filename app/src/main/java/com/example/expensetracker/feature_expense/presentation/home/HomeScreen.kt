package com.example.expensetracker.feature_expense.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.expensetracker.feature_expense.presentation.home.components.DefaultPieChart
import com.example.expensetracker.feature_expense.presentation.home.components.ExpenseCard
import com.example.expensetracker.feature_expense.presentation.home.components.RecentExpenseItem
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState

@Composable
fun HomeScreen(
    topPadding: Dp = 20.dp,
    cardImageSize: Dp = 300.dp,
    viewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val state = viewModel.state.value
    val formattedDate = viewModel.formattedDate.value
    val scrollState = rememberScrollState()
    val dateDialogState = rememberMaterialDialogState()
    Box(
        modifier = modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .padding(top = topPadding + cardImageSize / 4f)
                .shadow(10.dp, RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(MaterialTheme.colors.surface)
                .align(Alignment.BottomCenter)
                .fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(top = cardImageSize / 4f)
                    .verticalScroll(scrollState)
            ) {
                Row(
                    modifier = Modifier
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Analytics",
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier
                            .fillMaxHeight()
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        onClick = {
                            dateDialogState.show()
                        }
                    ) {
                        Text(text = "Since $formattedDate")
                    }
                }
                if(state.expenses.isEmpty()){
                    Text(
                        text = "No expenses found in this period :(",
                        modifier = Modifier
                            .padding(15.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface,
                        textAlign = TextAlign.Center
                    )
                }
                else{
                    DefaultPieChart(expenses = state.expenses)
                }
                Text(
                    text = "Recent Expenses",
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .align(Alignment.Start),
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onSurface
                )
                if(state.recentExpenses.isEmpty()){
                    Text(
                        text = "No recent expenses :(",
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface,
                        textAlign = TextAlign.Center
                    )
                }
                else{
                    for (expense in state.recentExpenses) {
                        RecentExpenseItem(expense = expense)
                    }
                }

            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            ExpenseCard(
                finalSum = state.finalSum,
                modifier = Modifier
                    .height(cardImageSize / 2f)
                    .width(cardImageSize)
                    .offset(y = topPadding)
                    .align(Alignment.TopCenter)
            )
        }

        MaterialDialog(
            dialogState = dateDialogState,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
            ),
            buttons = {
                positiveButton(text = "Ok")
                negativeButton(text = "Cancel")
            },
            shape = RoundedCornerShape(15.dp),
            backgroundColor = MaterialTheme.colors.background
        ) {
            datepicker(
                initialDate = state.date,
                title = "Pick a date"
            ) {
                viewModel.onEvent(HomeEvent.ChangeDate(it))
            }
        }

    }


}
