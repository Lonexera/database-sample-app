package com.databasesampleapp

import android.app.Application
import com.databasesampleapp.db.cursor.CursorDatabaseOpenHelper
import com.databasesampleapp.db.cursor.CursorRepository
import com.databasesampleapp.db.room.DogRoomDatabase
import com.databasesampleapp.db.room.RoomRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class DogsApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    private val roomDatabase: DogRoomDatabase by lazy {
        DogRoomDatabase.getDatabase(
            this,
            applicationScope
        )
    }

    val roomRepository by lazy { RoomRepository(roomDatabase.dogDao()) }

    private val cursorDatabase: CursorDatabaseOpenHelper by lazy {
        CursorDatabaseOpenHelper(this)
    }

    val cursorRepository by lazy { CursorRepository(cursorDatabase) }
}