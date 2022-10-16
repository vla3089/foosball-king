package com.vladds.foosballking.presentation.player

import androidx.recyclerview.widget.DiffUtil
import com.vladds.foosballking.data.player.Player

class PlayerDiffCallback : DiffUtil.ItemCallback<Player>() {
    override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean {
        return oldItem.name == newItem.name
    }
}
