package com.demo.streamflix.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.demo.streamflix.R
import com.demo.streamflix.databinding.FragmentHomeBinding
import com.demo.streamflix.ui.adapters.CategoryAdapter
import com.demo.streamflix.ui.adapters.ChannelAdapter
import com.demo.streamflix.util.Extensions.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var channelAdapter: ChannelAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservers()
        loadData()
    }

    private fun setupUI() {
        // Setup categories recycler view
        categoryAdapter = CategoryAdapter { category ->
            navigateToCategory(category.id)
        }
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = categoryAdapter
        }

        // Setup channels recycler view
        channelAdapter = ChannelAdapter { channel ->
            navigateToChannelDetail(channel)
        }
        binding.rvChannels.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = channelAdapter
        }

        // Setup refresh layout
        binding.swipeRefresh.setOnRefreshListener {
            loadData(refresh = true)
        }

        // Setup click listeners
        binding.ivProfile.setOnClickListener {
            navigateToProfile()
        }

        binding.ivSearch.setOnClickListener {
            navigateToSearch()
        }

        binding.tvSeeAllChannels.setOnClickListener {
            navigateToAllChannels()
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.categories.collect { categories ->
                categoryAdapter.submitList(categories)
            }
        }

        lifecycleScope.launch {
            viewModel.featuredChannels.collect { channels ->
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

    private fun loadData(refresh: Boolean = false) {
        viewModel.loadCategories(refresh)
        viewModel.loadFeaturedChannels(refresh)
    }

    private fun navigateToCategory(categoryId: Int) {
        val action = when (categoryId) {
            1 -> R.id.action_homeFragment_to_nacionalFragment
            2 -> R.id.action_homeFragment_to_actualidadFragment
            3 -> R.id.action_homeFragment_to_infantilFragment
            4 -> R.id.action_homeFragment_to_regionalFragment
            else -> return
        }
        findNavController().navigate(action)
    }

    private fun navigateToChannelDetail(channel: com.demo.streamflix.data.model.Channel) {
        val action = HomeFragmentDirections.actionHomeFragmentToChannelDetailFragment(channel)
        findNavController().navigate(action)
    }

    private fun navigateToProfile() {
        findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
    }

    private fun navigateToSearch() {
        findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
    }

    private fun navigateToAllChannels() {
        findNavController().navigate(R.id.action_homeFragment_to_allChannelsFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}