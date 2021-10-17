package com.databasesampleapp.adapter

import android.content.res.Resources
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.databasesampleapp.R
import com.databasesampleapp.databinding.DogLayoutBinding
import com.databasesampleapp.db.room.Dog

class DogViewHolder(private val binding: DogLayoutBinding,
                    private val resources: Resources,
                    private val onDeleteClick: (Dog) -> Unit,
                    private val onEditClick: (Dog) -> Unit)
    : RecyclerView.ViewHolder(binding.root) {

    private lateinit var dog: Dog

        fun bind(dog: Dog) {
            this.dog = dog

            makeButtonsInvisible()
            with(binding) {
                entityNameText.text = dog.name

                entityAgeText.text = buildString {
                    append(resources.getString(R.string.entity_age_text))
                    append(dog.age)
                }
                entityBreedText.text = buildString {
                    append(resources.getString(R.string.entity_breed_text))
                    append(dog.breed)
                }


                cardView.setOnLongClickListener {
                    makeButtonsVisible()
                    true
                }
            }
        }

    private fun makeButtonsInvisible() {
        with(binding) {
            deleteButton.visibility = View.GONE
            updateButton.visibility = View.GONE
        }
    }

    private fun makeButtonsVisible() {
        with(binding) {
            deleteButton.visibility = View.VISIBLE
            updateButton.visibility = View.VISIBLE

            deleteButton.setOnClickListener {
                onDeleteClick(dog)
            }

            updateButton.setOnClickListener {
                onEditClick(dog)
                makeButtonsInvisible()
            }
        }
    }
}
