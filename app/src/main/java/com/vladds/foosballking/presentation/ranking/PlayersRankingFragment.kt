package com.vladds.foosballking.presentation.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.vladds.foosballking.R
import com.vladds.foosballking.databinding.FragmentPlayersRankingBinding
import com.vladds.foosballking.foosballKingComponent
import kotlinx.coroutines.launch

class PlayersRankingFragment : Fragment() {
    private var _binding: FragmentPlayersRankingBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: PlayerRankingViewModel by viewModels {
        requireContext().foosballKingComponent().viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayersRankingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PlayerRankingAdapter(null)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(view.context)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.rankedPlayers.collect(adapter::submitList)
                }
                launch {
                    viewModel.rankingCategory.collect { rankingCategory: RankingCategory ->
                        val checkId = rankingCategoryToCheckedId(rankingCategory)
                        if (checkId != binding.rankingCategory.checkedButtonId) {
                            binding.rankingCategory.check(checkId)
                        }
                    }
                }
            }
        }

        binding.rankingCategory.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                val category = checkedIdToRankingCategory(checkedId)
                if (category != null) {
                    viewModel.setRankingCategory(category)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun rankingCategoryToCheckedId(category: RankingCategory): Int =
        when (category) {
            RankingCategory.ByGamesPlayed -> R.id.by_games_played
            RankingCategory.ByGamesWon -> R.id.by_victories
        }


    private fun checkedIdToRankingCategory(checkedId: Int): RankingCategory? =
        when (checkedId) {
            R.id.by_games_played -> RankingCategory.ByGamesPlayed
            R.id.by_victories -> RankingCategory.ByGamesWon
            else -> null
        }
}
