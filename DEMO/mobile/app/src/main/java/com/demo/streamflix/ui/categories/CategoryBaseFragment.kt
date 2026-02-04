package com.demo.streamflix.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.demo.streamflix.databinding.FragmentCategoryBinding
import com.demo.streamflix.ui.adapters.ChannelAdapter
import com.demo.streamflix.util.Extensions.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
abstract class CategoryBaseFragment : Fragment() {

    protected abstract fun getCategoryId(): Int
    protected abstract fun getCategoryName(): String
    
    private var _binding: FragmentCategoryBinding? = null
    protected val binding get() = _binding!!
    
    private val viewModel: CategoryViewModel by viewModels()
    private lateinit var channelAdapter: ChannelAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservers()
    }

    protected fun setupUI() {
        // Configurar toolbar
        binding.toolbar.title = getCategoryName()
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        // Configurar adapter de canais
        channelAdapter = ChannelAdapter { channel ->
            navigateToChannelDetail(channel)
        }
        
        binding.rvChannels.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = channelAdapter
        }

        // Configurar refresh layout
        binding.swipeRefresh.setOnRefreshListener {
            loadChannels(refresh = true)
        }
    }

    protected fun setupObservers() {
        lifecycleScope.launch {
            viewModel.channels.collect { channels ->
                channelAdapter.submitList(channels)
            }
        }

        lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                binding.swipeRefresh.isRefreshing = isLoading
                if (isLoading) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }

        lifecycleScope.launch {
            viewModel.error.collect { error ->
                error?.let {
                    showSnackbar(binding.root, it)
                }
            }
        }
    }

    protected fun loadChannels(refresh: Boolean = false) {
        viewModel.loadChannelsByCategory(getCategoryId(), refresh)
    }

    protected fun navigateToChannelDetail(channel: com.demo.streamflix.data.model.Channel) {
        // Usar safe args para passar o channel
        val action = when (getCategoryId()) {
            1 -> NacionalFragmentDirections.actionNacionalFragmentToChannelDetailFragment(channel)
            2 -> ActualidadFragmentDirections.actionActualidadFragmentToChannelDetailFragment(channel)
            3 -> InfantilFragmentDirections.actionInfantilFragmentToChannelDetailFragment(channel)
            4 -> RegionalFragmentDirections.actionRegionalFragmentToChannelDetailFragment(channel)
            else -> return
        }
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}