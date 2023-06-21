package com.example.expensetracker.feature_expense.presentation.add_expense.components

import android.widget.Space
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.expensetracker.feature_expense.domain.util.ExpenseCategory
import com.example.expensetracker.feature_expense.presentation.add_expense.DropDownMenuState

@Composable
fun DropDownMenu(
    modifier: Modifier = Modifier,
    dropDownMenuState: DropDownMenuState,
    changeCategory: (String) -> Unit,
    changeExpandedState : ()->Unit
) {
    var mTextFieldSize by remember { mutableStateOf(Size.Zero)}

    val icon = if (dropDownMenuState.isExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(modifier = modifier) {

        OutlinedTextField(
            value = dropDownMenuState.selectedCategory.name,
            onValueChange = { changeCategory(it) },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    mTextFieldSize = coordinates.size.toSize()
                }
                .clickable(onClick = {
                    changeExpandedState()
                }),
            label = {Text("Category")},
            trailingIcon = {
                Icon(icon,null)
            },
            enabled = false,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current),
                disabledLabelColor =  MaterialTheme.colors.onSurface.copy(ContentAlpha.medium)
            ),
            leadingIcon = {
                Icon(dropDownMenuState.selectedCategory.icon,null)
            }

        )
        DropdownMenu(
            expanded = dropDownMenuState.isExpanded,
            onDismissRequest = changeExpandedState,
            modifier = Modifier
                .width(with(LocalDensity.current){mTextFieldSize.width.toDp()})
        ) {
            ExpenseCategory.values().forEach { category ->
                DropdownMenuItem(
                    onClick = {
                        changeCategory(category.name)
                    }
                )
                {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Icon(
                            imageVector = category.icon,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = category.name)
                    }

                }
            }
        }
    }
}