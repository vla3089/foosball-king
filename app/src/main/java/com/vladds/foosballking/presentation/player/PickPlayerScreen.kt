package com.vladds.foosballking.presentation.player

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.themeadapter.material.MdcTheme
import com.vladds.foosballking.core.presentation.collectAsStateLifecycleAware
import com.vladds.foosballking.data.player.Player
import com.vladds.foosballking.foosballKingComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun PickPlayerScreen(
    viewModel: PickPlayerViewModel = viewModel(
        factory = LocalContext.current.foosballKingComponent().viewModelFactory
    ),
    onPlayerPicked: (Player) -> Unit
) {
    GameHistoryRecordList(
        viewModel.players
    ) { player ->
        onPlayerPicked(player)
    }
}

@Preview
@Composable
private fun GameHistoryViewPreview() {
    val player = Player(0, "Dave")
    MdcTheme {
        GameHistoryRecordList(MutableStateFlow(listOf(player))) { }
    }
}

@Composable
fun GameHistoryRecordList(
    records: Flow<List<Player>>,
    onClick: (Player) -> Unit
) {
    val recordsState by records.collectAsStateLifecycleAware(emptyList())
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        items(items = recordsState) { record ->
            PlayerRow(record, onClick)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun PlayerRow(
    player: Player,
    onClick: (Player) -> Unit
) {
    ListItem(
        modifier = Modifier.clickable { onClick(player) },
        text = {
            Text(player.name)
        }
    )
}
