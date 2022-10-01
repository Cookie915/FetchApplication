package com.example.fetchapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.fetchapplication.model.data.NetworkResult
import com.example.fetchapplication.ui.composables.CollapsableLazyColumn
import com.example.fetchapplication.ui.composables.CollapsableSection
import com.example.fetchapplication.ui.theme.FetchApplicationTheme
import com.example.fetchapplication.viewmodels.FetchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val fetchViewModel: FetchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val fetchData = fetchViewModel.fetchData.collectAsState()
            val fetchList = fetchViewModel.listMap.collectAsState()
            FetchApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(
                        Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_clipboard),
                                contentDescription = "List Icon",
                                modifier = Modifier.size(64.dp)
                            )
                        }
                        Divider(
                            Modifier
                                .fillMaxWidth()
                                .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 16.dp),
                            MaterialTheme.colors.primaryVariant
                        )
                        when (fetchData.value) {
                            is NetworkResult.Loading -> {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier,
                                        color = MaterialTheme.colors.primary
                                    )
                                }
                            }
                            is NetworkResult.Error -> {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.TopCenter
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Spacer(modifier = Modifier.height(16.dp))
                                        Card(
                                            backgroundColor = Color.LightGray,
                                            modifier = Modifier.fillMaxWidth(0.9f)
                                        ) {
                                            Text(
                                                text = "Error: ${fetchData.value.message}",
                                                modifier = Modifier.padding(4.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(32.dp))
                                        IconButton(onClick = { fetchViewModel.getFetchData() }) {
                                            Icon(
                                                Icons.Default.Refresh,
                                                tint = MaterialTheme.colors.primaryVariant,
                                                modifier = Modifier.size(48.dp),
                                                contentDescription = ""
                                            )
                                        }
                                    }
                                }
                            }
                            is NetworkResult.Success -> {
                                val sections: MutableList<CollapsableSection> = mutableListOf()
                                fetchList.value.forEach { list ->
                                    sections.add(CollapsableSection(list.key.toString(), list.value))
                                }
                                sections.sortBy { section ->
                                    section.title
                                }
                                CollapsableLazyColumn(sections = sections)
                            }
                        }
                    }
                }
            }
        }
    }
}
