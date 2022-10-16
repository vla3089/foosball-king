package com.vladds.foosballking.domain

import com.vladds.foosballking.data.gameshistory.GameHistoryRecord
import com.vladds.foosballking.data.player.Player

interface PlayersRankingUseCase {
    fun rankPlayers(records: List<GameHistoryRecord>): List<RankedPlayer>

    data class RankedPlayer(
        val player: Player,
        val score: Int
    )
}
