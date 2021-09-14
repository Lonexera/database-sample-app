package com.databasesampleapp.adapter

import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import com.databasesampleapp.databinding.DogLayoutBinding
import com.databasesampleapp.db.room.Dog

class DogViewHolder(private val binding: DogLayoutBinding,
    private val resources: Resources) : RecyclerView.ViewHolder(binding.root) {


        fun bind(dog: Dog) {

        }

}