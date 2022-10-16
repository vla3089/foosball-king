package com.vladds.foosballking.domain

import com.vladds.foosballking.data.gameshistory.GameHistoryRecord
import com.vladds.foosballking.data.player.Player

val john = Player(1, "John")
val dave = Player(2, "Dave")
val bruce = Player(3, "Bruce")

val historyRecordsStub = listOf(
    GameHistoryRecord(
        null,
        dave, 5,
        john, 1
    ),
    GameHistoryRecord(
        null,
        dave, 1,
        john, 5
    ),
    GameHistoryRecord(
        null,
        dave, 1,
        john, 5
    ),
    GameHistoryRecord(
        null,
        dave, 1,
        bruce, 5
    ),
    GameHistoryRecord(
        null,
        dave, 1,
        bruce, 5
    ),
    GameHistoryRecord(
        null,
        dave, 1,
        bruce, 5
    )
)
