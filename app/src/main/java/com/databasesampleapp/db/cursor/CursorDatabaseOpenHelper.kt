package com.databasesampleapp.db.cursor

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.databasesampleapp.db.dogsForCreatingDB
import com.databasesampleapp.db.room.Dog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CursorDatabaseOpenHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
), DogCursorDao {

    override fun onCreate(db: SQLiteDatabase?) {
        try {
            db?.execSQL(
                "CREATE TABLE $TABLE_NAME (" +
                        "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "$COLUMN_NAME TEXT," +
                        "$COLUMN_AGE INTEGER," +
                        "$COLUMN_BREED TEXT);"
            ) ?: throw SQLiteException()

            dogsForCreatingDB.forEach { dog ->
                insertDog(db, dog)
            }

        } catch (e: SQLiteException) {
            e.printStackTrace()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun insert(dog: Dog) {
        insertDog(writableDatabase, dog)
    }

    override suspend fun update(dog: Dog) {
        try {
            val newDogValue = ContentValues()
            newDogValue.put(COLUMN_NAME, dog.name)
            newDogValue.put(COLUMN_AGE, dog.age)
            newDogValue.put(COLUMN_BREED, dog.breed)

            writableDatabase.update(
                TABLE_NAME, newDogValue,
                "$COLUMN_ID = ?", arrayOf(dog.uid.toString())
            )
        } catch (e: SQLiteException) {
            e.printStackTrace()
            return
        }
    }

    override suspend fun delete(dog: Dog) {
        try {
            writableDatabase.delete(
                TABLE_NAME, "$COLUMN_ID = ?",
                arrayOf(dog.uid.toString())
            )
        } catch (e: SQLiteException) {
            e.printStackTrace()
            return
        }
    }

    override fun getAll(): Flow<List<Dog>> = flow {
        val cursor = readableDatabase.rawQuery("SELECT * FROM $TABLE_NAME", arrayOf())
        val listOfDogs = mutableListOf<Dog>()

        cursor.moveToFirst()
        do {
            val dog = Dog(
                cursor.getString(1), // dog name
                cursor.getInt(2), // dog age
                cursor.getString(3) // dog breed
            )
            dog.uid = cursor.getInt(0)
            listOfDogs.add(dog)
        } while (cursor.moveToNext())

        cursor.close()
        emit(listOfDogs)
    }

    private fun insertDog(db: SQLiteDatabase, dog: Dog) {
        val dogValue = ContentValues()
        dogValue.put(COLUMN_NAME, dog.name)
        dogValue.put(COLUMN_AGE, dog.age)
        dogValue.put(COLUMN_BREED, dog.breed)

        try {
            db.insert(TABLE_NAME, null, dogValue)
        } catch (e: SQLiteException) {
            e.printStackTrace()
            return
        }
    }


    companion object {
        const val DATABASE_NAME = "dog_cursor_database"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "DOGS"

        const val COLUMN_ID = "_id"
        const val COLUMN_NAME = "NAME"
        const val COLUMN_AGE = "AGE"
        const val COLUMN_BREED = "BREED"
    }

}