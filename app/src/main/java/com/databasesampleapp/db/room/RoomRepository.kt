package com.databasesampleapp.db.room


class RoomRepository(private val dogRoomDao: DogRoomDao) {

    val allDogs = dogRoomDao.getAll()

    suspend fun insert(dog: Dog) {
        dogRoomDao.insert(dog)
    }

    suspend fun update(dog: Dog) {
        dogRoomDao.update(dog)
    }

    suspend fun delete(dog: Dog) {
        dogRoomDao.delete(dog)
    }

}