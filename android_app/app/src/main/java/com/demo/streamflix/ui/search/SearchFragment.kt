package com.demo.streamflix.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.streamflix.R
import com.demo.streamflix.databinding.FragmentSearchBinding
import com.demo.streamflix.ui.adapters.SearchAdapter
import com.demo.streamflix.util.Extensions.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()

    private lateinit var searchAdapter: SearchAdapter
    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        // Setup back button
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        // Setup search adapter
        searchAdapter = SearchAdapter { channel ->
            navigateToChannelDetail(channel)
        }
        binding.rvSearchResults.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchAdapter
        }

        // Setup search functionality
        binding.etSearch.apply {
            setOnEditorActionListener { _, _, _ ->
                performSearch()
                hideKeyboard()
                true
            }

            doAfterTextChanged { editable ->
                editable?.let {
                    if (it.length >= 2) {
                        debounceSearch(it.toString())
                    } else {
                        clearSearchResults()
                    }
                }
            }
        }

        // Setup clear button
        binding.ivClear.setOnClickListener {
            binding.etSearch.text?.clear()
            clearSearchResults()
        }
    }

    private fun debounceSearch(query: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            delay(500) // 500ms debounce
            viewModel.searchChannels(query)
        }
    }

    private fun performSearch() {
        val query = binding.etSearch.text.toString().trim()
        if (query.isNotEmpty()) {
            hideKeyboard()
            viewModel.searchChannels(query)
        }
    }

    private fun clearSearchResults() {
        searchAdapter.submitList(emptyList())
        binding.tvNoResults.visibility = View.VISIBLE
        binding.rvSearchResults.visibility = View.GONE
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.searchResults.collect { channels ->
                if (channels.isNotEmpty()) {
                    searchAdapter.submitList(channels)
                    binding.tvNoResults.visibility = View.GONE
                    binding.rvSearchResults.visibility = View.VISIBLE
                } else {
                    clearSearchResults()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
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
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun navigateToChannelDetail(channel: com.demo.streamflix.data.model.Channel) {
        val action = SearchFragmentDirections.actionSearchFragmentToChannelDetailFragment(channel)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchJob?.cancel()
        _binding = null
    }
}