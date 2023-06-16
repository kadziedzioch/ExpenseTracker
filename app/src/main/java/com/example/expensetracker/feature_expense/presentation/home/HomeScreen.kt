package com.example.expensetracker.feature_expense.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.expensetracker.feature_expense.presentation.home.components.DefaultPieChart
import com.example.expensetracker.feature_expense.presentation.home.components.ExpenseCard

@Composable
fun HomeScreen(
    topPadding: Dp = 20.dp,
    cardImageSize: Dp = 200.dp,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.onSurface)
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = topPadding + cardImageSize / 2f)
                .shadow(10.dp, RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(MaterialTheme.colors.surface)
                .align(Alignment.BottomCenter)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = 100.dp)
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
                        onClick = {}
                    ){
                        Text(text = "Since 01.04.23")
                    }

                }
                DefaultPieChart(expenses = state.expenses)
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
            ExpenseCard(
                finalSum = state.finalSum,
                modifier = Modifier
                    .height(cardImageSize)
                    .width(cardImageSize + 100.dp)
                    .offset(y = topPadding)
                    .align(Alignment.TopCenter)
            )
        }

    }
}
