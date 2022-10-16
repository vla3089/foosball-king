package com.vladds.foosballking.presentation.history

import androidx.recyclerview.widget.DiffUtil
import com.vladds.foosballking.data.gameshistory.GameHistoryRecord

class GameHistoryRecordDiffCallback : DiffUtil.ItemCallback<GameHistoryRecord>() {
    override fun areItemsTheSame(oldItem: GameHistoryRecord, newItem: GameHistoryRecord): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: GameHistoryRecord,
        newItem: GameHistoryRecord
    ): Boolean = oldItem == newItem
}
