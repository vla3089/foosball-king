package com.vladds.foosballking.presentation.ranking

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.themeadapter.material.MdcTheme
import com.vladds.foosballking.R
import com.vladds.foosballking.core.presentation.collectAsStateLifecycleAware
import com.vladds.foosballking.domain.PlayersRankingUseCase
import com.vladds.foosballking.foosballKingComponent
import kotlinx.coroutines.flow.Flow

@Composable
fun RankingScreen(
    viewModel: PlayerRankingViewModel = viewModel(
        factory = LocalContext.current.foosballKingComponent().viewModelFactory
    )
) {
    var pagerState by remember { mutableStateOf(0) }
    Column {
        RankedTabs(pagerState) {
            pagerState = it
        }
        RankedPlayersList(
            viewModel.getRankedPlayersForCategory(
                if (pagerState == 0) {
                    RankingCategory.ByGamesPlayed
                } else {
                    RankingCategory.ByGamesWon
                }
            )

        )
    }
}

@Composable
fun RankedPlayersList(
    rankedPlayers: Flow<List<PlayersRankingUseCase.RankedPlayer>>,
    onClick: (PlayersRankingUseCase.RankedPlayer) -> Unit = {}
) {
    val state by rankedPlayers.collectAsStateLifecycleAware(emptyList())
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        items(items = state) { rankedPlayer ->
            RankedPlayerRow(rankedPlayer, onClick)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RankedPlayerRow(
    rankedPlayer: PlayersRankingUseCase.RankedPlayer,
    onClick: (PlayersRankingUseCase.RankedPlayer) -> Unit
) {
    ListItem(
        modifier = Modifier.clickable { onClick(rankedPlayer) },
        text = {
            Text(rankedPlayer.player.name)
        },
        trailing = {
            Text(rankedPlayer.score.toString())
        }
    )
}

@Composable
@Preview
fun RankedTabsPreview() {
    MdcTheme {
        RankedTabs()
    }
}

@Composable
fun RankedTabs(
    selectedTabIndex: Int = 0,
    onTabClicked: (Int) -> Unit = {}
) {
    val tabs = listOf(R.string.by_games_played, R.string.by_victories)
    Column {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    text = { Text(stringResource(id = tab)) },
                    selected = selectedTabIndex == index,
                    onClick = { onTabClicked(index) }
                )
            }
        }
    }
}
