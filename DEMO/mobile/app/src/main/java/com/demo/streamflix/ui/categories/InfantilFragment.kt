package com.demo.streamflix.ui.categories

import com.demo.streamflix.R

class InfantilFragment : CategoryBaseFragment() {

    override fun getCategoryId(): Int = 3 // ID da categoria Infantil
    override fun getCategoryName(): String = getString(R.string.category_kids)
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Carregar canais infantis
        loadChannels()
    }
}