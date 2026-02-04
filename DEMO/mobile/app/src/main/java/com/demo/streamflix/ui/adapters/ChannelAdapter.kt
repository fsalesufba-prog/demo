package com.demo.streamflix.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.demo.streamflix.R
import com.demo.streamflix.databinding.ItemChannelBinding

class ChannelAdapter(
    private val onItemClick: (com.demo.streamflix.data.model.Channel) -> Unit
) : ListAdapter<com.demo.streamflix.data.model.Channel, ChannelAdapter.ChannelViewHolder>(ChannelDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        val binding = ItemChannelBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ChannelViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ChannelViewHolder(
        private val binding: ItemChannelBinding,
        private val onItemClick: (com.demo.streamflix.data.model.Channel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(channel: com.demo.streamflix.data.model.Channel) {
            with(binding) {
                tvChannelName.text = channel.name
                
                // Format channel number with leading zeros
                tvChannelNumber.text = String.format("%03d", channel.number)
                
                // Load logo using Glide
                if (channel.logoUrl.isNotEmpty()) {
                    Glide.with(itemView.context)
                        .load(channel.logoUrl)
                        .placeholder(R.drawable.channel_placeholder)
                        .error(R.drawable.channel_placeholder)
                        .into(ivChannelLogo)
                } else {
                    // Use placeholder if no logo
                    ivChannelLogo.setImageResource(R.drawable.channel_placeholder)
                }
                
                // Show HD badge if channel is HD
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

class ChannelDiffCallback : DiffUtil.ItemCallback<com.demo.streamflix.data.model.Channel>() {
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