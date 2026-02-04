package com.demo.streamflix.ui.auth

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.demo.streamflix.R
import com.demo.streamflix.databinding.FragmentLoginBinding
import com.demo.streamflix.data.model.request.LoginRequest
import com.demo.streamflix.util.Extensions.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()

    private var isPasswordVisible = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        // Email input
        binding.etEmail.apply {
            setText("emanuel.801.r@gmail.com") // Pre-fill for demo
        }

        // Password visibility toggle
        binding.btnTogglePassword.setOnClickListener {
            togglePasswordVisibility()
        }

        // Remember me checkbox
        binding.cbRememberMe.isChecked = true

        // Login button
        binding.btnLogin.setOnClickListener {
            attemptLogin()
        }

        // Forgot password
        binding.tvForgotPassword.setOnClickListener {
            navigateToForgotPassword()
        }
    }

    private fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
        binding.etPassword.apply {
            inputType = if (isPasswordVisible) {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            setSelection(text.length)
        }

        // Update icon
        val iconRes = if (isPasswordVisible) {
            R.drawable.ic_visibility_off
        } else {
            R.drawable.ic_visibility
        }
        binding.btnTogglePassword.setImageResource(iconRes)
    }

    private fun attemptLogin() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val rememberMe = binding.cbRememberMe.isChecked

        // Basic validation
        if (email.isEmpty()) {
            binding.etEmail.error = "Correo electrónico requerido"
            return
        }

        if (password.isEmpty()) {
            binding.etPassword.error = "Contraseña requerida"
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Correo electrónico inválido"
            return
        }

        // Hide keyboard
        hideKeyboard()

        // Show loading
        binding.btnLogin.isEnabled = false
        binding.progressBar.visibility = View.VISIBLE

        // Call viewModel
        viewModel.login(LoginRequest(email, password, rememberMe))
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.loginState.collect { state ->
                when (state) {
                    is LoginViewModel.LoginState.Loading -> {
                        // Loading already shown
                    }
                    is LoginViewModel.LoginState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.btnLogin.isEnabled = true
                        
                        Toast.makeText(
                            requireContext(),
                            "Inicio de sesión exitoso",
                            Toast.LENGTH_SHORT
                        ).show()
                        
                        // Navigate to home
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                    is LoginViewModel.LoginState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.btnLogin.isEnabled = true
                        
                        Toast.makeText(
                            requireContext(),
                            state.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    else -> {
                        binding.progressBar.visibility = View.GONE
                        binding.btnLogin.isEnabled = true
                    }
                }
            }
        }
    }

    private fun navigateToForgotPassword() {
        // TODO: Implement forgot password navigation
        Toast.makeText(
            requireContext(),
            "Funcionalidad de recuperación de contraseña próximamente",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}