package com.vladds.foosballking.presentation.ranking

import androidx.lifecycle.ViewModel
import com.vladds.foosballking.domain.PlayersRankingUseCase
import com.vladds.foosballking.domain.StreamableRankedPlayersUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlayerRankingViewModel @Inject constructor(
    private val rankingUseCase: StreamableRankedPlayersUseCase
) : ViewModel() {

    val rankingCategory: Flow<RankingCategory>
        get() = rankingUseCase.rankingCategory

    val rankedPlayers: Flow<List<PlayersRankingUseCase.RankedPlayer>>
        get() = rankingUseCase.rankedPlayers

    fun setRankingCategory(category: RankingCategory) {
        rankingUseCase.setRankingCategory(category)
    }
}
