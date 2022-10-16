package com.vladds.foosballking.data.gameshistory

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GameRecordHistoryDao {

    @Query("SELECT * FROM game_record_history ORDER BY uid ASC")
    fun getRecords(): Flow<List<GameRecordHistoryEntity>>

    @Query("SELECT * FROM game_record_history WHERE uid LIKE :id LIMIT 1")
    suspend fun getRecordById(id: Long): GameRecordHistoryEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(player: GameRecordHistoryEntity): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(player: GameRecordHistoryEntity): Int

    @Transaction
    suspend fun insertOrUpdate(model: GameRecordHistoryEntity) {
        val id = insert(model)
        if (id == -1L) {
            update(model)
        }
    }
}
