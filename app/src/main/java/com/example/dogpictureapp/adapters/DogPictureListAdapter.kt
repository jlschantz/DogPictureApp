package com.example.dogpictureapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.dogpictureapp.comparators.DogPictureComparator
import com.example.dogpictureapp.databinding.ItemPictureBinding
import com.example.dogpictureapp.viewholders.DogPictureViewHolder

class DogPictureListAdapter: ListAdapter<String, DogPictureViewHolder>(DogPictureComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogPictureViewHolder {
        val binding = ItemPictureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DogPictureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DogPictureViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let {
            holder.bind(it)
        }
    }
}