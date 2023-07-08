package com.example.expensetracker.feature_expense.presentation.add_expense

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expensetracker.feature_expense.domain.util.ExpenseCategory
import com.example.expensetracker.feature_expense.presentation.add_expense.components.DropDownMenu
import com.example.expensetracker.feature_expense.presentation.util.Screen
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate

@Composable
fun AddExpenseScreen(
    modifier : Modifier = Modifier,
    topPadding: Dp = 80.dp,
    viewModel: AddExpenseViewModel = hiltViewModel(),
    navController : NavController,
    scaffoldState: ScaffoldState
) {
    val descriptionState = viewModel.descriptionState.value
    val amountState = viewModel.amountState.value
    val dateState = viewModel.dateState.value
    val dropDownMenuState = viewModel.dropDownMenuState.value
    val scrollState = rememberScrollState()
    val dateDialogState = rememberMaterialDialogState()
    val formattedDate = viewModel.formattedDate.value


    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest { event->
            when(event){
                is AddExpenseViewModel.UiEvent.ShowSnackBar ->{
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is AddExpenseViewModel.UiEvent.SaveExpense ->{
                    navController.navigate(Screen.HomeScreen.screen_route)
                }
            }
        }
    }
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = topPadding)
                    .shadow(10.dp, RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .background(MaterialTheme.colors.surface)
                    .align(Alignment.BottomCenter)
            )
            {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(top = 10.dp)
                )
                {
                    DropDownMenu(
                        modifier = Modifier.padding(10.dp),
                        dropDownMenuState = dropDownMenuState,
                        changeCategory = {
                            viewModel.onEvent(AddExpenseEvent.PickedCategory(ExpenseCategory.valueOf(it))) },
                        changeExpandedState = {
                            viewModel.onEvent(AddExpenseEvent.DropDownMenuExpandStateChanged)
                        }
                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        label = { Text(text = "Amount") },
                        value = amountState.text,
                        onValueChange = {viewModel.onEvent(AddExpenseEvent.EnteredAmount(it))},
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        placeholder = {
                            Text(text = "Enter amount...")
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = MaterialTheme.colors.onSurface
                        )
                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .clickable(
                                onClick = {
                                    dateDialogState.show()
                                }
                            ),
                        label = { Text(text = "When") },
                        value = formattedDate,
                        onValueChange = {},
                        maxLines = 1,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = null
                            )
                        },
                        enabled = false,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            disabledTextColor = MaterialTheme.colors.onSurface,
                            disabledLabelColor =  MaterialTheme.colors.onSurface.copy(ContentAlpha.medium)
                        )
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        label = { Text(text = "Description") },
                        value = descriptionState.text,
                        onValueChange = {viewModel.onEvent(AddExpenseEvent.EnteredDescription(it))},
                        minLines = 7,
                        maxLines = 7,
                        placeholder = {
                            Text(text = "Enter description...")
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = MaterialTheme.colors.onSurface
                        )
                    )

                    Button(onClick = { viewModel.onEvent(AddExpenseEvent.SaveExpense) }) {
                        Text(text = "Save")
                    }

                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ){
                Text(
                    text =  Screen.AddExpenseScreen.title,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(top = topPadding/4f, start = 10.dp),
                    color = MaterialTheme.colors.onBackground
                )
            }

            MaterialDialog(
                dialogState = dateDialogState,
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                ),
                buttons = {
                    positiveButton(text = "Ok")
                    negativeButton(text = "Cancel")
                },
                shape = RoundedCornerShape(15.dp),
                backgroundColor = MaterialTheme.colors.background
            ) {
                datepicker(
                    initialDate = dateState,
                    title = "Pick a date",
                ) {
                    viewModel.onEvent(AddExpenseEvent.PickedDate(it))
                }
            }


        }






}