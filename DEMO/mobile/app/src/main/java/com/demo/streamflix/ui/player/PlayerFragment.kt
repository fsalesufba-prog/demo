package com.demo.streamflix.ui.player

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.demo.streamflix.R
import com.demo.streamflix.databinding.FragmentPlayerBinding
import com.demo.streamflix.util.Extensions.showToast
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PlayerFragment : Fragment() {

    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PlayerViewModel by viewModels()
    private val args: PlayerFragmentArgs by navArgs()

    @Inject
    lateinit var exoPlayerManager: ExoPlayerManager
    
    private var exoPlayer: ExoPlayer? = null
    private var isFullscreen = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservers()
        initializePlayer()
    }

    private fun setupUI() {
        // Configurar informações do canal
        binding.tvChannelName.text = args.channel.name
        binding.tvChannelNumber.text = String.format("%03d", args.channel.number)

        // Configurar botão de voltar
        binding.ivBack.setOnClickListener {
            releasePlayer()
            findNavController().navigateUp()
        }

        // Configurar botão de tela cheia
        binding.btnFullscreen.setOnClickListener {
            toggleFullscreen()
        }

        // Configurar botão de favorito
        binding.btnFavorite.setOnClickListener {
            toggleFavorite()
        }

        // Configurar controles de player
        binding.playerView.setControllerVisibilityListener { visibility ->
            if (visibility == View.VISIBLE) {
                binding.controlsOverlay.visibility = View.VISIBLE
            } else {
                binding.controlsOverlay.visibility = View.GONE
            }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.isFavorite.collect { isFavorite ->
                updateFavoriteButton(isFavorite)
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

    private fun initializePlayer() {
        exoPlayer = exoPlayerManager.createPlayer(requireContext()).apply {
            // Configurar listener de eventos
            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    when (playbackState) {
                        Player.STATE_BUFFERING -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        Player.STATE_READY -> {
                            binding.progressBar.visibility = View.GONE
                            binding.tvLoading.visibility = View.GONE
                        }
                        Player.STATE_ENDED -> {
                            // Vídeo terminou
                        }
                    }
                }

                override fun onPlayerError(error: PlaybackException) {
                    binding.progressBar.visibility = View.GONE
                    binding.tvLoading.text = getString(R.string.player_error)
                    binding.tvLoading.visibility = View.VISIBLE
                    showToast("Error playing stream: ${error.message}")
                }
            })
        }

        // Configurar player view
        binding.playerView.player = exoPlayer
        binding.playerView.keepScreenOn = true

        // Carregar stream
        loadStream(args.channel.streamUrl)
    }

    private fun loadStream(streamUrl: String) {
        val dataSourceFactory = DefaultHttpDataSource.Factory()
            .setAllowCrossProtocolRedirects(true)
            .setUserAgent("StreamFlix-Android")

        val mediaSource = HlsMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(streamUrl))

        exoPlayer?.setMediaSource(mediaSource)
        exoPlayer?.prepare()
        exoPlayer?.play()

        // Verificar se é favorito
        viewModel.checkIfFavorite(args.channel.id)
    }

    private fun toggleFullscreen() {
        if (isFullscreen) {
            // Sair do modo tela cheia
            requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            binding.btnFullscreen.setImageResource(R.drawable.ic_fullscreen)
        } else {
            // Entrar no modo tela cheia
            requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            binding.btnFullscreen.setImageResource(R.drawable.ic_fullscreen_exit)
        }
        isFullscreen = !isFullscreen
    }

    private fun toggleFavorite() {
        viewModel.toggleFavorite(args.channel)
    }

    private fun updateFavoriteButton(isFavorite: Boolean) {
        val iconRes = if (isFavorite) {
            R.drawable.ic_favorite_filled
        } else {
            R.drawable.ic_favorite_border
        }
        binding.btnFavorite.setImageResource(iconRes)
    }

    private fun releasePlayer() {
        exoPlayer?.release()
        exoPlayer = null
        binding.playerView.player = null
        
        // Restaurar orientação padrão
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun onPause() {
        super.onPause()
        exoPlayer?.pause()
    }

    override fun onResume() {
        super.onResume()
        exoPlayer?.play()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        releasePlayer()
        _binding = null
    }
}