package com.example.expensetracker.feature_expense.presentation.home.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme


@Composable
fun ExpenseCard(
    finalSum: Long,
    modifier: Modifier = Modifier
) {
        Box(
            modifier = modifier
        ) {
            val primaryColor = MaterialTheme.colors.primary
            Canvas(
                modifier = Modifier
                    .matchParentSize()
            ) {
                val clipPath = Path().apply{
                    addRoundRect(
                        RoundRect(
                            Rect(
                                offset = Offset(0f, 0f),
                                size = Size(size.width, size.height)
                            ),
                            cornerRadius = CornerRadius(10.dp.toPx())
                        )
                    )
                }
                clipPath(clipPath){
                    val path = Path().apply {
                        val width = size.width
                        val height = size.height
                        moveTo(width.times(.40f), height.times(.0f))
                        cubicTo(
                            width.times(.45f),
                            height.times(.0f),
                            width.times(.78f),
                            height.times(1.0f),
                            width.times(.90f),
                            height.times(.0f)
                        )
                        close()
                    }
                    drawRoundRect(
                        color = primaryColor,
                        cornerRadius = CornerRadius(10.dp.toPx())
                    )
                    drawPath(
                        path = path,
                        color = Color(
                            ColorUtils.blendARGB(primaryColor.toArgb(), Color.Transparent.toArgb(), 0.5f)
                        )
                    )
                    drawCircle(
                        color = Color(
                            ColorUtils.blendARGB(primaryColor.toArgb(), Color.Transparent.toArgb(), 0.1f)
                        ),
                        radius = 140.dp.toPx(),
                        center = Offset(size.width + 10f, size.height + 50f)
                    )

                    drawCircle(
                        color = Color(
                            ColorUtils.blendARGB(primaryColor.toArgb(), Color.Transparent.toArgb(), 0.3f)
                        ),
                        radius = 100.dp.toPx(),
                        center = Offset(-50f, size.height)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
                    .padding(start = 10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier) {
                        Text(
                            text = "Total Expenses",
                            style = MaterialTheme.typography.body2
                        )
                        Row(modifier = Modifier) {
                            Text(
                                text = "$",
                                style = MaterialTheme.typography.h4
                            )
                            Text(
                                text = finalSum.toString(),
                                style = MaterialTheme.typography.h4
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Set limit"
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "****  ****  ****  7156",
                    style = MaterialTheme.typography.subtitle1
                )
            }
        }

}

@Preview
@Composable
fun ExpenseCardPreview() {
    ExpenseTrackerTheme() {
        ExpenseCard(finalSum = 15000)
    }

}