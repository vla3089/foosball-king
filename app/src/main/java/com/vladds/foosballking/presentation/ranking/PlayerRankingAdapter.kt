package com.vladds.foosballking.presentation.ranking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vladds.foosballking.data.player.Player
import com.vladds.foosballking.databinding.RowPlayerRankingBinding
import com.vladds.foosballking.domain.PlayersRankingUseCase

class PlayerRankingAdapter(private val callback: Callback?) :
    ListAdapter<PlayersRankingUseCase.RankedPlayer, PlayerRankingViewHolder>(
        PlayerRankingDiffCallback()
    ) {

    interface Callback {
        fun onPlayerRowClicked(player: Player)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerRankingViewHolder {
        return PlayerRankingViewHolder(
            RowPlayerRankingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PlayerRankingViewHolder, position: Int) {
        holder.bind(getItem(position), callback)
    }
}

class PlayerRankingViewHolder(private val binding: RowPlayerRankingBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        rankingView: PlayersRankingUseCase.RankedPlayer,
        callback: PlayerRankingAdapter.Callback?
    ) {
        binding.playerName.text = rankingView.player.name
        binding.playerScore.text = rankingView.score.toString()
        itemView.setOnClickListener {
            callback?.onPlayerRowClicked(rankingView.player)
        }
    }
}
