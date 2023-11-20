package com.example.eisenhower.database

import android.content.Context
import android.graphics.Color
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.eisenhower.dao.BlockDao
import com.example.eisenhower.dao.TasksDao
import com.example.eisenhower.model.Block
import com.example.eisenhower.model.Task
import java.util.concurrent.Executors

@Database(entities = [Task::class, Block::class], version = 1)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tasksDao(): TasksDao
    abstract fun blockDao(): BlockDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        val databaseWriteExecutor = Executors.newSingleThreadExecutor()

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addCallback(callback)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
        private val callback = object: Callback(){
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                databaseWriteExecutor.execute {
                    val blocks = listOf(
                        Block( title = "Ważne, Pilne", color = Color.parseColor("#FFCCCC"), priority = 0),
                        Block( title = "Ważne, Niepilne", color = Color.parseColor("#FFDDCC"), priority = 1),
                        Block( title = "Nieważne, Pilne", color = Color.parseColor("#FFEECC"), priority = 2),
                        Block( title = "Nieważne, Niepilne", color = Color.parseColor("#FFFFCC"), priority = 3)
                    )
                    for(block in blocks){
                        INSTANCE!!.blockDao().insert(block)
                    }
                }
            }
        }
    }
}
