package com.vladds.foosballking.presentation.player

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vladds.foosballking.data.player.Player
import com.vladds.foosballking.databinding.RowPlayerBinding

class PlayerAdapter(private val callback: Callback) :
    ListAdapter<Player, PlayerViewHolder>(PlayerDiffCallback()) {

    interface Callback {
        fun onPlayerRowClicked(player: Player)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder(
            RowPlayerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind(getItem(position), callback)
    }
}

class PlayerViewHolder(private val binding: RowPlayerBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(player: Player, callback: PlayerAdapter.Callback) {
        binding.playerName.text = player.name
        itemView.setOnClickListener {
            callback.onPlayerRowClicked(player)
        }
    }
}
