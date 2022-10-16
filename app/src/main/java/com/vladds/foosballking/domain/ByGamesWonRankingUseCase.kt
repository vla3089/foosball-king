package com.vladds.foosballking.domain

import com.vladds.foosballking.data.gameshistory.GameHistoryRecord
import com.vladds.foosballking.data.player.Player
import javax.inject.Inject

class ByGamesWonRankingUseCase @Inject constructor() : PlayersRankingUseCase {
    override fun rankPlayers(records: List<GameHistoryRecord>): List<PlayersRankingUseCase.RankedPlayer> =
        records.fold(mutableMapOf<Player, Int>()) { acc, gameHistoryRecord ->
            acc.putIfAbsent(gameHistoryRecord.firstPlayer, 0)
            acc.putIfAbsent(gameHistoryRecord.secondPlayer, 0)

            val playerWhoWon = gameHistoryRecord.playerWhoWon
            acc[playerWhoWon] = acc[playerWhoWon]!! + 1
            acc
        }.map {
            PlayersRankingUseCase.RankedPlayer(it.key, it.value)
        }.sortedByDescending { it.score }
}
