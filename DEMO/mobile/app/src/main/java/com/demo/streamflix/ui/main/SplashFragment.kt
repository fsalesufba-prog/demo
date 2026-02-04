package com.demo.streamflix.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.demo.streamflix.R
import com.demo.streamflix.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Start validation after a short delay
        lifecycleScope.launch {
            delay(1500) // 1.5 seconds splash
            viewModel.validateMembership()
        }

        setupObservers()
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.validationState.collect { state ->
                when (state) {
                    is SplashViewModel.ValidationState.Valid -> {
                        navigateToHome()
                    }
                    is SplashViewModel.ValidationState.Invalid -> {
                        navigateToLogin()
                    }
                    is SplashViewModel.ValidationState.Expired -> {
                        showMembershipExpiredDialog()
                    }
                    SplashViewModel.ValidationState.Loading -> {
                        // Show loading state
                        binding.tvStatus.text = getString(R.string.validating_membership)
                    }
                }
            }
        }
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
    }

    private fun navigateToLogin() {
        findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
    }

    private fun showMembershipExpiredDialog() {
        // TODO: Implement membership expired dialog
        // For now, navigate to login
        navigateToLogin()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}