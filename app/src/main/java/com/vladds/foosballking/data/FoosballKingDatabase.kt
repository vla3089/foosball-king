package com.vladds.foosballking.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vladds.foosballking.data.gameshistory.GameRecordHistoryDao
import com.vladds.foosballking.data.gameshistory.GameRecordHistoryEntity
import com.vladds.foosballking.data.player.PlayerDao
import com.vladds.foosballking.data.player.PlayerEntity

@Database(
    entities = [PlayerEntity::class, GameRecordHistoryEntity::class], version = 1,
    exportSchema = false
)
abstract class FoosballKingDatabase : RoomDatabase() {

    abstract fun playerDao(): PlayerDao

    abstract fun gameRecordHistoryDao(): GameRecordHistoryDao
}
