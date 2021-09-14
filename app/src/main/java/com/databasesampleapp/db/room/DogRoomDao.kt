package com.databasesampleapp.db.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface DogRoomDao {

    @Query("SELECT * FROM dogs")
    fun getAll(): Flow<List<Dog>>

    @Insert
    suspend fun insert(dog: Dog)

    @Update
    suspend fun update(dog: Dog)

    @Delete
    suspend fun delete(dog: Dog)
}