package com.vladds.foosballking.data.gameshistory

import androidx.room.*
import com.vladds.foosballking.data.player.PlayerEntity

@Entity(
    tableName = "game_record_history",
    indices = [Index("first_player_id"), Index("second_player_id")],
    foreignKeys = [ForeignKey(
        entity = PlayerEntity::class,
        childColumns = ["first_player_id"],
        parentColumns = ["uid"],
        onDelete = ForeignKey.NO_ACTION
    ), ForeignKey(
        entity = PlayerEntity::class,
        childColumns = ["second_player_id"],
        parentColumns = ["uid"],
        onDelete = ForeignKey.NO_ACTION
    )],
)
data class GameRecordHistoryEntity(
    @PrimaryKey(autoGenerate = true) val uid: Long? = null,
    @ColumnInfo(name = "first_player_id") val firstPlayerId: Long,
    @ColumnInfo(name = "first_player_score") val firstPlayerScore: Byte,
    @ColumnInfo(name = "second_player_id") val secondPlayerId: Long,
    @ColumnInfo(name = "second_player_score") val secondPlayerScore: Byte
)
