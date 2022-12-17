package com.vladds.foosballking.presentation.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vladds.foosballking.R
import com.vladds.foosballking.core.presentation.collectAsStateLifecycleAware
import com.vladds.foosballking.data.gameshistory.GameHistoryRecord
import com.vladds.foosballking.data.gameshistory.formatAsSingleString
import com.vladds.foosballking.foosballKingComponent
import kotlinx.coroutines.flow.Flow

@Composable
fun HistoryScreen(
    viewModel: GameHistoryViewModel = viewModel(
        factory = LocalContext.current.foosballKingComponent().viewModelFactory
    ),
    navigateToHistoryDetails: (GameHistoryRecord) -> Unit
) {
    GameHistoryRecordList(
        viewModel.historyRecords
    ) { record ->
        navigateToHistoryDetails(record)
    }
}

@Composable
fun GameHistoryRecordList(
    records: Flow<List<GameHistoryRecord>>,
    onClick: (GameHistoryRecord) -> Unit
) {
    val recordsState by records.collectAsStateLifecycleAware(emptyList())
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        items(items = recordsState) { record ->
            GameHistoryRecordRow(record, onClick)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GameHistoryRecordRow(
    record: GameHistoryRecord,
    onClick: (GameHistoryRecord) -> Unit
) {
    ListItem(
        modifier = Modifier.clickable { onClick(record) },
        headlineText = {
            Text(record.formatAsSingleString())
        },
        trailingContent = {
            Icon(
                painter = painterResource(R.drawable.ic_baseline_keyboard_arrow_right_24),
                contentDescription = null
            )
        }
    )
}
