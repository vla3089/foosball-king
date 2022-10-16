package com.vladds.foosballking.data.player

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {
    @Query("SELECT * FROM player ORDER BY uid ASC")
    fun getPlayers(): Flow<List<PlayerEntity>>

    @Query("SELECT * FROM player WHERE uid LIKE :id LIMIT 1")
    suspend fun getPlayerById(id: Long): PlayerEntity?

    @Query("SELECT * FROM player WHERE name LIKE :name LIMIT 1")
    suspend fun getPlayerByName(name: String): PlayerEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPlayer(player: PlayerEntity): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updatePlayer(player: PlayerEntity): Int

    @Transaction
    suspend fun insertOrUpdate(model: PlayerEntity) {
        val id = insertPlayer(model)
        if (id == -1L) {
            updatePlayer(model)
        }
    }
}