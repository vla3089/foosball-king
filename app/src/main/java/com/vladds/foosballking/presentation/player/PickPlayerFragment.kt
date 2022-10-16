package com.vladds.foosballking.presentation.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vladds.foosballking.data.player.Player
import com.vladds.foosballking.databinding.FragmentPickPlayerBinding
import com.vladds.foosballking.foosballKingComponent
import kotlinx.coroutines.launch

class PickPlayerFragment : Fragment(), PlayerAdapter.Callback {

    private var _binding: FragmentPickPlayerBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: PickPlayerViewModel by activityViewModels {
        requireContext().foosballKingComponent().viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPickPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PlayerAdapter(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(view.context)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.players.collect(adapter::submitList)
            }
        }
    }

    override fun onPlayerRowClicked(player: Player) {
        val key = requireNotNull(requireArguments().getString(PICKED_USER_KEY))
        viewModel.storePickedPlayer(key, player)
        findNavController().navigateUp()
    }
}

const val PICKED_USER_KEY = "PICKED_USER_KEY"