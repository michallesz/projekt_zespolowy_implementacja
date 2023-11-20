package com.example.eisenhower.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.eisenhower.dao.BlockDao
import com.example.eisenhower.database.AppDatabase
import com.example.eisenhower.model.Block


class BlockRepository internal constructor(application: Application) {
    private val mBlockDao: BlockDao
    private val mAllBlocks: LiveData<List<Block>>

    init {
        val elementRoomDatabase: AppDatabase = AppDatabase.getDatabase(application)
        mBlockDao = elementRoomDatabase.blockDao()
        mAllBlocks = mBlockDao.getBlocks()
    }

    val allBlocks: LiveData<List<Block>>
        get() = mAllBlocks

    fun delete(block: Block) {
        AppDatabase.databaseWriteExecutor.execute { mBlockDao.delete(block) }
    }

    fun update(block: Block) {
        AppDatabase.databaseWriteExecutor.execute { mBlockDao.update(block) }
    }

    fun insert(block: Block) {
        AppDatabase.databaseWriteExecutor.execute { mBlockDao.insert(block) }
    }
}