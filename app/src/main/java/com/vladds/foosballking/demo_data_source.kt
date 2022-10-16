package com.vladds.foosballking

import com.vladds.foosballking.data.gameshistory.GameRecordHistoryEntity
import com.vladds.foosballking.data.player.PlayerEntity

private val amos = PlayerEntity(1, "Amos")
private val diego = PlayerEntity(2, "Diego")
private val joel = PlayerEntity(3, "Joel")
private val tim = PlayerEntity(4, "Tim")
val players = listOf(amos, diego, joel, tim)

private var recordId = 0L
val demoSource = listOf(
    GameRecordHistoryEntity(
        uid = recordId++,
        amos.uid!!, 4,
        diego.uid!!, 5,
    ),
    GameRecordHistoryEntity(
        uid = recordId++,
        amos.uid, 1,
        diego.uid, 5,
    ),
    GameRecordHistoryEntity(
        uid = recordId++,
        amos.uid, 2,
        diego.uid, 5,
    ),
    GameRecordHistoryEntity(
        uid = recordId++,
        amos.uid, 0,
        diego.uid, 5,
    ),
    GameRecordHistoryEntity(
        uid = recordId++,
        amos.uid, 6,
        diego.uid, 5,
    ),
    GameRecordHistoryEntity(
        uid = recordId++,
        amos.uid, 5,
        diego.uid, 2,
    ),
    GameRecordHistoryEntity(
        uid = recordId++,
        amos.uid, 4,
        diego.uid, 0,
    ),
    GameRecordHistoryEntity(
        uid = recordId++,
        joel.uid!!, 4,
        diego.uid, 5,
    ),
    GameRecordHistoryEntity(
        uid = recordId++,
        tim.uid!!, 4,
        amos.uid, 5,
    ),
    GameRecordHistoryEntity(
        uid = recordId++,
        tim.uid, 5,
        amos.uid, 2,
    ),
    GameRecordHistoryEntity(
        uid = recordId++,
        amos.uid, 3,
        tim.uid, 5,
    ),
    GameRecordHistoryEntity(
        uid = recordId++,
        amos.uid, 5,
        tim.uid, 3,
    ),
    GameRecordHistoryEntity(
        uid = recordId++,
        amos.uid, 5,
        joel.uid, 4,
    ),
    GameRecordHistoryEntity(
        uid = recordId++,
        joel.uid, 5,
        tim.uid, 2,
    ),
)
