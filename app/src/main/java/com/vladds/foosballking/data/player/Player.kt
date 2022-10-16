package com.vladds.foosballking.data.player

data class Player(val id: PlayerId, val name: PlayerName)

typealias PlayerId = Long

typealias PlayerName = String
