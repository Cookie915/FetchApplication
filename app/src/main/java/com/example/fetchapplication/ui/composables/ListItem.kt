package com.example.fetchapplication.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fetchapplication.model.data.FetchData

@Composable
fun ListItem(data: FetchData) {
    Row(modifier = Modifier
        .fillMaxWidth(0.95f)
        .border(1.dp, MaterialTheme.colors.primaryVariant, RoundedCornerShape(25))
        .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${data.name}",
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.Light,
            maxLines = 1,
            textAlign = TextAlign.Start
        )
    }
}

@Preview
@Composable
fun ListItemPreview() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        Spacer(modifier = Modifier.height(34.dp))
        ListItem(data = FetchData(1, 287, "Item 287"))
    }
}