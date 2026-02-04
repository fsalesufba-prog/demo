package com.demo.streamflix.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.demo.streamflix.R
import com.demo.streamflix.databinding.FragmentCategoryBinding
import com.demo.streamflix.ui.adapters.ChannelAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NacionalFragment : CategoryBaseFragment() {

    override fun getCategoryId(): Int = 1 // ID da categoria Nacional
    override fun getCategoryName(): String = getString(R.string.category_national)
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Carregar canais nacionais
        loadChannels()
    }
}