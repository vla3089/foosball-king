package com.vladds.foosballking.presentation.player

import androidx.lifecycle.ViewModel
import com.vladds.foosballking.data.player.Player
import com.vladds.foosballking.data.player.PlayersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PickPlayerViewModel @Inject constructor(
    playersRepository: PlayersRepository
) : ViewModel() {

    val players: Flow<List<Player>> = playersRepository.getPlayers()

    private val pickedPlayers = mutableMapOf<String, Player>()

    fun storePickedPlayer(key: String, player: Player) {
        pickedPlayers[key] = player
    }

    fun getByKeyAndRemove(key: String): Player? {
        val player = pickedPlayers[key]
        pickedPlayers.remove(key)
        return player
    }
}
