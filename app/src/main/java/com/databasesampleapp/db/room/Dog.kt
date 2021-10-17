package com.databasesampleapp.db.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dogs")
data class Dog(
    @NonNull @ColumnInfo(name = "name") val name: String,
    @NonNull @ColumnInfo(name = "age") val age: Int,
    @NonNull @ColumnInfo(name = "breed") val breed: String
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}
