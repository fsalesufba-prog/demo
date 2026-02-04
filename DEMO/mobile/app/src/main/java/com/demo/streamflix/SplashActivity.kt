package com.demo.streamflix

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.demo.streamflix.databinding.ActivitySplashBinding
import com.demo.streamflix.util.SharedPrefs
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    private lateinit var binding: ActivitySplashBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
        
        // Start membership validation after a short delay
        android.os.Handler(mainLooper).postDelayed({
            validateMembership()
        }, 1000)
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_splash) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun validateMembership() {
        val isLoggedIn = sharedPrefs.getBoolean(Constants.PREF_IS_LOGGED_IN, false)
        val token = sharedPrefs.getString(Constants.PREF_AUTH_TOKEN, null)

        if (isLoggedIn && !token.isNullOrEmpty()) {
            // Check membership status via API
            checkMembershipStatus(token)
        } else {
            // Not logged in, navigate to login
            navController.navigate(R.id.action_splashFragment_to_loginFragment)
        }
    }

    private fun checkMembershipStatus(token: String) {
        // TODO: Implement actual API call to validate membership
        // For now, simulate API call
        val membershipValid = true // Replace with actual validation
        
        if (membershipValid) {
            navController.navigate(R.id.action_splashFragment_to_homeFragment)
        } else {
            navController.navigate(R.id.action_splashFragment_to_membershipExpiredFragment)
        }
    }
}