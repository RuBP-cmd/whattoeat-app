package com.rubp.whattoeat.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.composables.icons.materialicons.MaterialIcons
import com.composables.icons.materialicons.filled.Calendar_month
import com.rubp.whattoeat.ui.components.CardButton

@Composable
fun HomeScreen(
    onNavigateToEat: () -> Unit,
    onNavigateToPracticalWebsite: () -> Unit,
    onNavigateToOther: () -> Unit,
    onNavigateToCalendar: () -> Unit
) {
    val uriHandler = LocalUriHandler.current
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primary),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "WhatToEat",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )
            IconButton(onClick = onNavigateToCalendar) {
                Icon(
                    imageVector = MaterialIcons.Filled.Calendar_month,
                    contentDescription = "日历",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
        ) {
            MobaMessageCard()

            Column(
                modifier = Modifier.width(IntrinsicSize.Max),
            ) {
                val modifier = Modifier.height(70.dp)
                CardButton(
                    title = "Moba",
                    subtitle = "我们需要开始moba！",
                    modifier = modifier
                ) { onNavigateToEat() }
                CardButton(
                    title = "实用网站",
                    subtitle = "看看有哪些实用网站",
                    modifier = modifier
                ) { onNavigateToPracticalWebsite() }
                CardButton(
                    title = "测网速",
                    subtitle = "感觉学校网又卡了？",
                    modifier = modifier
                ) { uriHandler.openUri("https://test.xidian.edu.cn") }
                CardButton(
                    title = "其他",
                    modifier = modifier,
                ) { onNavigateToOther() }
            }
        }

    }
}
@Composable
private fun MobaMessageCard(){
    Card(
        modifier = Modifier
            .padding(30.dp)
            .width(400.dp)
            .height(80.dp)
    ){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "点击Moba开始\nmoba Queen!",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview(){
    HomeScreen(
        {},
        {},
        {},
        {}
    )
}