package com.vladds.foosballking.presentation.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.vladds.foosballking.data.gameshistory.GameHistoryRecord
import com.vladds.foosballking.databinding.RowGameHistoryBinding

class GameHistoryRecordAdapter(private val callback: Callback) :
    ListAdapter<GameHistoryRecord, GameHistoryRecordViewHolder>(
        GameHistoryRecordDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHistoryRecordViewHolder {
        return GameHistoryRecordViewHolder(
            RowGameHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: GameHistoryRecordViewHolder, position: Int) {
        holder.bind(getItem(position), callback)
    }

    interface Callback {
        fun onGameHistoryRecordRowClicked(item: GameHistoryRecord)
    }
}
