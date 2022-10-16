package com.vladds.foosballking.domain

import com.vladds.foosballking.data.gameshistory.GameHistoryRecord
import com.vladds.foosballking.data.player.Player
import javax.inject.Inject

class ByGamesPlayedRankingUseCase @Inject constructor() : PlayersRankingUseCase {
    override fun rankPlayers(records: List<GameHistoryRecord>): List<PlayersRankingUseCase.RankedPlayer> =
        records.fold(mutableMapOf<Player, Int>()) { acc, gameHistoryRecord ->
            incrementScore(acc, gameHistoryRecord.firstPlayer)
            incrementScore(acc, gameHistoryRecord.secondPlayer)
            acc
        }.map {
            PlayersRankingUseCase.RankedPlayer(it.key, it.value)
        }.sortedByDescending { it.score }

    private fun incrementScore(
        acc: MutableMap<Player, Int>,
        player: Player
    ) {
        acc.putIfAbsent(player, 0)
        acc[player] = acc[player]!! + 1
    }
}
