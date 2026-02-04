package com.demo.streamflix.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.demo.streamflix.R
import com.demo.streamflix.data.model.Category
import com.demo.streamflix.databinding.ItemCategoryBinding

class CategoryAdapter(
    private val onItemClick: (Category) -> Unit
) : ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CategoryViewHolder(
        private val binding: ItemCategoryBinding,
        private val onItemClick: (Category) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            with(binding) {
                tvCategoryName.text = category.name
                
                // Load icon using Glide
                if (category.iconUrl.isNotEmpty()) {
                    Glide.with(itemView.context)
                        .load(category.iconUrl)
                        .placeholder(R.drawable.ic_category_placeholder)
                        .error(R.drawable.ic_category_placeholder)
                        .into(ivCategoryIcon)
                } else {
                    // Use local icon based on category name
                    val iconRes = when (category.name.toLowerCase()) {
                        "nacional" -> R.drawable.ic_nacional
                        "actualidad" -> R.drawable.ic_actualidad
                        "infantil" -> R.drawable.ic_infantil
                        "regional" -> R.drawable.ic_regional
                        else -> R.drawable.ic_category_placeholder
                    }
                    ivCategoryIcon.setImageResource(iconRes)
                }

                // Set click listener
                root.setOnClickListener {
                    onItemClick(category)
                }
            }
        }
    }
}

class CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }
}