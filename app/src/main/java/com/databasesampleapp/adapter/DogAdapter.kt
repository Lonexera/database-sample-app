package com.databasesampleapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.databasesampleapp.databinding.DogLayoutBinding
import com.databasesampleapp.db.room.Dog

class DogAdapter(
    private val onDeleteClick: (Dog) -> Unit,
    private val onEditClick: (Dog) -> Unit
) : ListAdapter<Dog, DogViewHolder>(itemComparator) {

    private companion object {
        private val itemComparator = object : DiffUtil.ItemCallback<Dog>() {
            override fun areItemsTheSame(oldItem: Dog, newItem: Dog): Boolean {
                return oldItem.uid == newItem.uid
            }

            override fun areContentsTheSame(oldItem: Dog, newItem: Dog): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DogLayoutBinding.inflate(layoutInflater, parent, false)

        return DogViewHolder(
            binding,
            binding.root.context.resources,
            onDeleteClick,
            onEditClick
        )
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
