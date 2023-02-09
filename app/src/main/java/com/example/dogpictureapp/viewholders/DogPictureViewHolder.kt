package com.example.dogpictureapp.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dogpictureapp.databinding.ItemPictureBinding

class DogPictureViewHolder(private val binding: ItemPictureBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(pictureUrl: String) {
        binding.apply {
            Glide.with(itemView)
                .load(pictureUrl)
                .into(pictureImageView)
        }
    }
}