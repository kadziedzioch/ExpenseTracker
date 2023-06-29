package com.example.expensetracker.feature_expense.presentation.history.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.expensetracker.feature_expense.domain.util.ExpenseCategory


@Composable
fun CategoryItem(
    modifier : Modifier = Modifier,
    category: ExpenseCategory,
    onDeleteCategory: ()-> Unit
) {

    Box(modifier = modifier
        .clip(RoundedCornerShape(5.dp))
        .background(MaterialTheme.colors.secondary)
    ){
        Row(
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = category.name,
                color = MaterialTheme.colors.onSecondary
            )
            Spacer(modifier = Modifier.width(5.dp))
            IconButton(
                onClick = onDeleteCategory,
                modifier = Modifier.then(Modifier.size(24.dp))
            ) {
                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = "Delete category ${category.name}",
                    tint = MaterialTheme.colors.onSecondary
                )
            }
        }

    }

}