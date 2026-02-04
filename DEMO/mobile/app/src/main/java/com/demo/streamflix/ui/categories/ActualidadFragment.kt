package com.demo.streamflix.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.demo.streamflix.R

class ActualidadFragment : CategoryBaseFragment() {

    override fun getCategoryId(): Int = 2 // ID da categoria Actualidad
    override fun getCategoryName(): String = getString(R.string.category_current)
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Carregar canais de atualidade
        loadChannels()
    }
}