package com.vladds.foosballking.presentation.ranking

import androidx.recyclerview.widget.DiffUtil
import com.vladds.foosballking.domain.PlayersRankingUseCase

class PlayerRankingDiffCallback : DiffUtil.ItemCallback<PlayersRankingUseCase.RankedPlayer>() {
    override fun areItemsTheSame(
        oldItem: PlayersRankingUseCase.RankedPlayer,
        newItem: PlayersRankingUseCase.RankedPlayer
    ): Boolean = oldItem.player.id == newItem.player.id

    override fun areContentsTheSame(
        oldItem: PlayersRankingUseCase.RankedPlayer,
        newItem: PlayersRankingUseCase.RankedPlayer
    ): Boolean = oldItem.score == newItem.score && oldItem.player.name == newItem.player.name
}
