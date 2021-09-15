package com.databasesampleapp

import com.databasesampleapp.db.room.Dog

interface DogItemListener {

    fun deleteDog(dog: Dog)
    fun openUpdateScreen(dog: Dog)
}