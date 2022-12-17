package com.vladds.foosballking.presentation.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.vladds.foosballking.R
import com.vladds.foosballking.core.presentation.collectAsStateLifecycleAware
import com.vladds.foosballking.data.gameshistory.GameHistoryRecordId
import com.vladds.foosballking.foosballKingComponent
import kotlinx.coroutines.flow.StateFlow

@Composable
fun GameHistoryRecordDetailsScreen(
    navController: NavController,
    id: GameHistoryRecordId = -1,
    viewModel: AddGameHistoryRecordViewModel = viewModel(
        factory = LocalContext.current.foosballKingComponent().viewModelFactory
    ),
    onPickPlayerClicked: (String) -> Unit
) {
    LaunchedEffect(true) {
        viewModel.loadGame(id)
    }

    navController.currentBackStackEntry?.savedStateHandle?.get<Long>(PICK_FIRST_USER_KEY)
        ?.let { it: Long ->
            viewModel.updateFirstPlayer(it)
            navController.currentBackStackEntry?.savedStateHandle?.remove<Long>(PICK_FIRST_USER_KEY)
        }

    navController.currentBackStackEntry?.savedStateHandle?.get<Long>(PICK_SECOND_USER_KEY)?.let {
        viewModel.updateSecondPlayer(it)
        navController.currentBackStackEntry?.savedStateHandle?.remove<Long>(PICK_SECOND_USER_KEY)
    }

    AddGameHistoryView(
        viewModel.firstPlayer,
        viewModel.secondPlayer,
        viewModel.isValid(),
        firstPlayerClicked = { onPickPlayerClicked(PICK_FIRST_USER_KEY) },
        secondPlayerClicked = { onPickPlayerClicked(PICK_SECOND_USER_KEY) },
        firstPlayerScoreUpdated = { value ->
            viewModel.updateFirstPlayerScore(
                value.toInt().toByte()
            )
        },
        secondPlayerScoreUpdated = { value ->
            viewModel.updateSecondPlayerScore(
                value.toInt().toByte()
            )
        },
        onSaveClicked = {
            viewModel.save()
            navController.navigateUp()
        })
}

@Composable
fun AddGameHistoryView(
    firstPlayer: StateFlow<AddGameHistoryRecordViewModel.Companion.PlayerState>,
    secondPlayer: StateFlow<AddGameHistoryRecordViewModel.Companion.PlayerState>,
    isSaveEnabled: Boolean,
    firstPlayerClicked: () -> Unit = {},
    secondPlayerClicked: () -> Unit = {},
    firstPlayerScoreUpdated: (score: Float) -> Unit = {},
    secondPlayerScoreUpdated: (score: Float) -> Unit = {},
    onSaveClicked: () -> Unit = {}
) {
    val firstPlayerState by firstPlayer.collectAsStateLifecycleAware()
    val secondPlayerState by secondPlayer.collectAsStateLifecycleAware()
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Button(onClick = firstPlayerClicked) {
            Text(text = getPickPlayerButtonTitle(firstPlayerState))
        }
        Slider(
            value = firstPlayerState.score.toFloat(),
            onValueChange = firstPlayerScoreUpdated,
            valueRange = 0f..MAX_SCORE.toFloat(),
            steps = MAX_SCORE
        )
        Button(onClick = secondPlayerClicked) {
            Text(text = getPickPlayerButtonTitle(secondPlayerState))
        }
        Slider(
            value = secondPlayerState.score.toFloat(),
            valueRange = 0f..MAX_SCORE.toFloat(),
            onValueChange = secondPlayerScoreUpdated,
            steps = MAX_SCORE
        )
        Button(
            onClick = onSaveClicked,
            modifier = Modifier.fillMaxWidth(),
            enabled = isSaveEnabled,
        ) {
            Text(text = stringResource(R.string.save))
        }
    }
}

@Composable
private fun getPickPlayerButtonTitle(playerState: AddGameHistoryRecordViewModel.Companion.PlayerState): String =
    playerState.player?.name ?: stringResource(R.string.pick_user)

private const val MAX_SCORE = 7
const val PICK_FIRST_USER_KEY = "PICK_FIRST_USER_KEY"
const val PICK_SECOND_USER_KEY = "PICK_SECOND_USER_KEY"
