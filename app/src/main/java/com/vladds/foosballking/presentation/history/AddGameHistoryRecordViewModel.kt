package com.vladds.foosballking.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladds.foosballking.data.gameshistory.GameHistoryRecord
import com.vladds.foosballking.data.gameshistory.GameHistoryRecordId
import com.vladds.foosballking.data.gameshistory.GameScore
import com.vladds.foosballking.data.gameshistory.GamesHistoryRecordsRepository
import com.vladds.foosballking.data.player.Player
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddGameHistoryRecordViewModel @Inject constructor(
    private val gamesHistoryRepository: GamesHistoryRecordsRepository
) : ViewModel() {

    // needed to check whether we update or add record
    private var cachedGameHistoryRecord: GameHistoryRecord? = null

    private val _firstPlayer = MutableStateFlow<Player?>(null)
    val firstPlayer: Flow<Player?> = _firstPlayer.asStateFlow()

    private val _firstPlayerScore = MutableStateFlow<GameScore?>(null)
    val firstPlayerScore: Flow<GameScore?> = _firstPlayerScore.asStateFlow()

    private val _secondPlayer = MutableStateFlow<Player?>(null)
    val secondPlayer: Flow<Player?> = _secondPlayer.asStateFlow()

    private val _secondPlayerScore = MutableStateFlow<GameScore?>(null)
    val secondPlayerScore: Flow<GameScore?> = _secondPlayerScore.asStateFlow()

    fun loadGame(id: GameHistoryRecordId) {
        viewModelScope.launch {
            val gameRecord = gamesHistoryRepository.getRecordById(id)
            if (gameRecord != null) {
                with(gameRecord) {
                    cachedGameHistoryRecord = gameRecord
                    updateFirstPlayer(firstPlayer)
                    updateFirstPlayerScore(firstPlayerScore)
                    updateSecondPlayer(secondPlayer)
                    updateSecondPlayerScore(secondPlayerScore)
                }
            }
        }
    }

    fun updateFirstPlayer(player: Player?) {
        _firstPlayer.value = player
    }

    fun updateFirstPlayerScore(score: GameScore) {
        _firstPlayerScore.value = score
    }

    fun updateSecondPlayer(player: Player?) {
        _secondPlayer.value = player
    }

    fun updateSecondPlayerScore(score: GameScore) {
        _secondPlayerScore.value = score
    }

    fun save() {
        viewModelScope.launch {
            val builder = GameHistoryRecord.Companion.Builder(cachedGameHistoryRecord)
            builder.firstPlayer = _firstPlayer.value
            builder.firstPlayerScore = _firstPlayerScore.value
            builder.secondPlayer = _secondPlayer.value
            builder.secondPlayerScore = _secondPlayerScore.value

            if (builder.isValid()) {
                gamesHistoryRepository.insertOrUpdate(builder.build())
            }
        }
    }
}