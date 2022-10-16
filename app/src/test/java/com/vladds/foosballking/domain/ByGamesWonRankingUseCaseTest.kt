package com.vladds.foosballking.domain

import org.junit.Assert.assertEquals
import org.junit.Test

class ByGamesWonRankingUseCaseTest {
    private val sut = ByGamesWonRankingUseCase()

    @Test
    fun `should sort by number of games won`() {
        assertEquals(expectedList1, sut.rankPlayers(historyRecordsStub))
    }
}

private val expectedList1 = listOf(
    PlayersRankingUseCase.RankedPlayer(bruce, 3),
    PlayersRankingUseCase.RankedPlayer(john, 2),
    PlayersRankingUseCase.RankedPlayer(dave, 1)
)
