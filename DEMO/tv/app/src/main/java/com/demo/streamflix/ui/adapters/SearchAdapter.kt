package com.demo.streamflix.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.demo.streamflix.R
import com.demo.streamflix.databinding.ItemSearchResultBinding

class SearchAdapter(
    private val onItemClick: (com.demo.streamflix.data.model.Channel) -> Unit
) : ListAdapter<com.demo.streamflix.data.model.Channel, SearchAdapter.SearchViewHolder>(SearchDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchResultBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SearchViewHolder(
        private val binding: ItemSearchResultBinding,
        private val onItemClick: (com.demo.streamflix.data.model.Channel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(channel: com.demo.streamflix.data.model.Channel) {
            with(binding) {
                tvChannelNumber.text = String.format("%03d", channel.number)
                tvChannelName.text = channel.name
                tvChannelDescription.text = channel.description

                // Load logo
                if (channel.logoUrl.isNotEmpty()) {
                    Glide.with(itemView.context)
                        .load(channel.logoUrl)
                        .placeholder(R.drawable.channel_placeholder)
                        .error(R.drawable.channel_placeholder)
                        .into(ivChannelLogo)
                }

                // Show HD badge
                if (channel.isHd) {
                    tvHdBadge.visibility = View.VISIBLE
                } else {
                    tvHdBadge.visibility = View.GONE
                }

                // Set click listener
                root.setOnClickListener {
                    onItemClick(channel)
                }
            }
        }
    }
}

class SearchDiffCallback : DiffUtil.ItemCallback<com.demo.streamflix.data.model.Channel>() {
    override fun areItemsTheSame(
        oldItem: com.demo.streamflix.data.model.Channel,
        newItem: com.demo.streamflix.data.model.Channel
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: com.demo.streamflix.data.model.Channel,
        newItem: com.demo.streamflix.data.model.Channel
    ): Boolean {
        return oldItem == newItem
    }
}