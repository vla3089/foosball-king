package com.vladds.foosballking.data.gameshistory

import com.vladds.foosballking.data.player.PlayersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface GamesHistoryRecordsRepository {
    fun getRecords(): Flow<List<GameHistoryRecord>>

    suspend fun getRecordById(id: GameHistoryRecordId): GameHistoryRecord?

    suspend fun insertOrUpdate(record: GameHistoryRecord)
}

class DefaultGamesHistoryRecordsRepository @Inject constructor(
    private val playersRepository: PlayersRepository,
    private val gameRecordHistoryDao: GameRecordHistoryDao,
) : GamesHistoryRecordsRepository {

    override fun getRecords(): Flow<List<GameHistoryRecord>> {
        return gameRecordHistoryDao.getRecords().map {
            it.mapNotNull { recordEntity -> loadGameFromGameEntity(recordEntity) }
        }
    }

    override suspend fun getRecordById(id: GameHistoryRecordId): GameHistoryRecord? {
        val game = gameRecordHistoryDao.getRecordById(id)
        return if (game == null) {
            // data corruption - log exception and handle corruption
            null
        } else {
            loadGameFromGameEntity(game)
        }
    }

    override suspend fun insertOrUpdate(record: GameHistoryRecord) {
        gameRecordHistoryDao.insertOrUpdate(record.toEntity())
    }

    private suspend fun loadGameFromGameEntity(recordHistoryEntity: GameRecordHistoryEntity): GameHistoryRecord? {
        val firstPlayer = playersRepository.getPlayerById(recordHistoryEntity.firstPlayerId)
        if (firstPlayer == null) {
            // data corruption - log exception and handle corruption
            return null
        }

        val secondPlayer = playersRepository.getPlayerById(recordHistoryEntity.secondPlayerId)
        if (secondPlayer == null) {
            // data corruption - log exception and handle corruption
            return null
        }

        return GameHistoryRecord(
            id = recordHistoryEntity.uid,
            firstPlayer = firstPlayer,
            firstPlayerScore = recordHistoryEntity.firstPlayerScore,
            secondPlayer = secondPlayer,
            secondPlayerScore = recordHistoryEntity.secondPlayerScore,
        )
    }

    private fun GameHistoryRecord.toEntity(): GameRecordHistoryEntity = GameRecordHistoryEntity(
        uid = this.id,
        firstPlayerId = this.firstPlayer.id,
        firstPlayerScore = this.firstPlayerScore,
        secondPlayerId = this.secondPlayer.id,
        secondPlayerScore = this.secondPlayerScore
    )
}
