package com.vladds.foosballking.presentation.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vladds.foosballking.R
import com.vladds.foosballking.data.gameshistory.GameHistoryRecord
import com.vladds.foosballking.databinding.FragmentGamesHistoryBinding
import com.vladds.foosballking.foosballKingComponent
import com.vladds.foosballking.presentation.AddItemMenuProvider
import kotlinx.coroutines.launch

class GamesHistoryRecordsFragment : Fragment(), GameHistoryRecordAdapter.Callback {

    private var _binding: FragmentGamesHistoryBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: GameHistoryViewModel by viewModels {
        requireContext().foosballKingComponent().viewModelFactory
    }

    private val adapter = GameHistoryRecordAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGamesHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(view.context)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.historyRecords.collect(adapter::submitList)
                }
            }
        }

        requireActivity().addMenuProvider(
            AddItemMenuProvider {
                goToAddGameHistoryRecordFragment(Bundle())
            },
            viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onGameHistoryRecordRowClicked(item: GameHistoryRecord) {
        goToAddGameHistoryRecordFragment(bundleOf(GAME_HISTORY_RECORD_ID_KEY to item.id))
    }

    private fun goToAddGameHistoryRecordFragment(args: Bundle) {
        findNavController().navigate(R.id.addGameHistoryRecordFragment, args)
    }
}
