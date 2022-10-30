package com.vladds.foosballking.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladds.foosballking.data.gameshistory.GameHistoryRecord
import com.vladds.foosballking.data.gameshistory.GameHistoryRecordId
import com.vladds.foosballking.data.gameshistory.GameScore
import com.vladds.foosballking.data.gameshistory.GamesHistoryRecordsRepository
import com.vladds.foosballking.data.player.Player
import com.vladds.foosballking.data.player.PlayersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddGameHistoryRecordViewModel @Inject constructor(
    private val gamesHistoryRepository: GamesHistoryRecordsRepository,
    private val playersRepository: PlayersRepository,
) : ViewModel() {

    // needed to check whether we update or add record
    private var cachedGameHistoryRecord: GameHistoryRecord? = null

    private val _firstPlayer = MutableStateFlow(EMPTY_STATE)
    val firstPlayer: StateFlow<PlayerState> = _firstPlayer.asStateFlow()

    private val _secondPlayer = MutableStateFlow(EMPTY_STATE)
    val secondPlayer: StateFlow<PlayerState> = _secondPlayer.asStateFlow()

    fun loadGame(id: GameHistoryRecordId) {
        viewModelScope.launch {
            val gameRecord = gamesHistoryRepository.getRecordById(id)
            if (gameRecord != null) {
                with(gameRecord) {
                    cachedGameHistoryRecord = gameRecord
                    _firstPlayer.value = PlayerState(firstPlayer, firstPlayerScore)
                    _secondPlayer.value = PlayerState(secondPlayer, secondPlayerScore)
                }
            }
        }
    }

    fun updateFirstPlayer(player: Player?) {
        _firstPlayer.value = _firstPlayer.value.copy(player = player)
    }

    fun updateFirstPlayer(id: Long) {
        viewModelScope.launch {
            updateFirstPlayer(playersRepository.getPlayerById(id))
        }
    }

    fun updateSecondPlayer(id: Long) {
        viewModelScope.launch {
            updateSecondPlayer(playersRepository.getPlayerById(id))
        }
    }

    fun updateFirstPlayerScore(score: GameScore) {
        _firstPlayer.value = _firstPlayer.value.copy(score = score)
    }

    fun updateSecondPlayer(player: Player?) {
        _secondPlayer.value = _secondPlayer.value.copy(player = player)
    }

    fun updateSecondPlayerScore(score: GameScore) {
        _secondPlayer.value = _secondPlayer.value.copy(score = score)
    }

    fun isValid(): Boolean {
        return buildBuilder().isValid()
    }

    fun save() {
        viewModelScope.launch {
            val builder = buildBuilder()
            if (builder.isValid()) {
                gamesHistoryRepository.insertOrUpdate(builder.build())
            }
        }
    }

    private fun buildBuilder(): GameHistoryRecord.Companion.Builder {
        val builder = GameHistoryRecord.Companion.Builder(cachedGameHistoryRecord)
        builder.firstPlayer = _firstPlayer.value.player
        builder.firstPlayerScore = _firstPlayer.value.score
        builder.secondPlayer = _secondPlayer.value.player
        builder.secondPlayerScore = _secondPlayer.value.score
        return builder
    }

    companion object {
        data class PlayerState(val player: Player?, val score: GameScore)
        val EMPTY_STATE = PlayerState(null, 0)
    }
}