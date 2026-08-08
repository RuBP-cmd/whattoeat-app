package com.rubp.whattoeat.ui.screens.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rubp.whattoeat.ui.components.AppTopBar
import com.rubp.whattoeat.ui.screens.home.HomeScreen
import kotlin.time.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import kotlinx.datetime.number

@Composable
fun CalendarScreen(onBack: () -> Unit) {
    // 获取今天
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())

    // 初始化为当月 1 号
    var currentMonthView by remember {
        mutableStateOf(LocalDate(today.year, today.month.number, 1))
    }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    // 计算当月天数
    val daysInMonth = remember(currentMonthView) {
        val nextMonth = currentMonthView.plus(1, DateTimeUnit.MONTH)
        val lastDay = nextMonth.minus(1, DateTimeUnit.DAY)
        lastDay.dayOfMonth
    }

    // 周几偏移
    val firstDayOfWeek = remember(currentMonthView) {
        (currentMonthView.dayOfWeek.ordinal + 1) % 7
    }

    CalendarContent(
        today = today,
        currentMonth = currentMonthView,
        daysInMonth = daysInMonth,
        firstDayOfWeek = firstDayOfWeek,
        selectedDate = selectedDate,
        onBack = onBack,
        onPrevMonth = { currentMonthView = currentMonthView.minus(1, DateTimeUnit.MONTH) },
        onNextMonth = { currentMonthView = currentMonthView.plus(1, DateTimeUnit.MONTH) },
        onSelectDate = { date -> selectedDate = date }
    )
}

@Composable
private fun CalendarContent(
    today: LocalDate,
    currentMonth: LocalDate,
    daysInMonth: Int,
    firstDayOfWeek: Int,
    selectedDate: LocalDate?,
    onBack: () -> Unit,
    onPrevMonth: () -> Unit,
    onNextMonth: () -> Unit,
    onSelectDate: (LocalDate) -> Unit
) {
    Scaffold(
        topBar = { AppTopBar(onBack, "日历", {}) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = onPrevMonth) { Text("上月") }
                Text(
                    text = "${currentMonth.year}年 ${currentMonth.month.number}月",
                    style = MaterialTheme.typography.titleMedium
                )
                Button(onClick = onNextMonth) { Text("下月") }
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                listOf("日", "一", "二", "三", "四", "五", "六").forEach { dayLabel ->
                    Text(
                        text = dayLabel,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(firstDayOfWeek, key = { "blank_$it" }) {
                    Spacer(Modifier.aspectRatio(1f))
                }
                items(daysInMonth, key = { "day_$it" }) { index ->
                    val day = index + 1
                    val date = LocalDate(currentMonth.year, currentMonth.month.number, day)

                    DayCell(
                        day = day,
                        isToday = (date == today),
                        isSelected = (date == selectedDate),
                        onClick = { onSelectDate(date) }
                    )
                }
            }
        }
    }
}

@Composable
private fun DayCell(
    day: Int,
    isToday: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        isSelected -> MaterialTheme.colorScheme.primary
        isToday -> MaterialTheme.colorScheme.primaryContainer
        else -> MaterialTheme.colorScheme.surface
    }
    val textColor = when {
        isSelected -> MaterialTheme.colorScheme.onPrimary
        isToday -> MaterialTheme.colorScheme.onPrimaryContainer
        else -> MaterialTheme.colorScheme.onSurface
    }

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(2.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$day",
            color = textColor,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CalendarScreenPreview() {
    MaterialTheme {
        CalendarScreen(onBack = {})
    }
}