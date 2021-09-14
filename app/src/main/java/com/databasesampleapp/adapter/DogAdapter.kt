package com.databasesampleapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.databasesampleapp.databinding.DogLayoutBinding
import com.databasesampleapp.db.Dog

class DogAdapter : ListAdapter<Dog, DogViewHolder>(itemComporator) {

    private companion object {

        private val itemComporator = object : DiffUtil.ItemCallback<Dog>() {
            override fun areItemsTheSame(oldItem: Dog, newItem: Dog): Boolean {
                TODO("Not yet implemented")
            }

            override fun areContentsTheSame(oldItem: Dog, newItem: Dog): Boolean {
                TODO("Not yet implemented")
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DogLayoutBinding.inflate(layoutInflater, parent, false)

        return DogViewHolder(binding, binding.root.context.resources)
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}