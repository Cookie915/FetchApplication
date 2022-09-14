package com.example.fetchapplication.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fetchapplication.model.data.FetchData

@Composable
fun CollapsableLazyColumn(
    sections: List<CollapsableSection>,
    modifier: Modifier = Modifier
) {
    val collapseState = remember(sections) { sections.map { true }.toMutableStateList()}
    LazyColumn(modifier) {
        sections.forEachIndexed { i, item ->
            val collapsed = collapseState[i]
            item(key = "header_$i") {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = false, color = MaterialTheme.colors.primaryVariant)
                        ) {
                            collapseState[i] = !collapsed
                        },
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        item.title,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .weight(1f),
                        color = MaterialTheme.colors.primary
                    )
                    Icon(
                        Icons.Default.run {
                            if (collapsed)
                                KeyboardArrowDown
                            else
                                KeyboardArrowUp
                        },
                        contentDescription = "Expand List",
                        tint = MaterialTheme.colors.primaryVariant
                    )
                }
                Divider()
                Spacer(modifier = Modifier.height(2.dp))
            }
            if (!collapsed) {
                items(item.rows) { fetchData ->
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp), horizontalArrangement = Arrangement.Center) {
                        ListItem(data = fetchData)
                    }
                }
            }
        }
    }
}

data class CollapsableSection(val title: String, val rows: List<FetchData>)