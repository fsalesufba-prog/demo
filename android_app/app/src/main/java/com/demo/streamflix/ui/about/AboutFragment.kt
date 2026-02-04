package com.demo.streamflix.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.demo.streamflix.R
import com.demo.streamflix.databinding.FragmentAboutBinding
import com.demo.streamflix.util.Extensions.showToast

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        // Setup back button
        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        // Set company info (redacted from image)
        binding.tvCompanyName.text = "(Redacted)"
        binding.tvCompanyEmail.text = "(Redacted)"
        binding.tvContactNumber.text = "+51 937 466 317"

        // Setup about us text
        binding.tvAboutUs.text = "Únete a la experiencia y accede a los mejores canales de televisión peruana."

        // Setup privacy policy link
        binding.btnPrivacyPolicy.setOnClickListener {
            openPrivacyPolicy()
        }

        // Setup contact buttons
        binding.btnEmail.setOnClickListener {
            sendEmail()
        }

        binding.btnPhone.setOnClickListener {
            makePhoneCall()
        }

        // Setup current channel (for TV app simulation)
        binding.tvCurrentChannel.text = "001 - Willax HD"
        binding.ivChannelLogo.setImageResource(R.drawable.ic_willax)
    }

    private fun openPrivacyPolicy() {
        // TODO: Open privacy policy URL
        showToast("Política de privacidad - Próximamente")
    }

    private fun sendEmail() {
        try {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf("contacto@streamflix.demo"))
                putExtra(Intent.EXTRA_SUBJECT, "Consulta - StreamFlix DEMO")
            }
            startActivity(intent)
        } catch (e: Exception) {
            showToast("No se pudo abrir el cliente de correo")
        }
    }

    private fun makePhoneCall() {
        try {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:+51937466317")
            }
            startActivity(intent)
        } catch (e: Exception) {
            showToast("No se pudo abrir el marcador")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}