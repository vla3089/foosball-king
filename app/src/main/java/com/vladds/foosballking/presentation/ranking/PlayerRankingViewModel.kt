package com.vladds.foosballking.presentation.ranking

import androidx.lifecycle.ViewModel
import com.vladds.foosballking.domain.PlayersRankingUseCase
import com.vladds.foosballking.domain.StreamableRankedPlayersUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlayerRankingViewModel @Inject constructor(
    private val rankingUseCase: StreamableRankedPlayersUseCase,
) : ViewModel() {

    fun getRankedPlayersForCategory(category: RankingCategory): Flow<List<PlayersRankingUseCase.RankedPlayer>> {
        return rankingUseCase.getRankedPlayersForCategory(category)
    }
}
