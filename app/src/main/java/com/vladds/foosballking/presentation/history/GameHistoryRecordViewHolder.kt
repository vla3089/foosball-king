package com.vladds.foosballking.presentation.history

import androidx.recyclerview.widget.RecyclerView
import com.vladds.foosballking.data.gameshistory.GameHistoryRecord
import com.vladds.foosballking.data.gameshistory.formatAsSingleString
import com.vladds.foosballking.databinding.RowGameHistoryBinding

class GameHistoryRecordViewHolder(
    private val binding: RowGameHistoryBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: GameHistoryRecord, callback: GameHistoryRecordAdapter.Callback) {
        binding.record.text = item.formatAsSingleString()
        itemView.setOnClickListener {
            callback.onGameHistoryRecordRowClicked(item)
        }
    }
}