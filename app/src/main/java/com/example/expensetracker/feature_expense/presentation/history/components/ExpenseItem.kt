package com.example.expensetracker.feature_expense.presentation.history.components

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
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun ExpenseItem(
    modifier: Modifier = Modifier,
    expense: Expense,
    onDeleteClicked: () -> Unit
) {
    var expanded by remember{
        mutableStateOf(false)
    }
    val formattedDate =
        DateTimeFormatter
            .ofPattern("dd/MMM/yy")
            .format(LocalDate.ofEpochDay(expense.date))


    Column(
        modifier = modifier
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            .fillMaxWidth()
    ){
        Text(
            text = formattedDate,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(top = 7.dp),
            color = MaterialTheme.colors.onSurface
        )
        Row(
            modifier = Modifier
                .clickable(onClick = { expanded = !expanded })
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                imageVector =  ExpenseCategory.valueOf(expense.category).icon,
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "$" + expense.amount.toString(),
                style = MaterialTheme.typography.h6,
                modifier = Modifier.fillMaxHeight(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onSurface
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = onDeleteClicked) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onSurface
                )
            }
        }
        if(expanded){
            Text(
                text = expense.description,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                color = MaterialTheme.colors.onSurface
            )
        }
        Divider(
            thickness = 1.dp
        )
    }


}