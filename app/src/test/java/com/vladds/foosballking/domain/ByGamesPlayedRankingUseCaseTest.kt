package com.vladds.foosballking.domain

import org.junit.Assert.assertEquals
import org.junit.Test

class ByGamesPlayedRankingUseCaseTest {
    private val sut = ByGamesPlayedRankingUseCase()

    @Test
    fun `should sort by number of games played`() {
        assertEquals(expectedList1, sut.rankPlayers(historyRecordsStub))
    }
}

private val expectedList1 = listOf(
    PlayersRankingUseCase.RankedPlayer(dave, 6),
    PlayersRankingUseCase.RankedPlayer(john, 3),
    PlayersRankingUseCase.RankedPlayer(bruce, 3)
)
