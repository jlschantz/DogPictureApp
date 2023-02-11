package com.example.dogpictureapp.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.dogpictureapp.R
import com.example.dogpictureapp.databinding.ItemPictureBinding

class DogPictureViewHolder(private val binding: ItemPictureBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(pictureUrl: String) {
        binding.apply {
            Glide.with(itemView)
                .load(pictureUrl)
                .placeholder(R.drawable.baseline_image_24)
                .error(R.drawable.baseline_image_not_supported_24)
                .into(pictureImageView)
        }
    }
}