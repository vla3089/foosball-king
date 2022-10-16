package com.vladds.foosballking.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.google.android.material.button.MaterialButtonToggleGroup
import com.vladds.foosballking.R
import com.vladds.foosballking.data.gameshistory.GameScore
import com.vladds.foosballking.databinding.GameScorePickerBinding

class GameScorePicker(
    context: Context,
    attributeSet: AttributeSet
) : MaterialButtonToggleGroup(context, attributeSet) {

    init {
        GameScorePickerBinding.inflate(LayoutInflater.from(context), this)
    }

    var callback: ScoreChangedCallback? = null

    var score: GameScore?
        get() = mapIdToScore(checkedButtonId)
        set(value) {
            val id = mapGameScoreToId(value)
            if (id != null) {
                check(id)
            } else {
                clearChecked()
            }
        }

    init {
        addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                val score = mapIdToScore(checkedId)
                if (score != null) {
                    callback?.onScoreChanged(score)
                }
            }
        }
    }

    private fun mapIdToScore(id: Int): GameScore? {
        return when (id) {
            R.id.button0 -> 0
            R.id.button1 -> 1
            R.id.button2 -> 2
            R.id.button3 -> 3
            R.id.button4 -> 4
            R.id.button5 -> 5
            R.id.button6 -> 6
            R.id.button7 -> 7
            else -> null
        }
    }

    private fun mapGameScoreToId(score: GameScore?): Int? {
        val zero: Byte = 0
        val one: Byte = 1
        val two: Byte = 2
        val three: Byte = 3
        val four: Byte = 4
        val five: Byte = 5
        val six: Byte = 6
        val seven: Byte = 7
        return when (score) {
            zero -> R.id.button0
            one -> R.id.button1
            two -> R.id.button2
            three -> R.id.button3
            four -> R.id.button4
            five -> R.id.button5
            six -> R.id.button6
            seven -> R.id.button7
            else -> null
        }
    }

    interface ScoreChangedCallback {
        fun onScoreChanged(score: GameScore)
    }
}
