package com.vladds.foosballking.presentation.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.vladds.foosballking.R
import com.vladds.foosballking.data.gameshistory.GameScore
import com.vladds.foosballking.databinding.FragmentAddGameHistoryRecordBinding
import com.vladds.foosballking.foosballKingComponent
import com.vladds.foosballking.presentation.SaveItemMenuProvider
import com.vladds.foosballking.presentation.player.PICKED_USER_KEY
import com.vladds.foosballking.presentation.player.PickPlayerViewModel
import com.vladds.foosballking.widgets.GameScorePicker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AddGameHistoryRecordFragment : Fragment() {

    private var _binding: FragmentAddGameHistoryRecordBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: AddGameHistoryRecordViewModel by viewModels {
        requireContext().foosballKingComponent().viewModelFactory
    }

    private val pickPlayerViewModel: PickPlayerViewModel by activityViewModels {
        requireContext().foosballKingComponent().viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddGameHistoryRecordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pickFirstPlayer.setOnClickListener(
            createPickPlayerListener(PICK_FIRST_USER_KEY)
        )

        binding.pickSecondPlayer.setOnClickListener(
            createPickPlayerListener(PICK_SECOND_USER_KEY)
        )

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.firstPlayer.collect {
                        binding.pickFirstPlayer.text = it?.name ?: getString(R.string.pick_user)
                    }
                }

                launch {
                    viewModel.secondPlayer.collect {
                        binding.pickSecondPlayer.text = it?.name ?: getString(R.string.pick_user)
                    }
                }
            }
        }

        bingScorePickerBidirectionally(
            binding.firstPlayerScorePicker,
            viewModel.firstPlayerScore,
            viewModel::updateFirstPlayerScore
        )

        bingScorePickerBidirectionally(
            binding.secondPlayerScorePicker,
            viewModel.secondPlayerScore,
            viewModel::updateSecondPlayerScore
        )

        val historyRecordId = arguments?.getLong(GAME_HISTORY_RECORD_ID_KEY, -1) ?: -1
        viewModel.loadGame(historyRecordId)

        requireActivity().addMenuProvider(
            SaveItemMenuProvider {
                viewModel.save()
            },
            viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )
    }

    override fun onResume() {
        super.onResume()
        val updatedFirstPlayer = pickPlayerViewModel.getByKeyAndRemove(PICK_FIRST_USER_KEY)
        if (updatedFirstPlayer != null) {
            viewModel.updateFirstPlayer(updatedFirstPlayer)
        }

        val updatedSecondPlayer = pickPlayerViewModel.getByKeyAndRemove(PICK_SECOND_USER_KEY)
        if (updatedSecondPlayer != null) {
            viewModel.updateSecondPlayer(updatedSecondPlayer)
        }
    }

    private fun bingScorePickerBidirectionally(
        scopePicker: GameScorePicker,
        data: Flow<GameScore?>,
        updater: (GameScore) -> Unit
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    data.collect { gameScore ->
                        if (scopePicker.score != gameScore) {
                            scopePicker.score = gameScore
                        }
                    }
                }
            }
        }
        scopePicker.callback = object : GameScorePicker.ScoreChangedCallback {
            override fun onScoreChanged(score: GameScore) {
                updater(score)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createPickPlayerListener(key: String): View.OnClickListener =
        Navigation.createNavigateOnClickListener(
            R.id.pickPlayerFragment,
            bundleOf(
                PICKED_USER_KEY to key
            )
        )
}

const val GAME_HISTORY_RECORD_ID_KEY = "GAME_HISTORY_RECORD_ID_KEY"
const val PICK_FIRST_USER_KEY = "PICK_FIRST_USER_KEY"
const val PICK_SECOND_USER_KEY = "PICK_SECOND_USER_KEY"
