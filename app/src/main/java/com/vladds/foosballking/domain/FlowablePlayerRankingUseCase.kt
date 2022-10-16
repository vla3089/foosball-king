package com.vladds.foosballking.domain

import com.vladds.foosballking.data.gameshistory.GamesHistoryRecordsRepository
import com.vladds.foosballking.presentation.ranking.RankingCategory
import com.vladds.foosballking.presentation.ranking.RankingCategoryQualifier
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.rx2.asFlow
import kotlinx.coroutines.rx2.asFlowable
import javax.inject.Inject

interface StreamableRankedPlayersUseCase {

    val rankingCategory: Flow<RankingCategory>

    val rankedPlayers: Flow<List<PlayersRankingUseCase.RankedPlayer>>

    fun setRankingCategory(category: RankingCategory)
}

class FlowableRankedPlayersUseCase @Inject constructor(
    gamesHistoryRecordsRepository: GamesHistoryRecordsRepository,
    @RankingCategoryQualifier(RankingCategory.ByGamesPlayed) private val byGamesPlayed: PlayersRankingUseCase,
    @RankingCategoryQualifier(RankingCategory.ByGamesWon) private val byGamesWon: PlayersRankingUseCase,
) : StreamableRankedPlayersUseCase {

    private val _rankingCategory = BehaviorSubject.createDefault(RankingCategory.ByGamesPlayed)
    override val rankingCategory: Flow<RankingCategory>
        get() = _rankingCategory.asFlow()

    override val rankedPlayers: Flow<List<PlayersRankingUseCase.RankedPlayer>> =
        Flowable.combineLatest(
            gamesHistoryRecordsRepository.getRecords().onStart {
                emit(emptyList())
            }
                .asFlowable(Dispatchers.IO)
                .observeOn(Schedulers.computation()),
            _rankingCategory.toFlowable(BackpressureStrategy.LATEST)
                .observeOn(Schedulers.computation())
                .map {
                    when (it) {
                        RankingCategory.ByGamesPlayed -> byGamesPlayed
                        RankingCategory.ByGamesWon -> byGamesWon
                    }
                }
        ) { records, rankingUseCase ->
            rankingUseCase.rankPlayers(records)
        }
            .asFlow()

    override fun setRankingCategory(category: RankingCategory) {
        if (_rankingCategory.value != category) {
            _rankingCategory.onNext(category)
        }
    }
}
