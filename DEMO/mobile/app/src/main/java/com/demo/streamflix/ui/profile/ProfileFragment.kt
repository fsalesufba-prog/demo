package com.demo.streamflix.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.demo.streamflix.R
import com.demo.streamflix.databinding.FragmentProfileBinding
import com.demo.streamflix.util.Extensions.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservers()
        loadUserData()
    }

    private fun setupUI() {
        // Setup back button
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        // Setup buttons
        binding.btnUpdateData.setOnClickListener {
            navigateToEditProfile()
        }

        binding.btnChangePassword.setOnClickListener {
            navigateToChangePassword()
        }

        binding.btnLogout.setOnClickListener {
            logout()
        }

        // Setup current channel (for TV app simulation)
        binding.tvCurrentChannel.text = "001 - Willax HD"
        binding.ivChannelLogo.setImageResource(R.drawable.ic_willax)
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.userProfile.collect { user ->
                user?.let {
                    binding.tvUserName.text = it.fullName
                    binding.tvUserEmail.text = it.email
                    binding.tvUserPhone.text = it.phone ?: "N/A"
                }
            }
        }

        lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }

        lifecycleScope.launch {
            viewModel.error.collect { error ->
                error?.let {
                    showToast(it)
                }
            }
        }
    }

    private fun loadUserData() {
        viewModel.loadUserProfile()
    }

    private fun navigateToEditProfile() {
        findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
    }

    private fun navigateToChangePassword() {
        findNavController().navigate(R.id.action_profileFragment_to_changePasswordFragment)
    }

    private fun logout() {
        viewModel.logout()
        findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}