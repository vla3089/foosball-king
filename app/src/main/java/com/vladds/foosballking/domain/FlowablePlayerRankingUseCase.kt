package com.vladds.foosballking.domain

import com.vladds.foosballking.data.gameshistory.GamesHistoryRecordsRepository
import com.vladds.foosballking.presentation.ranking.RankingCategory
import com.vladds.foosballking.presentation.ranking.RankingCategoryQualifier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface StreamableRankedPlayersUseCase {

    fun getRankedPlayersForCategory(category: RankingCategory): Flow<List<PlayersRankingUseCase.RankedPlayer>>
}

class FlowableRankedPlayersUseCase @Inject constructor(
    private val gamesHistoryRecordsRepository: GamesHistoryRecordsRepository,
    @RankingCategoryQualifier(RankingCategory.ByGamesPlayed) private val byGamesPlayed: PlayersRankingUseCase,
    @RankingCategoryQualifier(RankingCategory.ByGamesWon) private val byGamesWon: PlayersRankingUseCase,
) : StreamableRankedPlayersUseCase {

    override fun getRankedPlayersForCategory(category: RankingCategory): Flow<List<PlayersRankingUseCase.RankedPlayer>> {
        return gamesHistoryRecordsRepository.getRecords().map { records ->
            when (category) {
                RankingCategory.ByGamesPlayed -> byGamesPlayed
                RankingCategory.ByGamesWon -> byGamesWon
            }.rankPlayers(records)
        }
    }
}
