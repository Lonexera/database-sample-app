package com.databasesampleapp.db.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.databasesampleapp.db.dogsForCreatingDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Dog::class], version = 1)
abstract class DogRoomDatabase : RoomDatabase() {

    abstract fun dogDao(): DogRoomDao

    companion object {
        @Volatile
        private var INSTANCE: DogRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): DogRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    DogRoomDatabase::class.java,
                    "dog_room_database"
                )
                    .addCallback(DogDatabaseCallback(scope))
                    .build()

                INSTANCE = instance
                instance
            }
        }

        private class DogDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.dogDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(dogRoomDao: DogRoomDao) {
            dogsForCreatingDB.forEach { dog ->
                dogRoomDao.insert(dog)
            }
        }
    }
}
