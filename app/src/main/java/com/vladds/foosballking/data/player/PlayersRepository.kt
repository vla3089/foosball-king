package com.vladds.foosballking.data.player

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface PlayersRepository {
    fun getPlayers(): Flow<List<Player>>

    suspend fun getPlayerById(id: PlayerId): Player?
}

class DefaultPlayersRepository @Inject constructor(
    private val playerDao: PlayerDao
) : PlayersRepository {

    override fun getPlayers(): Flow<List<Player>> =
        playerDao.getPlayers().map {
            it.mapNotNull { playerEntity -> playerEntity.toPlayerOrNull() }
        }

    override suspend fun getPlayerById(id: PlayerId): Player? =
        playerDao.getPlayerById(id).toPlayerOrNull()

    private fun PlayerEntity?.toPlayerOrNull(): Player? = if (this != null && this.uid != null) {
        Player(this.uid, this.name)
    } else {
        null
    }
}
