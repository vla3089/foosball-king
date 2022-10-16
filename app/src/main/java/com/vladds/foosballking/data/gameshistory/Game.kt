package com.vladds.foosballking.data.gameshistory

import com.vladds.foosballking.data.player.Player
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

data class GameHistoryRecord(
    val id: GameHistoryRecordId?,
    val firstPlayer: Player,
    val firstPlayerScore: GameScore,
    val secondPlayer: Player,
    val secondPlayerScore: GameScore
) {
    val playerWhoWon: Player
        get() = if (firstPlayerScore > secondPlayerScore) {
            firstPlayer
        } else {
            secondPlayer
        }

    companion object {
        class Builder(from: GameHistoryRecord? = null) {
            private val id: GameHistoryRecordId? = from?.id
            var firstPlayer: Player? = from?.firstPlayer
            var firstPlayerScore: GameScore? = from?.firstPlayerScore
            var secondPlayer: Player? = from?.secondPlayer
            var secondPlayerScore: GameScore? = from?.secondPlayerScore

            @Throws(IllegalStateException::class)
            fun build(): GameHistoryRecord {
                val firstPlayer = firstPlayer
                val firstPlayerScore = firstPlayerScore
                val secondPlayer = secondPlayer
                val secondPlayerScore = secondPlayerScore
                if (isValid(firstPlayer, firstPlayerScore, secondPlayer, secondPlayerScore)) {
                    return GameHistoryRecord(
                        id = this.id,
                        firstPlayer = firstPlayer,
                        firstPlayerScore = firstPlayerScore,
                        secondPlayer = secondPlayer,
                        secondPlayerScore = secondPlayerScore,
                    )
                }
                throw IllegalStateException()
            }

            fun isValid(): Boolean =
                isValid(firstPlayer, firstPlayerScore, secondPlayer, secondPlayerScore)

            @OptIn(ExperimentalContracts::class)
            private fun isValid(
                firstPlayer: Player?,
                firstPlayerScore: GameScore?,
                secondPlayer: Player?,
                secondPlayerScore: GameScore?
            ): Boolean {
                contract {
                    returns(true) implies (firstPlayer != null)
                    returns(true) implies (firstPlayerScore != null)
                    returns(true) implies (secondPlayer != null)
                    returns(true) implies (secondPlayerScore != null)
                }
                return firstPlayer != null &&
                        firstPlayerScore != null &&
                        secondPlayer != null &&
                        secondPlayerScore != null &&
                        firstPlayer != secondPlayer
            }
        }
    }
}

fun GameHistoryRecord.formatAsSingleString(): String =
    "%s, %d, %s, %d".format(
        this.firstPlayer.name,
        this.firstPlayerScore,
        this.secondPlayer.name,
        this.secondPlayerScore
    )

typealias GameHistoryRecordId = Long

typealias GameScore = Byte
