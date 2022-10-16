package com.vladds.foosballking.presentation.history

import androidx.lifecycle.ViewModel
import com.vladds.foosballking.data.gameshistory.GameHistoryRecord
import com.vladds.foosballking.data.gameshistory.GamesHistoryRecordsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GameHistoryViewModel @Inject constructor(gamesHistoryRepository: GamesHistoryRecordsRepository) :
    ViewModel() {

    val historyRecords: Flow<List<GameHistoryRecord>> = gamesHistoryRepository.getRecords()
}
