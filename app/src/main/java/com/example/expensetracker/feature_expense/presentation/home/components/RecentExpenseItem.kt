package com.example.expensetracker.feature_expense.presentation.home.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.expensetracker.feature_expense.domain.model.Expense
import com.example.expensetracker.feature_expense.domain.util.ExpenseCategory
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun RecentExpenseItem(
    modifier: Modifier = Modifier,
    expense: Expense
) {

    val formattedDate =
        DateTimeFormatter
            .ofPattern("dd/MMM/yy")
            .format(LocalDate.ofEpochDay(expense.date))

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                imageVector =  ExpenseCategory.valueOf(expense.category).icon,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "$" + expense.amount.toString(),
                style = MaterialTheme.typography.h6,
                modifier = Modifier.fillMaxHeight(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = formattedDate,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(top = 7.dp)
            )
        }
        Divider(
            thickness = 1.dp
        )
    }


}