package com.vladds.foosballking.data.player

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "player"
)
data class PlayerEntity(
    @PrimaryKey(autoGenerate = true) val uid: Long? = null,
    @ColumnInfo(name = "name") val name: String,
)
