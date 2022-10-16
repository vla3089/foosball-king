package com.vladds.foosballking.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vladds.foosballking.core.presentation.FoosballViewModelFactory
import com.vladds.foosballking.core.presentation.ViewModelKey
import com.vladds.foosballking.data.gameshistory.DefaultGamesHistoryRecordsRepository
import com.vladds.foosballking.data.gameshistory.GamesHistoryRecordsRepository
import com.vladds.foosballking.data.player.DefaultPlayersRepository
import com.vladds.foosballking.data.player.PlayersRepository
import com.vladds.foosballking.domain.*
import com.vladds.foosballking.presentation.history.AddGameHistoryRecordViewModel
import com.vladds.foosballking.presentation.history.GameHistoryViewModel
import com.vladds.foosballking.presentation.player.PickPlayerViewModel
import com.vladds.foosballking.presentation.ranking.PlayerRankingViewModel
import com.vladds.foosballking.presentation.ranking.RankingCategory
import com.vladds.foosballking.presentation.ranking.RankingCategoryQualifier
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface FoosballKingModule {
    @Binds
    fun bindGamesHistoryRepository(value: DefaultGamesHistoryRecordsRepository): GamesHistoryRecordsRepository

    @Binds
    fun bindPlayersRepository(value: DefaultPlayersRepository): PlayersRepository

    @Binds
    fun bindRatingByGamesPlayedUseCase(value: ByGamesPlayedRankingUseCase): PlayersRankingUseCase

    @Binds
    fun bindFlowableRankedPlayersUseCase(value: FlowableRankedPlayersUseCase): StreamableRankedPlayersUseCase

    @Binds
    @IntoMap
    @ViewModelKey(GameHistoryViewModel::class)
    fun bindGamesHistoryViewModel(value: GameHistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddGameHistoryRecordViewModel::class)
    fun bindAddGameHistoryRecordViewModel(value: AddGameHistoryRecordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PickPlayerViewModel::class)
    fun bindPickPlayerViewModel(value: PickPlayerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PlayerRankingViewModel::class)
    fun bindPlayerRankingViewModel(value: PlayerRankingViewModel): ViewModel

    @Binds
    fun bindViewModelFactory(value: FoosballViewModelFactory): ViewModelProvider.Factory

    @Binds
    @RankingCategoryQualifier(RankingCategory.ByGamesPlayed)
    fun bindByGamesFilter(value: ByGamesPlayedRankingUseCase): PlayersRankingUseCase

    @Binds
    @RankingCategoryQualifier(RankingCategory.ByGamesWon)
    fun bindRatingByGamesWonUseCase(value: ByGamesWonRankingUseCase): PlayersRankingUseCase
}
