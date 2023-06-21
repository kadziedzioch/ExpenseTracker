package com.example.expensetracker.feature_expense.presentation.home.components

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.expensetracker.feature_expense.domain.model.Expense
import com.example.expensetracker.feature_expense.domain.util.ExpenseCategory
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

@Composable
fun DefaultPieChart(
    expenses: Map<ExpenseCategory, List<Expense>>,
    modifier: Modifier = Modifier,
    pieChartSize: Dp = 200.dp
) {
    val colorPalette = arrayListOf(
        MaterialTheme.colors.primary.toArgb(),
        MaterialTheme.colors.secondary.toArgb(),
        MaterialTheme.colors.error.toArgb(),
        MaterialTheme.colors.onSurface.toArgb()
    )
    val holeColor = MaterialTheme.colors.surface.toArgb()
    Crossfade(targetState = expenses) { pieChartData ->
        AndroidView(factory = { context ->
            PieChart(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                )
                this.description.isEnabled = false
                this.isDrawHoleEnabled = true
                this.legend.isEnabled = false
                this.setHoleColor(holeColor)
                this.legend.horizontalAlignment =
                    Legend.LegendHorizontalAlignment.CENTER

            }
        },
            modifier = modifier
                .size(pieChartSize)
                .padding(5.dp), update = {
                updatePieChartWithData(
                    it,
                    pieChartData,
                    colorPalette
                )
            })
    }

}

private fun updatePieChartWithData(
    chart: PieChart,
    data: Map<ExpenseCategory, List<Expense>>,
    colorPalette : ArrayList<Int>
){
    val entries = ArrayList<PieEntry>()
    for ((category, expenses) in data) {
        entries.add(PieEntry(expenses.sumOf { it.amount }.toFloat(), category.name))
    }
    val ds = PieDataSet(entries, "")

    ds.colors = colorPalette
    ds.yValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE
    ds.xValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE
    ds.sliceSpace = 2f
    ds.setDrawValues(false)
    val d = PieData(ds)
    chart.data = d
    chart.invalidate()
}