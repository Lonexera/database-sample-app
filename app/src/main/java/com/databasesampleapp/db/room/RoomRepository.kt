package com.databasesampleapp.db.room

import com.databasesampleapp.db.Repository

class RoomRepository(
    private val dogRoomDao: DogRoomDao
    ) : Repository {

    override val repoName: String = "room"
    override val allDogs = dogRoomDao.getAll()

    override suspend fun insert(dog: Dog) {
        dogRoomDao.insert(dog)
    }

    override suspend fun update(dog: Dog) {
        dogRoomDao.update(dog)
    }

    override suspend fun delete(dog: Dog) {
        dogRoomDao.delete(dog)
    }
}
