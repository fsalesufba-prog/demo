package com.demo.streamflix.ui.categories

import com.demo.streamflix.R

class RegionalFragment : CategoryBaseFragment() {

    override fun getCategoryId(): Int = 4 // ID da categoria Regional
    override fun getCategoryName(): String = getString(R.string.category_regional)
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Carregar canais regionais
        loadChannels()
    }
}