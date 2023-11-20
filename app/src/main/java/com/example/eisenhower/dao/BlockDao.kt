package com.example.eisenhower.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.eisenhower.model.Block

@Dao
interface BlockDao {
    @Query("SELECT * FROM block")
    fun getBlocks(): LiveData<List<Block>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(block: Block)

    @Update
    fun update(block: Block)

    @Delete
    fun delete(block: Block)
}
